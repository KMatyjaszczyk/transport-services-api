package pl.itkurnik.transportservicesapi.domain.reservation;

import org.springframework.stereotype.Component;
import pl.itkurnik.transportservicesapi.domain.reservation.dto.CreateReservationRequest;
import pl.itkurnik.transportservicesapi.domain.reservation.dto.UpdateReservationRequest;

import java.util.Date;
import java.util.regex.Pattern;

@Component
public class ReservationValidator {
    private static final Pattern CUSTOMER_NAME_PATTERN = Pattern.compile("^[A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż][A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż .&-]{2,}$");
    private static final Pattern DESTINATION_PATTERN = Pattern.compile("^[A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż][A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż .-]{2,}$");

    public void validateCreateRequestData(CreateReservationRequest request) {
        validateCustomerName(request.getCustomerName());
        validateDestination(request.getDestination());
        validateDepartureDate(request.getDepartureDate());
    }

    public void validateUpdateRequestData(UpdateReservationRequest request) {
        validateCustomerName(request.getCustomerName());
        validateDestination(request.getDestination());
        validateDepartureDate(request.getDepartureDate());
    }

    private void validateDepartureDate(Date departureDate) {
        boolean departureDateIsFromThePast = departureDate.before(new Date());
        if (departureDateIsFromThePast) {
            throw new RuntimeException("Departure date is from the past");
        }
    }

    private void validateCustomerName(String customerName) {
        boolean customerNameIsValid = CUSTOMER_NAME_PATTERN.matcher(customerName).matches();
        if (!customerNameIsValid) {
            throw new RuntimeException("Invalid customer name");
        }
    }

    private void validateDestination(String destination) {
        boolean destinationIsValid = DESTINATION_PATTERN.matcher(destination).matches();
        if (!destinationIsValid) {
            throw new RuntimeException("Invalid destination");
        }
    }
}
