package SriEshwar.project1.service;

import SriEshwar.project1.dto.TireRequestDTO;
import SriEshwar.project1.entity.Tire;
import SriEshwar.project1.entity.Vehicle;
import SriEshwar.project1.repository.TireRepository;
import SriEshwar.project1.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TireService {

    @Autowired
    private TireRepository tireRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Tire createTire(TireRequestDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + dto.getVehicleId()));

        Tire tire = new Tire();
        tire.setSerialNumber(dto.getSerialNumber());
        tire.setBrand(dto.getBrand());
        tire.setSize(dto.getSize());
        tire.setVehicle(vehicle);
        tire.setPosition(dto.getPosition());
        tire.setStatus("ACTIVE");
        tire.setCurrentPsi(dto.getCurrentPsi() != null ? dto.getCurrentPsi() : 32.0);
        tire.setWearPercentage(dto.getWearPercentage() != null ? dto.getWearPercentage() : 0.0);

        return tireRepository.save(tire);
    }

    public List<Tire> getAllTires() {
        return tireRepository.findAll();
    }

    public Tire getTireById(Long id) {
        return tireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tire not found with id: " + id));
    }
}
