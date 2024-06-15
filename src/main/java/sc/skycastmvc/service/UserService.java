package sc.skycastmvc.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sc.skycastmvc.entity.UserEntity;
import sc.skycastmvc.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity changePassword(String username,
                                     String newPassword) {
        UserEntity updatedUser = userRepository.findByUsername(username);
        updatedUser.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(updatedUser);
    }
}
