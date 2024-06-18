package sc.skycastmvc.exception;

public class CityIsAlreadyFavourite extends Exception {

    public CityIsAlreadyFavourite() {
    }

    public CityIsAlreadyFavourite(String message) {
        super(message);
    }

    public CityIsAlreadyFavourite(String message, Throwable cause) {
        super(message, cause);
    }
}
