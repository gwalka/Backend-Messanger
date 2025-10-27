package basic.main.dto;

import lombok.Builder;

@Builder
public record MessageDto(
        String content,
        String sender,
        Long chatId,
        Long senderId
) {
}
