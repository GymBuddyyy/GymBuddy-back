package kr.gymbuddyback.entity;


import jakarta.persistence.*;
import kr.gymbuddyback.model.UserModel;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Table(name = "users")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity implements UserDetails {
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


    @Builder
    public UserEntity(String name, String email, String password, Long age, Long gender, String address, String role, String interested, boolean enabled, Long imageId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.role = role;
        this.interested = interested;
        this.enabled = enabled;
    }

    public static UserEntity toUserEntity(UserModel userModel) {
        return UserEntity.builder()
                .name(userModel.getName())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .age(userModel.getAge())
                .gender(userModel.getGender())
                .address(userModel.getAddress())
                .role("ROLE_USER")
                .interested(userModel.getInterested())
                .enabled(true)
                .build();
    }

    @Override //권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    //사용자의 id 반환 (고유값)
    @Override
    public String getUsername() {
        return email;
    }

    //사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // true -> 만료되지 않았음
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; //true -> 잠금되지 않았음
    }

    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //true -> 만료되지 않았음
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true; //true -> 사용 가능
    }


}
