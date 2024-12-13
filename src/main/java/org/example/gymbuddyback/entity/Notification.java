package org.example.gymbuddyback.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collation = "notification")
public class Notification {
    @Id
    private String id;
    private String message;
    private boolean isRead;
}
