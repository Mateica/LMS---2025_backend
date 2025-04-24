package main.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import main.model.File;
import main.model.Message;
import main.repository.MessageRepository;
import main.repository.RegisteredUserRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private RegisteredUserRepository userRepo;
	
	public Message sendMessage(Long senderId, Long receiverId, String content, List<MultipartFile> files) throws IOException {
		Message message =  new Message();
		
		message.setTimePublished(LocalDateTime.now());
		message.setContent(content);
		message.setSender(userRepo.findById(senderId).orElseThrow());
		message.setReceiver(userRepo.findById(receiverId).orElseThrow());
		
		if(files != null) {
			for(MultipartFile f : files) {
				String path = saveFile(f);
				
				File attachment = new File();
				
				attachment.setUrl(path);
				attachment.setMessage(message);
				attachment.setDescription(content);
				
				message.getAttachments().add(attachment);
			}
		}
		
		return this.messageRepo.save(message);
	}
	
	private String saveFile(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads").resolve(fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + fileName;
	}
}
