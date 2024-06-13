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
        return form.getPassword().equals(form.getMatchingPassword());
    }
}
