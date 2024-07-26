package sc.skycastmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sc.skycastmvc.entity.ChosenCity;
import sc.skycastmvc.entity.UserEntity;

import java.util.Optional;

public interface ChosenCityRepository extends JpaRepository<ChosenCity, Long> {

    Optional<ChosenCity> findByCityName(String cityName);

    Optional<ChosenCity> findByUser(UserEntity userEntity);

    Optional<ChosenCity> findByCityNameAndUser(String city, UserEntity userEntity);
}
