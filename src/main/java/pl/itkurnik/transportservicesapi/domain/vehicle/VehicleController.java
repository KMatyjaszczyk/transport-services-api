package pl.itkurnik.transportservicesapi.domain.vehicle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
    public final VehicleService vehicleService;

    @GetMapping
    public List<VehicleEntity> findAll() {
        log.info("Finding all vehicles");
        return vehicleService.findAll();
    }
}
