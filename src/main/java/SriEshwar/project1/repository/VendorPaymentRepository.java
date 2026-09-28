package SriEshwar.project1.repository;

import SriEshwar.project1.entity.VendorPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorPaymentRepository extends JpaRepository<VendorPayment, Long> {
}
