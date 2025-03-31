package bg.softuni.cozypetshotel.session;

import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public AppUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(email)
                .map(AppUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    private static UserDetails map(User user) {
        return new AppUserDetails(
                user.getUuid(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(Role::getRole).map(AppUserDetailsService::map).toList(),
                user.getFirstname(),
                user.getLastName()
//                user.getExpiredBookings()
        );
    }

    private static GrantedAuthority map(RoleNameEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}
