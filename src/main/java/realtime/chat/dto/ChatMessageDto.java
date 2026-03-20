package realtime.chat.dto;

import java.time.LocalDateTime;

public record ChatMessageDto(
    Long id,
    String sender,
    String content,
    LocalDateTime timestamp
) {}
