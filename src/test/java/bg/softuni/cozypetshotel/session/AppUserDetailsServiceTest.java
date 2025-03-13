package bg.softuni.cozypetshotel.session;

import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

public class AppUserDetailsServiceTest {
    private AppUserDetailsService toTest;
    private static final String TEST_EMAIL = "user@example.com";
    private static final String NOT_EXISTING_EMAIL = "none@example.com";
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new AppUserDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        User testUser = new User()
                .setEmail(TEST_EMAIL)
                .setPassword("1234")
                .setFirstName("Petar")
                .setLastName("Petrov")
                .setRoles(Set.of(
                        new Role().setRole(RoleNameEnum.ADMIN),
                        new Role().setRole(RoleNameEnum.USER)
                ));

        when(mockUserRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = toTest.loadUserByUsername(TEST_EMAIL);

        Assertions.assertInstanceOf(AppUserDetails.class, userDetails);
        AppUserDetails appUserDetails = (AppUserDetails) userDetails;

        Assertions.assertEquals(TEST_EMAIL, userDetails.getUsername());
        Assertions.assertEquals("1234", userDetails.getPassword());
        Assertions.assertEquals("Petar", appUserDetails.getFirstName());
        Assertions.assertEquals("Petrov", appUserDetails.getLastName());
        Assertions.assertEquals(testUser.getFirstName() + " " + testUser.getLastName(),
                appUserDetails.getFullName());

        var expectedRoles = testUser.getRoles().stream().map(Role::getRole).map(role -> "ROLE_" + role).toList();
        var actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Assertions.assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(NOT_EXISTING_EMAIL)
        );
    }
}