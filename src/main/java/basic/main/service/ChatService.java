package basic.main.service;

import basic.main.context.UserContext;
import basic.main.dto.ChatResponseDto;
import basic.main.dto.MessageDto;
import basic.main.dto.UserSearchDto;
import basic.main.entity.Chat;
import basic.main.entity.Message;
import basic.main.entity.User;
import basic.main.repository.ChatRepository;
import basic.main.repository.MessageRepository;
import basic.main.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final UserContext context;
    private final MessageRepository messageRepository;

    public List<UserSearchDto> findUser(String search) {
        List<User> users = userRepository.findByUserNameContaining(search);

        if (users.isEmpty()) {
            return Collections.emptyList();
        }

        return users.stream()
                .map(user -> new UserSearchDto(user.getId(), user.getUserName()))
                .toList();
    }

    public ChatResponseDto createChat(Long secondUserId) {
        Long userId = context.getCurrentUserId();
        User firstUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        User secondUser = userRepository.findById(secondUserId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Optional<Chat> existingChatOpt = chatRepository.findByUsersIds(userId, secondUserId);
        System.out.println("Existing chat found: " + existingChatOpt.isPresent());

        if (existingChatOpt.isPresent()) {
            Chat existingChat = existingChatOpt.get();
            return ChatResponseDto.builder()
                    .id(existingChat.getId())
                    .firstUserId(userId)
                    .secondUserId(secondUserId)
                    .build();
        }

        Chat newChat = Chat.builder()
                .firstUser(firstUser)
                .secondUser(secondUser)
                .build();

        Chat savedChat = chatRepository.save(newChat);

        return ChatResponseDto.builder()
                .id(savedChat.getId())
                .firstUserId(userId)
                .secondUserId(secondUserId)
                .build();
    }

    public MessageDto processMessage(Long chatId, String content, Long currentUserId) {
        System.out.println("=== DEBUG: currentUserId = " + currentUserId);

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + currentUserId));

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Чат не найден: " + chatId));

        Message message = Message.builder()
                .chat(chat)
                .text(content)
                .author(user)
                .createdAt(LocalDateTime.now())
                .build();

        messageRepository.save(message);

        return MessageDto.builder()
                .chatId(chatId)
                .content(content)
                .sender(user.getUserName())
                .senderId(user.getId())
                .build();
    }


}
