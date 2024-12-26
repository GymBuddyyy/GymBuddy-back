package kr.gymbuddyback.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GymModel {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone_number;

    private Long user_id;

}
