package com.example.blps.services;

import com.example.blps.exceptions.AuthorizeException;
import com.example.blps.exceptions.InvalidDataException;
import com.example.blps.exceptions.NoSuchUserException;
import com.example.blps.model.User;
import com.example.blps.model.dto.LoginRequestDTO;
import com.example.blps.model.dto.RegisterRequestDTO;
import com.example.blps.model.enums.RoleName;
import com.example.blps.repositories.RoleRepository;
import com.example.blps.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User authUser(LoginRequestDTO loginRequestDTO) throws NoSuchUserException, AuthorizeException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new AuthorizeException("Ошибка авторизации");
        }
        User user = userRepository.findUserByEmail(loginRequestDTO.getEmail());
        if (user == null) {
            throw new NoSuchUserException("Пользователя с таким логином не существует");
        }
        return user;
    }

    public User registerUser(RegisterRequestDTO registerRequestDTO) throws InvalidDataException {
        validateRegisterDTO(registerRequestDTO);
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new InvalidDataException("Пользователь с таким email уже существует");
        }
        User user = new User(
                registerRequestDTO.getName(),
                registerRequestDTO.getEmail(),
                passwordEncoder.encode(registerRequestDTO.getPassword()),
                roleRepository.findByName(RoleName.USER)
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