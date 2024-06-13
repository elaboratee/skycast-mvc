package sc.skycastmvc.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sc.skycastmvc.annotation.PasswordMatches;
import sc.skycastmvc.misc.RegistrationForm;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj,
                           ConstraintValidatorContext context) {
        RegistrationForm form = (RegistrationForm) obj;
        if (!form.getPassword().equals(form.getMatchingPassword())) {
            // Отключаем стандартную ошибку
            context.disableDefaultConstraintViolation();
            // Создаем кастомную ошибку
            context.buildConstraintViolationWithTemplate("Пароли не совпадают")
                    .addPropertyNode("matchingPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
