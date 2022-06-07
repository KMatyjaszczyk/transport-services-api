package pl.itkurnik.transportservicesapi.domain.vehicle;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    public final VehicleService vehicleService;

    @GetMapping
    public List<VehicleEntity> findAll() {
        return vehicleService.findAll();
    }
}
