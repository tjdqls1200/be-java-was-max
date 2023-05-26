package was.spring.servlet.mvc.controller;

import was.spring.servlet.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ResponseStatus {
    HttpStatus status() default HttpStatus.OK;
}
