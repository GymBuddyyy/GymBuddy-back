/*
package org.example.gymbuddyback.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "chat")
public class Chat {
    @Id
    private String id;

    private Long senderId;
    private Long receiverId;
    private Long roomId;
    private String message;
    private MessageType type;
    private boolean isRead;
    private LocalDateTime time;
    private String fileUrl;
    private String fileType;
    private Long fileSize;

}
*/
