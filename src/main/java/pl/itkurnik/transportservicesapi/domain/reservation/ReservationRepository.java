package pl.itkurnik.transportservicesapi.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.itkurnik.transportservicesapi.domain.user.UserEntity;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findAllByUser(UserEntity user);
}
