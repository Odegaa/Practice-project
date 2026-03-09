package com.odegaa.practiceproject.configurations.auth;

import com.odegaa.practiceproject.advice.ApiResponse;
import com.odegaa.practiceproject.configurations.security.JwtProvider;
import com.odegaa.practiceproject.entities.Employee;
import com.odegaa.practiceproject.repositories.EmployeeRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(EmployeeRepository employeeRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = employeeRepository.findByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Пользователь не найдено: " + username);
        }
        return userDetails;
    }

    public ApiResponse<String> login(@Valid AuthDTO authDTO) {
        UserDetails userDetails = loadUserByUsername(authDTO.getUsername());
        Employee employee = (Employee) userDetails;

        if (!passwordEncoder.matches(authDTO.getPassword(), userDetails.getPassword())) {
            log.error("Неверный логин и пароль!");
            throw new ValidationException("Неверный логин и пароль!");
        }

        if (!userDetails.isEnabled()) {
            log.error("Ваш аккаунт заблокирован или деактивирован!");
            throw new ValidationException("Ваш аккаунт заблокирован или деактивирован!");
        }

        String token = jwtProvider.generateToken(employee.getUsername(), employee.getRoles());

        return ApiResponse.<String>builder()
                .success(true)
                .message("Добро пожаловать!")
                .data(token)
                .build();
    }
}
