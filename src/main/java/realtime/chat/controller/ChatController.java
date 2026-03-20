package realtime.chat.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import realtime.chat.dto.ChatMessageDto;
import realtime.chat.service.ChatService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessage, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        String username = principal.getName();
        return chatService.processAndSaveMessage(chatMessage, username);
    }

}
