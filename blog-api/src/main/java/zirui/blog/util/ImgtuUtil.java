package zirui.blog.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: ImgtuUtil
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/27 15:54
 */
@Slf4j
public class ImgtuUtil {
    static final String IMGTU_USER_NAME = "zirui";
    static final String IMGTU_PASSWORD = "q1a2z3";
    static final String IMGTU_ALBUMID = "fpQv6";

    static final private String IMGTU_INIT_URL = "https://imgtu.com/init";

    static final private String IMGTU_LOGIN_URL = "https://imgtu.com/login";

    static final private String IMGTU_OPERATE_URL = "https://imgtu.com/json";

    static final private Pattern SESSION_ID_PATTERN = Pattern.compile("PHPSESSID=([^;]*); path=/; HttpOnly");

    static final private Pattern AUTH_TOKEN_PATTERN = Pattern.compile("PF\\.obj\\.config\\.auth_token = \"([0-9a-f]{40})\";");

    static final private Pattern KEEP_LOGIN_PATTERN = Pattern.compile("KEEP_LOGIN=([^;]*);");

    static final private long INIT_VALID_DURATION = 15L * 60 * 1000;

    static final private long LOGIN_VALID_DURATION = 30L * 24 * 60 * 60 * 1000;

    @Getter
    static private String sessionId;

    @Getter
    static private String authToken;

    @Getter
    static private String keepLogin;

    @Getter
    static private long initTimestamp = 0;

    @Getter
    static private long loginTimestamp = 0;

    public static Boolean initSession() {
        return initSession(false);
    }

    public static Boolean initSession(boolean forceAction) {
        if (!forceAction && !isSessionIdExpired()) {
            log.info("?????????????????????????????????????????????????????????????????????");
            return null;
        }
        synchronized (ImgtuUtil.class) {
            // ?????????
            sessionId = null;
            authToken = null;

            // ???????????????
            String httpRawString;
            CloseableHttpResponse httpResponse;
            try {
                httpResponse = HttpUtil.get(IMGTU_INIT_URL, new HashMap<>(0), new HashMap<>(0));
                HttpEntity httpEntity = httpResponse.getEntity();
                httpRawString = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                log.error("????????????????????????????????????????????????{}???" + e.getLocalizedMessage());
                e.printStackTrace();
                return false;
            }

            // ??????SessionId
            Header[] responseHeaders = httpResponse.getAllHeaders();
            for (Header header : responseHeaders) {
                if ("Set-Cookie".equalsIgnoreCase(header.getName())) {
                    String cookies = header.getValue();
                    Matcher matcher = SESSION_ID_PATTERN.matcher(cookies);
                    if (matcher.find(0)) {
                        sessionId = matcher.group(1);
                    }
                }
            }

            if (sessionId == null) {
                log.error("??????????????????????????????SessionId?????????");
                return false;
            }

            // ??????AuthToken
            Matcher matcher = AUTH_TOKEN_PATTERN.matcher(httpRawString);
            if (matcher.find(0)) {
                authToken = matcher.group(1);
            } else {
                log.error("??????????????????????????????AuthToken?????????");
                return false;
            }

            log.info("?????????????????? SessionId:" + sessionId);
            log.info("?????????????????? AuthToken:" + authToken);

            initTimestamp = System.currentTimeMillis();
            return true;
        }
    }

    public static Boolean login() throws IOException {
        return login(false);
    }

    public static Boolean login(boolean forceAction) throws IOException {
        if (!forceAction && !isLoginExpired()) {
            log.info("?????????????????????????????????????????????????????????????????????");
            return null;
        }
        synchronized (ImgtuUtil.class) {
            // ???????????????
            if (isSessionIdExpired()) {
                Boolean b = initSession();
                if (!(b == null || b)) {
                    log.error("?????????????????????????????????????????????");
                }
            }

            // ???????????????
            Map<String, String> headers = new HashMap<>(3);
            headers.put("cookie", "PHPSESSID=" + sessionId + ";");
            headers.put("content-type", "application/x-www-form-urlencoded");
            headers.put("connection", "keep-alive");

            CloseableHttpResponse httpResponse = HttpUtil.post(IMGTU_LOGIN_URL, new HashMap<>(0), headers, "login-subject=" + IMGTU_USER_NAME + "&password=" + IMGTU_PASSWORD + "&auth_token=" + authToken);

            Header[] responseHeaders = httpResponse.getAllHeaders();
            for (Header header : responseHeaders) {
                if ("Set-Cookie".equalsIgnoreCase(header.getName())) {
                    String cookies = header.getValue();
                    Matcher matcher = KEEP_LOGIN_PATTERN.matcher(cookies);
                    if (matcher.find(0)) {
                        keepLogin = matcher.group(1);
                    }
                }
            }

            if (keepLogin != null) {
                loginTimestamp = System.currentTimeMillis();
                log.info("??????????????? KeepLogin:" + keepLogin);
                return true;
            } else {
                log.error("?????????????? StatusCode:" + httpResponse.getStatusLine().getStatusCode());
                return false;
            }
        }
    }

    public static boolean ensureLogin() throws IOException {
        Boolean loginRes = login();
        if (loginRes == null) {
            Boolean initRes = initSession();
            return initRes == null || initRes;
        } else {
            return loginRes;
        }
    }

    public static JsonObject upload(byte[] bytes, String fileName, ContentType fileType) throws IOException {
        log.info("-------->>>> ?????????????? <<<<--------");
        if (!ensureLogin()) {
            log.error("???????????????????????????????????????");
            return null;
        }

        Map<String, String> headers = new HashMap<>(3);
        headers.put("Cookie", "PHPSESSID=" + sessionId + "; KEEP_LOGIN=" + keepLogin);
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36");
        headers.put("Connection", "keep-alive");

        try {
            Map<String, ContentBody> params = new HashMap<>(6);
            params.put("source", new ByteArrayBody(bytes, fileType, fileName));
            params.put("type", new StringBody("file", ContentType.MULTIPART_FORM_DATA));
            params.put("action", new StringBody("upload", ContentType.MULTIPART_FORM_DATA));
            params.put("timestamp", new StringBody(Long.toString(System.currentTimeMillis()), ContentType.MULTIPART_FORM_DATA));
            params.put("auth_token", new StringBody(authToken, ContentType.MULTIPART_FORM_DATA));
            params.put("nsfw", new StringBody("0", ContentType.MULTIPART_FORM_DATA));
            params.put("album_id", new StringBody(IMGTU_ALBUMID, ContentType.MULTIPART_FORM_DATA));

            CloseableHttpResponse httpResponse = HttpUtil.multipart(IMGTU_OPERATE_URL, new HashMap<>(0), headers, params);
            String httpRawString = EntityUtils.toString(httpResponse.getEntity());
            log.info("????????????????????????????????????");
            return new Gson().fromJson(httpRawString, JsonObject.class);
        } catch (IOException e) {
            log.error("?????????????????????{}" + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static JsonObject delete(String deleteId) throws IOException {
        log.info("-------->>>> ?????????????? <<<<--------");
        if (!ensureLogin()) {
            log.error("???????????????????????????????????????");
            return null;
        }
        Map<String, String> headers = new HashMap<>(3);
        headers.put("Cookie", "PHPSESSID=" + sessionId + "; KEEP_LOGIN=" + keepLogin);
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36");
        headers.put("Connection", "keep-alive");

        try {
            Map<String, ContentBody> params = new HashMap<>(5);
            params.put("auth_token", new StringBody(authToken, ContentType.MULTIPART_FORM_DATA));
            params.put("action", new StringBody("delete", ContentType.MULTIPART_FORM_DATA));
            params.put("single", new StringBody("true", ContentType.MULTIPART_FORM_DATA));
            params.put("delete", new StringBody("image", ContentType.MULTIPART_FORM_DATA));
            params.put("deleting[id]", new StringBody(deleteId, ContentType.MULTIPART_FORM_DATA));

            CloseableHttpResponse httpResponse = HttpUtil.multipart(IMGTU_OPERATE_URL, new HashMap<>(0), headers, params);
            String httpRawString = EntityUtils.toString(httpResponse.getEntity());
            log.info("????????????????????????????????????");
            return new Gson().fromJson(httpRawString, JsonObject.class);
        } catch (IOException e) {
            log.error("?????????????????????{}" + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    static boolean isSessionIdExpired() {
        return initTimestamp + INIT_VALID_DURATION < System.currentTimeMillis();
    }

    static boolean isLoginExpired() {
        return loginTimestamp + LOGIN_VALID_DURATION < System.currentTimeMillis();
    }

    public static String uploadPic(File file, String fileName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[0];
        try {
            BufferedImage bi;
            bi = ImageIO.read(file);
            ImageIO.write(bi, "jpg", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return uploadPic(bytes, fileName);
    }

    public static String uploadPic(byte[] bytes, String fileName) throws IOException {
        JsonObject testFile = upload(bytes, fileName, FileType.checkType(fileName));
        assert testFile != null;
        JsonObject image1 = testFile.getAsJsonObject("image");
        assert image1 != null;
        JsonObject image = image1.getAsJsonObject("image");
        assert image != null;
        JsonPrimitive jp = image.getAsJsonPrimitive("url");

        String asString = jp.getAsString();
        log.info("short url: " + asString);

        return asString;
    }

    public static void main(String[] args) throws IOException {
        File img = new File("C:\\Users\\mikeq\\Desktop\\WeChat_20220727174850.mp4");
        String s = uploadPic(img, "testFile");

        log.info("s :" + s);
    }
}
