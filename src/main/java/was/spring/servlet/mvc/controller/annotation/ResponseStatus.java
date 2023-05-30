package was.spring.servlet.mvc.controller.annotation;

import was.spring.servlet.common.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ResponseStatus {
    HttpStatus status() default HttpStatus.OK;
}
