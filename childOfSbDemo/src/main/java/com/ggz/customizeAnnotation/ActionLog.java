package com.ggz.customizeAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLog {

    /**
     * 业务模块名称
     * @return
     */
    String module();

    /**
     * 操作名称
     * @return
     */
    String action();

    /**
     * 控制器出现异常时，解决方案
     * @return
     */
    String error() default "操作失败";
}
