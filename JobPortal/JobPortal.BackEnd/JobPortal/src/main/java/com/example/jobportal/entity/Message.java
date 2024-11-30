package com.example.jobportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id") // Custom column name for the ID
    private Long id;

    @Column(name = "sender_email", nullable = false)
    private String senderEmail;

    @Column(name = "recipient_email", nullable = false)
    private String recipientEmail;

    @Column(name = "message_content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Lob
    @Column(name = "attachment_data", columnDefinition = "LONGBLOB")
    private byte[] attachment;

    @Column(name = "attachment_mime_type") // Add this field for MIME type
    private String attachmentMimeType;

    @Column(name = "attachment_name") // Add this field for attachment name
    private String attachmentName;

    @Column(name = "is_read", nullable = false)
    private boolean read = false;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime timestamp;

    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    public Message(String senderEmail, String recipientEmail, String content, byte[] attachment, String attachmentMimeType, String attachmentName) {
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.content = content;
        this.attachment = attachment;
        this.attachmentMimeType = attachmentMimeType; // Set the MIME type
        this.attachmentName = attachmentName; // Set the original attachment name
        this.timestamp = LocalDateTime.now();
        this.read = false;
    }
}
