package sc.skycastmvc.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sc.skycastmvc.entity.ChosenCity;
import sc.skycastmvc.entity.UserEntity;
import sc.skycastmvc.exception.CityIsAlreadyFavourite;
import sc.skycastmvc.repository.ChosenCityRepository;
import sc.skycastmvc.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ChosenCityRepository chosenCityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       ChosenCityRepository chosenCityRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.chosenCityRepository = chosenCityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Используется, чтобы изменить пароль пользователя
     * @param username имя пользователя, у которого необходимо изменить пароль
     * @param newPassword новый пароль
     * @return объект <code>UserEntity</code> с измененным паролем
     */
    @Transactional
    public UserEntity changePassword(String username,
                                     String newPassword) {
        UserEntity updatedUser = userRepository.findByUsername(username);
        updatedUser.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(updatedUser);
    }

    /**
     * Используется для добавления города с названием <code>cityName</code>
     * в список избранных городов пользователя <code>user</code>
     * @param user пользователь, в список которого необходимо добавить избранный город
     * @param cityName название добавляемого города
     */
    @Transactional
    public UserEntity addChosenCity(UserEntity user,
                                    String cityName) throws CityIsAlreadyFavourite {

        // Получаем список избранных городов пользователя
        List<ChosenCity> chosenCities = user.getChosenCities();

        // Если город уже является избранным для данного пользователя
        if (isFavouriteCity(chosenCities, cityName)) {
            throw new CityIsAlreadyFavourite("Данный город уже является избранным для пользователя"
                    + user.getUsername());
        }

        // Создаем новый кортеж на основе названия города
        ChosenCity chosenCity = new ChosenCity(cityName);

        // Добавляем избранный город пользователю
        user.addChosenCity(chosenCity);

        // Сохраняем изменения в базе данных
        chosenCityRepository.save(chosenCity);

        return user;
    }

    /**
     * Проверяет, является ли город с названием <code>toCheck</code> избранным
     * @param chosenCityList список, в котором осуществляется поиск города
     * @param cityNameToCheck имя проверяемого города
     * @return <code>true</code> - если город с именем <code>toCheck</code> уже
     * является избранным (находится в <code>chosenCityList</code>), <code>false</code> -
     * в противоположном случае
     */
    private boolean isFavouriteCity(List<ChosenCity> chosenCityList,
                                    String cityNameToCheck) {
        boolean isFavourite = false;
        for (ChosenCity chosenCity : chosenCityList) {
            if (chosenCity.getCityName().equals(cityNameToCheck)) {
                isFavourite = true;
                break;
            }
        }
        return isFavourite;
    }
}
