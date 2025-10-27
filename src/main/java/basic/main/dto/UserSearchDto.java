package basic.main.dto;

import lombok.Builder;

@Builder
public record UserSearchDto(
        Long id,
        String username
) {
}
