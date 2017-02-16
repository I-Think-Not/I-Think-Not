package itn.issuemanager.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
	String message() default "숫자 영문자 특수 문자를 포함한 8 ~ 12 자를 입력하세요.";
	
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
