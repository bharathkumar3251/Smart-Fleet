package SriEshwar.project1.repository;

import SriEshwar.project1.entity.AnalyticAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticAccountRepository extends JpaRepository<AnalyticAccount, Long> {
}
