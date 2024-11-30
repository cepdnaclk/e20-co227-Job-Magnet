package com.example.jobportal.service;

import com.example.jobportal.entity.Message;
import com.example.jobportal.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(Message message) {
        return messageRepository.save(message); // Save the message to the database
    }

    public List<Message> getUnreadMessages(String recipientEmail) {
        return messageRepository.findByRecipientEmailAndReadIsFalse(recipientEmail);
    }

    public void markMessageAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setRead(true);
        messageRepository.save(message);
    }

    public List<Message> getConversationBetweenUsers(String user1Email, String user2Email) {
        return messageRepository.findBySenderEmailAndRecipientEmailOrSenderEmailAndRecipientEmailOrderByIdAsc(
                user1Email, user2Email, user2Email, user1Email);
    }

    public List<Message> getUnreadMessagesBetweenUsers(String senderEmail, String recipientEmail) {
        return messageRepository.findBySenderEmailAndRecipientEmailAndReadIsFalse(senderEmail, recipientEmail);
    }
    public Message findMessageById(Long id) {
        return messageRepository.findById(id).orElse(null); // Return null if not found
    }

    public void deleteMessage(Message message) {
        messageRepository.delete(message); // Delete the message
    }

}



