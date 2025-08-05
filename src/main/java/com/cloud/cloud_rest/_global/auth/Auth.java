package com.cloud.cloud_rest._global.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 이 어노테이션을 메소드에만 붙일 수 있도록 제한
@Retention(RetentionPolicy.RUNTIME) // 런타임 시에 이 어노테이션 정보를 JVM이 읽을 수 있도록 설정
public @interface Auth {
}
