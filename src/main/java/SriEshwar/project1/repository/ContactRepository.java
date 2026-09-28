package SriEshwar.project1.repository;

import SriEshwar.project1.entity.Contact;
import SriEshwar.project1.enums.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByType(ContactType type);
}
