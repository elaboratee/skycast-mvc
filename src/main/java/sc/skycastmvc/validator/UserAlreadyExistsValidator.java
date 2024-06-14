package sc.skycastmvc.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sc.skycastmvc.annotation.UserAlreadyExists;
import sc.skycastmvc.misc.RegistrationForm;
import sc.skycastmvc.repository.UserRepository;

/**
 * Отвечает за валидацию существования пользователя при регистрации.
 * При наличии пользователя с именем пользователя <code>username</code> выбрасывает
 * кастомную ошибку
 * @see UserAlreadyExists
 */
public class UserAlreadyExistsValidator implements ConstraintValidator<UserAlreadyExists, Object> {

    private final UserRepository userRepository;

    public UserAlreadyExistsValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserAlreadyExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj,
                           ConstraintValidatorContext context) {
        RegistrationForm form = (RegistrationForm) obj;
        if (userRepository.findByUsername(form.getUsername()) != null) {
            // Отключаем стандартную ошибку
            context.disableDefaultConstraintViolation();
            // Создаем кастомную ошибку
            context.buildConstraintViolationWithTemplate("Такой пользователь уже существует")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
