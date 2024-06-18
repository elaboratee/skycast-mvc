package sc.skycastmvc.exception;

public class CityIsNotFavourite extends Exception {

    public CityIsNotFavourite() {
    }

    public CityIsNotFavourite(String message) {
        super(message);
    }

    public CityIsNotFavourite(String message, Throwable cause) {
        super(message, cause);
    }
}
