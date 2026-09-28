package SriEshwar.project1.repository;

import SriEshwar.project1.entity.TireTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TireTelemetryRepository extends JpaRepository<TireTelemetry, Long> {
    List<TireTelemetry> findByTireId(Long tireId);
}
