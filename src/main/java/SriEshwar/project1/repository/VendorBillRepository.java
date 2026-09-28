package SriEshwar.project1.repository;

import SriEshwar.project1.entity.VendorBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorBillRepository extends JpaRepository<VendorBill, Long> {
}
