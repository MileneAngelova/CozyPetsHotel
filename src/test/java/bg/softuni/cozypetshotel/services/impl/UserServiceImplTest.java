package bg.softuni.cozypetshotel.services.impl;

import bg.softuni.cozypetshotel.models.dtos.RegisterDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.repositories.RoleRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.session.AppUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final String TEST_EMAIL = "user@example.com";
    private static final String NOT_EXISTENT_EMAIL = "nonexistent@email.com";
    private UserServiceImpl toTest;
    @Captor
    private ArgumentCaptor<User> userCaptor;
    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private PasswordEncoder mockedPasswordEncoder;
    @Mock
    private SecurityContext mockedSecurityContext;
    @Mock
    private Authentication mockedAuthentication;
    @Mock
    private AppUserDetails mockedAppUserDetails;


    @BeforeEach
    void setUp() {
        toTest = new UserServiceImpl(
                mockedUserRepository,
                mockRoleRepository,
                new ModelMapper(),
                mockedPasswordEncoder);
        SecurityContextHolder.setContext(mockedSecurityContext);
    }

    @Test
    void testUserRegistration() {

        RegisterDTO userRegistrationDTO =
                new RegisterDTO()
                        .setFirstName("Ivan")
                        .setLastName("Ivanov")
                        .setPassword("1234")
                        .setEmail(TEST_EMAIL);

        when(mockedPasswordEncoder.encode(userRegistrationDTO.getPassword()))
                .thenReturn(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword());

        toTest.register(userRegistrationDTO);
        verify(mockedUserRepository).save(userCaptor.capture());
        User actualSavedEntity = userCaptor.getValue();

        assertNotNull(actualSavedEntity);
        Assertions.assertEquals(userRegistrationDTO.getFirstName(), actualSavedEntity.getFirstName());
        Assertions.assertEquals(userRegistrationDTO.getLastName(), actualSavedEntity.getLastName());
        Assertions.assertEquals(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword(),
                actualSavedEntity.getPassword());
        Assertions.assertEquals(userRegistrationDTO.getEmail(), actualSavedEntity.getEmail());
    }

    @Test
    public void testGetCurrentUser_UserAuthenticated() {
        when(mockedSecurityContext.getAuthentication()).thenReturn(mockedAuthentication);
        when(mockedAuthentication.getPrincipal()).thenReturn(mockedAppUserDetails);

        Optional<AppUserDetails> result = toTest.getCurrentUser();

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockedAppUserDetails, result.get());
    }

    @Test
    public void testGetCurrentUser_NoAuthentication() {
        when(mockedSecurityContext.getAuthentication()).thenReturn(null);

        Optional<AppUserDetails> result = toTest.getCurrentUser();

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testGetCurrentUser_NotDamUserDetails() {
        when(mockedSecurityContext.getAuthentication()).thenReturn(mockedAuthentication);
        when(mockedAuthentication.getPrincipal()).thenReturn(new Object());

        Optional<AppUserDetails> result = toTest.getCurrentUser();

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testFindByEmail_ExistingUser() {
        String existingEmail = TEST_EMAIL;
        User existingUser = new User();
        existingUser.setEmail(existingEmail);
        when(mockedUserRepository.findByEmail(existingEmail)).thenReturn(Optional.of(existingUser));

        UserDTO userDTO = toTest.findByEmail(existingEmail);

        assertNotNull(userDTO);
        Assertions.assertEquals(existingEmail, userDTO.getEmail());
        verify(mockedUserRepository).findByEmail(existingEmail);
    }

    @Test
    public void testFindByEmail_NonExistingUser() {
        String nonExistingEmail = NOT_EXISTENT_EMAIL;
        when(mockedUserRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> toTest.findByEmail(nonExistingEmail));
    }

    @Test
    public void testEditEmail_Success() {
        User user = new User();
        String email = user.getEmail();
        String newEmail = TEST_EMAIL;

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        when(mockedUserRepository.findByEmail(newEmail)).thenReturn(Optional.empty());

        toTest.editEmail(email, newEmail);

        verify(mockedUserRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assertions.assertEquals(newEmail, savedUser.getEmail());
    }

    @Test
    public void testEditEmail_UserNotFound() {
        User user = new User();
        String email = user.getEmail();
        String newEmail = TEST_EMAIL;

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        when(mockedUserRepository.findByEmail(newEmail)).thenReturn(Optional.empty());

        toTest.editEmail(email, newEmail);
        verify(mockedUserRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assertions.assertEquals(newEmail, savedUser.getEmail());
    }

    @Test
    public void testEditEmail_EmailAlreadyExists() {
        User user = new User();
        String email = user.getEmail();
        String newEmail = TEST_EMAIL;

        when(mockedUserRepository.findByEmail(newEmail)).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> toTest.editEmail(email, newEmail));
    }

    @Test
    public void testEditPassword_Success() {
        User user = new User();
        user.setEmail(TEST_EMAIL);
        user.setFirstName("Petar");
        user.setLastName("Petrov");
        user.setPassword("encodedOldPassword");

        when(mockedPasswordEncoder.matches("1234", "encodedOldPassword")).thenReturn(true);
        when(mockedPasswordEncoder.encode("123456")).thenReturn("encodedNewPassword");

        when(mockedUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(mockedUserRepository.save(user)).thenReturn(user);

        toTest.editPassword(user.getEmail(), "1234", "123456");

        verify(mockedPasswordEncoder).matches("1234", "encodedOldPassword");
        verify(mockedPasswordEncoder).encode("123456");
        assertEquals("encodedNewPassword", user.getPassword());
        verify(mockedUserRepository).save(user);
    }

    @Test
    public void testEditPassword_IncorrectCurrentPassword() {
        String currentPassword = "wrongPassword";
        String newPassword = "newPassword";

        User user = new User();
        String email = user.getEmail();
        user.setEmail(email);
        user.setPassword(mockedPasswordEncoder.encode("correctPassword"));

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(mockedPasswordEncoder.matches(currentPassword, user.getPassword())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            toTest.editPassword(email, currentPassword, newPassword);
        });
    }

    @Test
    public void testEditPassword_IncorrectNewPassword() {
        String currentPassword = "correctPassword";
        String newPassword = "invalidNewPassword";

        User user = new User();
        String email = user.getEmail();
        user.setEmail(email);
        user.setPassword(mockedPasswordEncoder.encode(currentPassword));

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(mockedPasswordEncoder.matches(currentPassword, user.getPassword())).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            toTest.editPassword(email, currentPassword, newPassword);
        });

        Assertions.assertEquals("Password must be between 4 and 30 symbols long!", thrown.getMessage());
    }

    @Test
    public void testEditPassword_UserNotFound() {
        User user = new User();
        String email = user.getEmail();
        user.setEmail(email);

        String currentPassword = "correctPassword";
        String newPassword = "newPassword";

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            toTest.editPassword(email, currentPassword, newPassword);
        });
    }

    @Test
    public void testEditUsername_Success() {
        String newUsername = "newUsername";

        User user = new User();
        String email = user.getEmail();
        user.setUsername(newUsername);

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        toTest.editUsername(email, newUsername);
        assertEquals(newUsername, user.getUsername());
    }

    @Test
    public void editLastName() {
        String newLastName = "newLastName";

        User user = new User();
        String email = user.getEmail();
        user.setEmail(email);
        user.setLastName(newLastName);

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        toTest.editLastName(email, newLastName);
        assertEquals(newLastName, user.getLastName());
    }

    @Test
    public void editContactNumber() {
        String newContactNumber = "newContactNumber";

        User user = new User();
        String email = user.getEmail();
        user.setEmail(email);
        user.setContactNumber(newContactNumber);

        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        toTest.editLastName(email, newContactNumber);
        assertEquals(newContactNumber, user.getContactNumber());
    }
}
