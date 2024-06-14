package sc.skycastmvc.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sc.skycastmvc.validator.UserAlreadyExistsValidator;

import java.lang.annotation.*;

/**
 * Проверяет, что пользователь с именем пользователя <code>username</code>
 * не существует
 * @see sc.skycastmvc.misc.RegistrationForm
 * @see UserAlreadyExistsValidator
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserAlreadyExistsValidator.class)
@Documented
public @interface UserAlreadyExists {

    String message() default "Такой пользователь уже существует";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
