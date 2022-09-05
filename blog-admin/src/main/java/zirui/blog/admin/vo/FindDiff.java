package zirui.blog.admin.vo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blogP
 *
 * @className: findDiff
 * @Description: TODO
 * @version: v1.17.0
 * @author: ZIRUI QIAO
 * @date: 2022/8/29 13:12
 */
public class FindDiff {
    static String s1 = "";
    static String s2 = "";

    public static void getDiff(String s1) {
        for(int i=0; i<s1.length(); i++) {
            if(s1.charAt(i) == '[') {
                for(int j=i; j<s1.length(); j++) {
                    if(s1.charAt(j) == ']'){
                        StringBuilder temp = new StringBuilder();
                        for(int k=i; k<=j; k++) {
                            temp.append(s1.charAt(k));
                        }
                        String pattern = ".*" + temp.toString() + ".*";
                        boolean isMatch = readS2(pattern);
                        if(isMatch) {
                            System.out.println("temp =" + temp.toString());
                        }
                        break;
                    }
                }
            }
        }
    }

    public static boolean readS2(String pattern) {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
            String pathname = "C:\\Users\\mikeq\\Desktop\\s1.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                boolean isMatch = Pattern.matches(pattern, line);
                if(isMatch) return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void reads1() {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
            String pathname = "C:\\Users\\mikeq\\Desktop\\s2.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if(line != null) {
                    getDiff(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        reads1();
    }
}
