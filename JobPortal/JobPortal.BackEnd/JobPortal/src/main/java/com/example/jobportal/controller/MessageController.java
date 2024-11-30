package com.example.jobportal.controller;

import com.example.jobportal.dto.MessageDTO;
import com.example.jobportal.entity.Message;
import com.example.jobportal.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public ResponseEntity<String> sendMessage(
            @RequestParam("senderEmail") String senderEmail,
            @RequestParam("recipientEmail") String recipientEmail,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Message message;

        // Initialize variables for file data
        byte[] fileBytes = null;
        String attachmentMimeType = null;
        String attachmentName = null;

        // Process the file if present
        if (file != null && !file.isEmpty()) {
            try {
                fileBytes = file.getBytes();
                attachmentMimeType = file.getContentType(); // Get the MIME type
                attachmentName = file.getOriginalFilename(); // Get the original filename
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing error: " + e.getMessage());
            }
        }

        // Create a new message entity
        message = new Message(senderEmail, recipientEmail, content, fileBytes, attachmentMimeType, attachmentName);

        try {
            // Save the message using the service
            messageService.sendMessage(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message: " + e.getMessage());
        }

        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping("/unread/{recipientEmail}")
    public ResponseEntity<List<MessageDTO>> getUnreadMessages(@PathVariable String recipientEmail) {
        List<Message> messages = messageService.getUnreadMessages(recipientEmail);
        List<MessageDTO> messageDTOs = messages.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(messageDTOs);
    }

    @PostMapping("/mark-as-read/{messageId}")
    public ResponseEntity<Void> markMessageAsRead(@PathVariable Long messageId) {
        messageService.markMessageAsRead(messageId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/conversation/{user1Email}/{user2Email}")
    public ResponseEntity<List<MessageDTO>> getConversationBetweenUsers(@PathVariable String user1Email, @PathVariable String user2Email) {
        List<Message> messages = messageService.getConversationBetweenUsers(user1Email, user2Email);
        List<MessageDTO> messageDTOs = messages.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(messageDTOs);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        try {
            Message message = messageService.findMessageById(id);
            if (message != null && !message.isRead()) {
                messageService.deleteMessage(message);
                return ResponseEntity.ok("Message deleted successfully");
            } else if (message != null && message.isRead()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot delete read message");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting message: " + e.getMessage());
        }
    }

    private MessageDTO convertToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setSenderEmail(message.getSenderEmail());
        messageDTO.setRecipientEmail(message.getRecipientEmail());
        messageDTO.setContent(message.getContent());
        messageDTO.setRead(message.isRead());
        messageDTO.setAttachment(message.getAttachment()); // Include attachment data
        messageDTO.setAttachmentMimeType(message.getAttachmentMimeType()); // Include MIME type
        messageDTO.setAttachmentName(message.getAttachmentName()); // Include attachment name
        return messageDTO;
    }
}
