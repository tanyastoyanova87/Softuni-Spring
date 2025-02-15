package Philately.user.service;

import Philately.user.model.User;
import Philately.user.repository.UserRepository;
import Philately.web.dto.LoginRequest;
import Philately.web.dto.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest registerRequest) {
        Optional<User> optionalUser = this.userRepository.findByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail());

        if (optionalUser.isPresent()) {
            throw new RuntimeException("User with this username/email already exist");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        this.userRepository.save(user);
    }

    public User loginUser(LoginRequest loginRequest) {
        Optional<User> optionalUser = this.userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Incorrect username or password");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect username or password");
        }

        return user;
    }

    public User getById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with id %s does not exist.".formatted(userId)));
    }
}
