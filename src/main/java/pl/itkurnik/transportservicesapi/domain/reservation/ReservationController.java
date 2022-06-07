package pl.itkurnik.transportservicesapi.domain.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.itkurnik.transportservicesapi.domain.reservation.dto.CreateReservationRequest;
import pl.itkurnik.transportservicesapi.domain.reservation.dto.UpdateReservationRequest;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationEntity> findAll() {
        return reservationService.findAll();
    }

    @GetMapping("/user")
    public List<ReservationEntity> findAllByUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return reservationService.findAllByUserEmail(userEmail);
    }

    @PostMapping
    public void create(@RequestBody CreateReservationRequest request) {
        reservationService.create(request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
    }

    @PutMapping
    public void update(@RequestBody UpdateReservationRequest request) {
        reservationService.update(request);
    }
}
