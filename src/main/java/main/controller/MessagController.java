package main.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import main.dto.MessageDTO;
import main.dto.RegisteredUserDTO;
import main.model.Message;
import main.service.MessageService;

@RestController
@RequestMapping(path = "/api/messages")
public class MessagController {
	@Autowired
    private MessageService messageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageDTO> posaljiPoruku(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam String content,
            @RequestParam(required = false) List<MultipartFile> files
    ) throws IOException {
    	Message message = messageService.sendMessage(senderId, senderId, content, files);
        return new ResponseEntity<MessageDTO>(new MessageDTO(message.getTimePublished(), new RegisteredUserDTO(message.getSender().getUsername(), message.getSender().getPassword(), message.getSender().getEmail()),
        		new RegisteredUserDTO(message.getReceiver().getUsername(), message.getReceiver().getPassword(), message.getReceiver().getEmail()), message.getContent()), HttpStatus.OK);
    }
}
