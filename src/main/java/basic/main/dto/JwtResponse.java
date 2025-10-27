package basic.main.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}
