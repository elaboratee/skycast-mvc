package sc.skycastmvc.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sc.skycastmvc.validator.PasswordMatchesValidator;

import java.lang.annotation.*;

/**
 * Проверяет, совпадают ли поля <code>password</code> и <code>matchingPassword</code>
 * класса {@link sc.skycastmvc.misc.RegistrationForm}
 * @see PasswordMatchesValidator
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "Passwords don't match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
