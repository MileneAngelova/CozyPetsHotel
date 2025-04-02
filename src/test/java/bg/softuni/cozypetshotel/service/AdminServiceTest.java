package bg.softuni.cozypetshotel.service;

import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.repositories.RoleRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.services.AdminService;
import bg.softuni.cozypetshotel.services.BookingService;
import bg.softuni.cozypetshotel.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    private static final String TEST_EMAIL = "user@example.com";
    private AdminService toTest;
    @Captor
    private ArgumentCaptor<User> userCaptor;
    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private RoleRepository mockedRoleRepository;
    @Mock
    private UserService mockedUserService;
    @Mock
    private SecurityContext mockedSecurityContext;


    @BeforeEach
    void setUp() {
        toTest = new AdminService(
                mockedUserRepository,
                mockedRoleRepository,
                mockedUserService,
                new ModelMapper());
        SecurityContextHolder.setContext(mockedSecurityContext);
    }


    @Test
    public void testFindAllUsers_Success() {
        User user1 = new User();
        user1.setUuid(UUID.randomUUID());
        user1.setEmail("user1@example.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setActive(true);

        User user2 = new User();
        user2.setUuid(UUID.randomUUID());
        user2.setEmail("user2@example.com");
        user2.setFirstName("John");
        user2.setLastName("Smith");
        user2.setActive(true);

        List<User> users = List.of(user1, user2);

        when(mockedUserRepository.findAll()).thenReturn(users);

        List<UserDTO> foundUsers = toTest.getAllUsers();

        Assertions.assertEquals(2, foundUsers.size());
    }

    @Test
    public void addRoleAdminToUser() {
        User user = new User();
        String email = user.getEmail();

        Role userRole = new Role();
        Role adminRole = new Role();

        userRole.setRole(RoleNameEnum.USER);
        adminRole.setRole(RoleNameEnum.ADMIN);

        user.setRoles(new HashSet<>(Set.of(userRole)));

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(mockedRoleRepository.findByRole(RoleNameEnum.ADMIN)).thenReturn(adminRole);

        toTest.addRoleAdminToUser(email);

        Assertions.assertEquals(2, user.getRoles().size());
        verify(mockedUserRepository, times(1)).save(user);
    }

    @Test
    public void testRemoveRoleAdminFromUser() {
        User user = new User();
        String email = TEST_EMAIL;

        Role adminRole = new Role();
        adminRole.setRole(RoleNameEnum.ADMIN);

        user.setRoles(new HashSet<>(Set.of(adminRole)));

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(user));
        toTest.removeRoleAdminFromUser(email);
    }

    @Test
    public void testBlockUser() {
        User user = new User();
        user.setActive(true);

        when(mockedUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
        toTest.blockUser(TEST_EMAIL);

        verify(mockedUserRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assertions.assertFalse(savedUser.isActive());
    }

    @Test
    public void testActivateUserAccount() {
        User user = new User();
        user.setActive(false);
        user.setEmail(TEST_EMAIL + "disabled");

        when(mockedUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
        toTest.activateUserAccount(TEST_EMAIL);

        verify(mockedUserRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assertions.assertTrue(savedUser.isActive());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setEmail(TEST_EMAIL);
        when(mockedUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        toTest.deleteUser(TEST_EMAIL);

        verify(mockedUserRepository).delete(user);    }
}

