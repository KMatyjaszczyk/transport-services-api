package pl.itkurnik.transportservicesapi.domain.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pl.itkurnik.transportservicesapi.domain.user.UserEntity;
import pl.itkurnik.transportservicesapi.domain.vehicle.VehicleEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(generator = "reservation-sequence-generator")
    @GenericGenerator(
            name = "reservation-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "reservation_sequence"),
                    @Parameter(name = "initial_value", value = "4"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "customer_name")
    private String customerName;

    @ManyToOne(targetEntity = VehicleEntity.class)
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ReservationType type;

    @Column(name = "departure_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureDate;

    @Column(name = "departure_place")
    private String departurePlace;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "last_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
}
