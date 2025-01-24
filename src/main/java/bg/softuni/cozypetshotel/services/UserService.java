package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.RegisterDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.repositories.RoleRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.session.AppUserDetails;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterDTO registerDTO) {
        User newUser = this.modelMapper.map(registerDTO, User.class);
        Role newUserRole = this.roleRepository.findByRole(RoleNameEnum.USER);
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUser.getRoles().add(newUserRole);

        userRepository.save(newUser);
    }
}

