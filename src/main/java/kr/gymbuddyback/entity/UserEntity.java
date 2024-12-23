package kr.gymbuddyback.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Long age;

    @Column(name = "gender")
    private Long gender;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    private String role;

    @Column(name = "interested")
    private String interested;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "image_id")
    private Long ImageId;

}
