package pl.itkurnik.transportservicesapi.domain.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.itkurnik.transportservicesapi.domain.reservation.dto.CreateReservationRequest;
import pl.itkurnik.transportservicesapi.domain.reservation.dto.UpdateReservationRequest;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationEntity> findAll() {
        log.info("Finding all reservations");
        return reservationService.findAll();
    }

    @GetMapping("/user")
    public List<ReservationEntity> findAllByUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Finding all reservations by user {}", userEmail);
        return reservationService.findAllByUserEmail(userEmail);
    }

    @PostMapping
    public void create(@RequestBody CreateReservationRequest request) {
        log.info("Creating reservation");
        reservationService.create(request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting reservation with id {}", id);
        reservationService.deleteById(id);
    }

    @PutMapping
    public void update(@RequestBody UpdateReservationRequest request) {
        log.info("Updating reservation with id {}", request.getId());
        reservationService.update(request);
    }

    @PutMapping("/{id}")
    public void cancelById(@PathVariable Long id) {
        log.info("Cancelling reservation with id {}", id);
        reservationService.cancelById(id);
    }
}
