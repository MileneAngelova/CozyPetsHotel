package bg.softuni.cozypetshotel.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void testRegister_ValidInput() throws Exception {
        mockMvc.perform(post("/register")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("email", "petar@abv.bg")
                        .param("password", "1234")
                        .param("confirmPassword", "1234")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Optional<User> userOpt = userRepository.findByEmail("petar@abv.bg");

        Assertions.assertTrue(userOpt.isPresent());

        User user = userOpt.get();

        Assertions.assertEquals("Petar", user.getFirstName());
        Assertions.assertEquals("Petrov", user.getLastName());

        Assertions.assertTrue(passwordEncoder.matches("1234", user.getPassword()));
    }
}
