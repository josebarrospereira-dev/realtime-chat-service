package realtime.chat.dto;

import realtime.chat.model.enums.MessageType;

import java.time.LocalDateTime;

public record ChatMessageDto(
    String sender,
    String content,
    MessageType type,
    LocalDateTime timestamp
) {}
