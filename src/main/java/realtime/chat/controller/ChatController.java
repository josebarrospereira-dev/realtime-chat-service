package realtime.chat.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import realtime.chat.dto.ChatMessageDto;
import realtime.chat.model.MessageEntity;
import realtime.chat.repository.MessageRepository;
import realtime.chat.service.ChatService;

import java.security.Principal;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final MessageRepository messageRepository;

    private static final int PAGE_SIZE = 20;

    @GetMapping("/messages")
    @ResponseBody
    public ResponseEntity<Page<ChatMessageDto>> getMessages(
            @RequestParam(defaultValue = "0") int page) {

        Page<ChatMessageDto> result = messageRepository
                .findAllPaged(PageRequest.of(page, PAGE_SIZE))
                .map(m -> new ChatMessageDto(
                        m.getId(),
                        m.getSender().getUsername(),
                        m.getContent(),
                        m.getTimestamp()));

        return ResponseEntity.ok(result);
    }

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
