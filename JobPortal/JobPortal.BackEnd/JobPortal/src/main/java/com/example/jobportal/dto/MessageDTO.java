package com.example.jobportal.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDTO {
    private Long id;
    private String senderEmail;
    private String recipientEmail;
    private String content;
    private boolean read;
    private byte[] attachment; // Include attachment data
    private String attachmentMimeType; // Include MIME type
    private String attachmentName; // Include original attachment name
}
