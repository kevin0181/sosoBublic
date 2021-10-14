package soso.sosoproject.controller.message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    //chat example
    @GetMapping("/message")
    public String msgPage() {
        return "/user/message";
    }

    //chat example
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Chat chat(Chat chat) throws Exception {
        return new Chat(chat.getName(), chat.getMessage());
    }
}
