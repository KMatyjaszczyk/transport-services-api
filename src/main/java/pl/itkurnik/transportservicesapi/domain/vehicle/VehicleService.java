package pl.itkurnik.transportservicesapi.domain.vehicle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public List<VehicleEntity> findAll() {
        return vehicleRepository.findAll();
    }

    public Optional<VehicleEntity> findById(Long id) {
        return vehicleRepository.findById(id);
    }
}
