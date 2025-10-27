package basic.main.dto;

import lombok.Builder;

@Builder
public record ChatResponseDto(
        Long id,
        Long firstUserId,
        Long secondUserId
) {
}
