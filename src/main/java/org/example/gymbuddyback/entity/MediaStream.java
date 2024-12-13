package org.example.gymbuddyback.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collation = "mediaStream")
public class MediaStream {
    @Id
    private String id;

    private String roomId;
    private boolean isAudio;
    private boolean isVideo;
}
