package sc.skycastmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sc.skycastmvc.entity.ChosenCity;

import java.util.Optional;

public interface ChosenCityRepository extends JpaRepository<ChosenCity, Long> {

    Optional<ChosenCity> findByCityName(String cityName);
}
