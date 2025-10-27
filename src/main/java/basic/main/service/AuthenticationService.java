package basic.main.service;

import basic.main.context.UserContext;
import basic.main.dto.JwtResponse;
import basic.main.dto.UserLoginDto;
import basic.main.dto.UserRegisterDto;
import basic.main.entity.User;
import basic.main.enums.Role;
import basic.main.repository.UserRepository;
import basic.main.security.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final UserContext userContext;

    public void registerUser(UserRegisterDto dto) {
        User user = new User(dto.userName(), dto.email(), dto.password(), Role.USER, bCryptPasswordEncoder);
        userRepository.save(user);
    }

    public JwtResponse login(UserLoginDto dto) {
        User user = userRepository.findByUserName(dto.login())
                .or(() -> userRepository.findByEmail(dto.login()))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        if (!user.validatePassword(dto.password(), bCryptPasswordEncoder)) {
            throw new IllegalArgumentException("Wrong credentials");
        }

        Map<String, Object> claims = Map.of(
                "role", "ROLE_" + user.getRole().name(),
                "userId", user.getId()
        );

        String accessToken = jwtService.generateAccessToken(user.getUserName(), claims);
        String refreshToken = jwtService.generateRefreshToken(user.getUserName());
        return new JwtResponse(accessToken, refreshToken);

    }

}
