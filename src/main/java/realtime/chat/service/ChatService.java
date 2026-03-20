package realtime.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import realtime.chat.dto.ChatMessageDto;
import realtime.chat.model.MessageEntity;
import realtime.chat.repository.MessageRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository repository;

    public ChatMessageDto processAndSaveMessage(ChatMessageDto dto, String username) {
        MessageEntity entity = MessageEntity.builder()
                .sender(username)
                .content(dto.content())
                .type(dto.type())
                .timestamp(LocalDateTime.now())
                .build();

        repository.save(entity);

        return new ChatMessageDto(
                entity.getSender(),
                entity.getContent(),
                entity.getType(),
                entity.getTimestamp()
        );
    }
}