package sc.skycastmvc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sc.skycastmvc.entity.ChosenCity;
import sc.skycastmvc.entity.UserEntity;
import sc.skycastmvc.exception.CityIsAlreadyFavourite;
import sc.skycastmvc.exception.CityIsNotFavourite;
import sc.skycastmvc.repository.ChosenCityRepository;
import sc.skycastmvc.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChosenCityRepository chosenCityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService sut;

    @Test
    void canChangePassword() {
        // given
        String userName = "TestUser";
        String newPassword = "newPassword";

        UserEntity user = new UserEntity(userName, "oldPassword");

        given(userRepository.findByUsername(userName)).willReturn(Optional.of(user));
        given(passwordEncoder.encode(newPassword)).willReturn("encodedNewPassword");
        given(userRepository.save(any(UserEntity.class))).willAnswer(i -> i.getArgument(0));

        // when
        UserEntity updatedUser = sut.changePassword(userName, newPassword);

        // then
        assertEquals("encodedNewPassword", updatedUser.getPassword());
        verify(userRepository, times(1)).findByUsername(userName);
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void canAddChosenCity() throws CityIsAlreadyFavourite {
        // given
        String userName = "TestUser";
        String cityName = "NewCity";

        UserEntity user = new UserEntity(userName, "password");
        ChosenCity chosenCity = new ChosenCity(cityName);

        given(chosenCityRepository.save(any(ChosenCity.class))).willReturn(chosenCity);

        // when
        UserEntity updatedUser = sut.addChosenCity(user, cityName);

        // then
        assertTrue(updatedUser.getChosenCities()
                .stream()
                .anyMatch(city -> city.getCityName().equals(cityName)));
        verify(chosenCityRepository, times(1)).save(any(ChosenCity.class));
    }

    @Test
    void throwWhenCityIsAlreadyFavourite() {
        // given
        String userName = "TestUser";
        String cityName = "NewCity";

        UserEntity user = new UserEntity(userName, "password");
        ChosenCity chosenCity = new ChosenCity(cityName);

        user.addCity(chosenCity);

        // when
        // then
        assertThrows(CityIsAlreadyFavourite.class, () -> sut.addChosenCity(user, cityName));
        verify(chosenCityRepository, never()).save(any(ChosenCity.class));
    }

    @Test
    void canDeleteChosenCity() throws CityIsNotFavourite {
        // given
        String userName = "TestUser";
        String cityName = "NewCity";

        UserEntity user = new UserEntity(userName, "password");
        ChosenCity chosenCity = new ChosenCity(cityName);

        user.addCity(chosenCity);

        given(chosenCityRepository.findByCityNameAndUser(cityName, user)).willReturn(Optional.of(chosenCity));

        // when
        UserEntity updatedUser = sut.deleteChosenCity(user, cityName);

        // then
        assertFalse(updatedUser.getChosenCities().contains(chosenCity));
        verify(chosenCityRepository, times(1)).deleteById(chosenCity.getId());
    }

    @Test
    void throwWhenCityIsNotFavourite() {
        // given
        String userName = "TestUser";
        String cityName = "NewCity";

        UserEntity user = new UserEntity(userName, "password");

        // when
        // then
        assertThrows(CityIsNotFavourite.class, () -> sut.deleteChosenCity(user, cityName));
        verify(chosenCityRepository, never()).deleteById(any(Long.class));
    }
}
