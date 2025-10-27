package basic.main.controller;

import basic.main.dto.MessageDto;
import basic.main.security.jwt.JwtService;
import basic.main.service.ChatService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;
    private final JwtService jwtService;

    @MessageMapping("/chat/{chatId}/sendMessage")
    @SendTo("/topic/chat/{chatId}")
    public MessageDto sendMessage(
            @DestinationVariable Long chatId,
            @Payload String rawPayload // теперь это JSON
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(rawPayload);
            String content = json.get("content").asText();
            String token = json.get("token").asText();

            if (token == null || token.isEmpty()) {
                throw new RuntimeException("Token missing in payload");
            }

            Claims claims = jwtService.parseToken(token);
            Long currentUserId = claims.get("userId", Long.class);

            return chatService.processMessage(chatId, content, currentUserId);
        } catch (Exception e) {
            throw new RuntimeException("Invalid message format", e);
        }
    }
}
