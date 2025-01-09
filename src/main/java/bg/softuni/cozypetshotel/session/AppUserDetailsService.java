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
//    private final ModelMapper modelMapper;

    public AppUserDetailsService(UserRepository userRepository)
//            , ModelMapper modelMapper)
    {
        this.userRepository = userRepository;
//        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(email)
                .map(AppUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));

        //        Optional<User> byEmail = this.userRepository.findByEmail(email);
//                return new AppUserDetails(
//                        byEmail.get().getUsername(),
//                        byEmail.get().getEmail(),
//                        byEmail.get().getPassword(),
//                        byEmail.get().getFirstname(),
//                        byEmail.get().getLastName());
    }

    private static UserDetails map(User user) {
//        Role role = new Role();
//        role.setName(RoleNameEnum.USER);
        return new AppUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(Role::getRole).map(AppUserDetailsService::map).toList(), user.getFirstname(),
                user.getLastName()
        );
    }

    private static GrantedAuthority map(RoleNameEnum role) {
        return new SimpleGrantedAuthority(
                "Role_" + role
        );
    }
}
