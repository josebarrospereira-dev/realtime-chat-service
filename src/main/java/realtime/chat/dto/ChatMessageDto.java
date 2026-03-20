package realtime.chat.dto;

import java.time.LocalDateTime;

public record ChatMessageDto(
    String sender,
    String content,
    LocalDateTime timestamp
) {}
