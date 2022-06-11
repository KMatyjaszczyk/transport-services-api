package pl.itkurnik.transportservicesapi.domain.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.itkurnik.transportservicesapi.api.ErrorCodes;
import pl.itkurnik.transportservicesapi.domain.reservation.dto.CreateReservationRequest;
import pl.itkurnik.transportservicesapi.domain.reservation.dto.UpdateReservationRequest;
import pl.itkurnik.transportservicesapi.domain.user.UserEntity;
import pl.itkurnik.transportservicesapi.domain.user.UserService;
import pl.itkurnik.transportservicesapi.domain.vehicle.VehicleEntity;
import pl.itkurnik.transportservicesapi.domain.vehicle.VehicleService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final VehicleService vehicleService;
    private final ReservationValidator validator;

    public List<ReservationEntity> findAll() {
        return reservationRepository.findAll();
    }

    public List<ReservationEntity> findAllByUserEmail(String email) {
        UserEntity user = findUserByEmail(email);
        return reservationRepository.findAllByUser(user);
    }

    public void create(CreateReservationRequest request) {
        validator.validateCreateRequestData(request);

        UserEntity user = receiveCurrentUser();
        VehicleEntity vehicle = findVehicleById(request.getVehicleId());

        ReservationEntity reservation = new ReservationEntity();
        reservation.setCustomerName(request.getCustomerName());
        reservation.setVehicle(vehicle);
        reservation.setType(request.getReservationType());
        reservation.setDepartureDate(request.getDepartureDate());
        reservation.setDestination(request.getDestination());
        reservation.setUser(user);
        reservation.setCreationDate(new Date());
        reservation.setStatus(ReservationStatus.CREATED);

        reservationRepository.save(reservation);
    }

    private VehicleEntity findVehicleById(Long id) {
        return vehicleService.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCodes.VEHICLE_NOT_FOUND));
    }

    private UserEntity findUserByEmail(String email) {
        return userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ErrorCodes.USER_NOT_FOUND));
    }

    public void deleteById(Long id) {
        UserEntity user = receiveCurrentUser();

        validateReservation(id, user);
        reservationRepository.deleteById(id);
    }

    public void update(UpdateReservationRequest request) {
        validator.validateUpdateRequestData(request);

        UserEntity user = receiveCurrentUser();
        VehicleEntity vehicle = findVehicleById(request.getVehicleId());

        ReservationEntity reservation = validateReservation(request.getId(), user);
        validateIfIsCancelled(reservation);

        reservation.setCustomerName(request.getCustomerName());
        reservation.setVehicle(vehicle);
        reservation.setType(request.getReservationType());
        reservation.setDepartureDate(request.getDepartureDate());
        reservation.setDestination(request.getDestination());
        reservation.setStatus(ReservationStatus.EDITED);
        reservation.setLastUpdateDate(new Date());

        reservationRepository.save(reservation);
    }

    public void cancelById(Long id) {
        UserEntity user = receiveCurrentUser();
        ReservationEntity reservation = validateReservation(id, user);
        validateIfIsCancelled(reservation);

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setLastUpdateDate(new Date());
        reservationRepository.save(reservation);
    }



    private UserEntity receiveCurrentUser() {
        String userEmail = receiveCurrentUserEmail();
        return findUserByEmail(userEmail);
    }

    private ReservationEntity validateReservation(Long id, UserEntity user) {
        ReservationEntity reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCodes.RESERVATION_NOT_FOUND));

        boolean reservationBelongsToCurrentUser = Objects.equals(reservation.getUser().getId(), user.getId());
        if (!reservationBelongsToCurrentUser) {
            throw new RuntimeException(ErrorCodes.NOT_HIS_OWN_RESERVATION);
        }

        return reservation;
    }

    private void validateIfIsCancelled(ReservationEntity reservation) {
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new RuntimeException(ErrorCodes.RESERVATION_CANCELLED);
        }
    }

    private String receiveCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
