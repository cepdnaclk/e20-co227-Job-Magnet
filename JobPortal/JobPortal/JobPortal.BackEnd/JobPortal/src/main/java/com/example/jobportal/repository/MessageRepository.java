package com.example.jobportal.repository;

import com.example.jobportal.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRecipientEmailAndReadIsFalse(String recipientEmail);

    List<Message> findBySenderEmailAndRecipientEmailOrSenderEmailAndRecipientEmailOrderByIdAsc(
            String senderEmail1, String recipientEmail1, String senderEmail2, String recipientEmail2);

    List<Message> findBySenderEmailAndRecipientEmailAndReadIsFalse(
            String senderEmail, String recipientEmail);
}
