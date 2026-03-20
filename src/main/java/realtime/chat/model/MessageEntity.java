package realtime.chat.model;

import jakarta.persistence.*;
import lombok.*;
import realtime.chat.model.enums.MessageType;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private LocalDateTime timestamp;
}