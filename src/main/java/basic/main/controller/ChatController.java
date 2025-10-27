package basic.main.controller;

import basic.main.dto.ChatResponseDto;
import basic.main.dto.UserSearchDto;
import basic.main.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/search")
    public ResponseEntity<List<UserSearchDto>> searchUsers(@RequestParam String query) {
        List<UserSearchDto> users = chatService.findUser(query);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDto> createChat(@RequestParam Long secondUserId) {
        ChatResponseDto chat = chatService.createChat(secondUserId);
        return ResponseEntity.ok(chat);
    }

}
