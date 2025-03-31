package bg.softuni.cozypetshotel.session;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.entities.Booking;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class AppUserDetails extends User {
    private final UUID uuid;
    private String firstName;
    private String lastName;
//    private List<BookingDTO> expiredBookings;

    public AppUserDetails(UUID uuid, String email, String password,
                          Collection<? extends GrantedAuthority> authorities,
                          String firstName,
                          String lastName
//            , List<Booking> activeBookings
    ) {
        super(email, password, authorities);
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public AppUserDetails setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppUserDetails setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();

        if (firstName != null) {
            fullName.append(this.firstName);
        }
        if (lastName != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(lastName);
        }

        return fullName.toString();
    }
//
//    public List<BookingDTO> getExpiredBookings() {
//        return expiredBookings;
//    }
//
//    public AppUserDetails setExpiredBookings(List<BookingDTO> expiredBookings) {
//        this.expiredBookings = expiredBookings;
//        return this;
//    }
}
