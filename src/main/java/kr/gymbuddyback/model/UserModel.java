package kr.gymbuddyback.model;

import kr.gymbuddyback.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Long id;

    private String name;

    private String email;

    private String password;

    private Long age;

    private Long gender;

    private String address;

    private String role;

    private String interested;

    private boolean enabled;



    public static UserModel toUserModel (UserEntity userEntity) {
        return UserModel.builder()
                .age(userEntity.getAge())
                .address(userEntity.getAddress())
                .email(userEntity.getEmail())
                .gender(userEntity.getGender())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .enabled(userEntity.isEnabled())
                .build();
    }

}
