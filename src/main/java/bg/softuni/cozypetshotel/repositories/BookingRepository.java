package bg.softuni.cozypetshotel.repositories;

import bg.softuni.cozypetshotel.models.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByEmailAndCheckOut(String email, LocalDate date);
}
