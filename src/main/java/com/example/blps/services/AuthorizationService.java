package com.example.blps.services;

import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchUserException;
import com.example.blps.model.Role;
import com.example.blps.model.User;
import com.example.blps.model.dto.LoginRequestDTO;
import com.example.blps.model.dto.RegisterRequestDTO;
import com.example.blps.model.enums.RoleName;
import com.example.blps.repositories.RoleRepository;
import com.example.blps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User authUser(LoginRequestDTO loginRequestDTO) throws NoSuchUserException, InvalidDataException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        Optional<User> userO = userRepository.findByEmail(loginRequestDTO.getEmail());
        if (!userO.isPresent()) {
            throw new NoSuchUserException("Пользователя с таким логином не существует");
        }
        User user = userO.get();
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new InvalidDataException("Неправильный пароль");
        }
        return user;
    }

    public User registerUser(RegisterRequestDTO registerRequestDTO) throws InvalidDataException {
        validateRegisterDTO(registerRequestDTO);
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new InvalidDataException("Пользователь с таким email уже существует");
        }
        Optional<Role> roleO = roleRepository.findByName(RoleName.valueOf(registerRequestDTO.getName()));
        if (!roleO.isPresent()) {
            throw new InvalidDataException("Такой роли не существует");
        }
        User user = new User(
                registerRequestDTO.getName(),
                registerRequestDTO.getEmail(),
                passwordEncoder.encode(registerRequestDTO.getPassword()),
                roleO.get()
        );
        user = userRepository.save(user);
        return user;
    }

    private void validateRegisterDTO(RegisterRequestDTO registerRequestDTO) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (registerRequestDTO.getPassword() == null || registerRequestDTO.getPassword().equals("")
                || registerRequestDTO.getPassword().length() < 5) {
            message.append("Пароль должен состоять минимум из 5 символов");
            valid = false;
        }
        if (registerRequestDTO.getName() == null || registerRequestDTO.getName().equals("")) {
            message.append("ФИО не может быть пустым");
            valid = false;
        }
        if (registerRequestDTO.getEmail() == null || registerRequestDTO.getEmail().equals("")) {
            message.append("Email не может быть пустым");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }
}
