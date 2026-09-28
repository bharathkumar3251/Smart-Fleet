package SriEshwar.project1.repository;

import SriEshwar.project1.entity.Tire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TireRepository extends JpaRepository<Tire, Long> {
    List<Tire> findByVehicleId(Long vehicleId);
}
