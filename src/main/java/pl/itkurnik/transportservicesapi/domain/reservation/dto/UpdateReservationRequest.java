package pl.itkurnik.transportservicesapi.domain.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.itkurnik.transportservicesapi.domain.reservation.ReservationType;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UpdateReservationRequest {
    private Long id;
    private String customerName;
    private Long vehicleId;
    private ReservationType reservationType;
    private Date departureDate;
    private String destination;
}
