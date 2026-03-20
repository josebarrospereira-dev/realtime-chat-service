package realtime.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import realtime.chat.dto.ChatMessageDto;
import realtime.chat.model.MessageEntity;
import realtime.chat.model.UserEntity;
import realtime.chat.repository.MessageRepository;
import realtime.chat.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatMessageDto processAndSaveMessage(ChatMessageDto dto, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MessageEntity entity = messageRepository.save(MessageEntity.builder()
                .sender(user)
                .content(dto.content())
                .timestamp(LocalDateTime.now())
                .build());

        return new ChatMessageDto(entity.getId(), username, entity.getContent(), entity.getTimestamp());
    }
}