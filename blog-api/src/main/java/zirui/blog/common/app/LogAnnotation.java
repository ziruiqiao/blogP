package zirui.blog.common.app;


import java.lang.annotation.*;

/**
 * 日志注解
 */
// Type 代表可以放在类上， method代表可以放在方法上
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";
    String operator() default "";
}
