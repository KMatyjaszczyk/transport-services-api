package pl.itkurnik.transportservicesapi.api;

public class ErrorCodes {
    public static String INCORRECT_USERNAME_OR_PASSWORD = "Incorrect username or password";
    public static String USER_ALREADY_EXISTS = "User %s already exists";
    public static String USER_NOT_FOUND = "User not found";

    public static String VEHICLE_NOT_FOUND = "Vehicle not found";

    public static String RESERVATION_NOT_FOUND = "Reservation not found";
    public static String NOT_HIS_OWN_RESERVATION = "User wants to manipulate not his own reservation";
}
