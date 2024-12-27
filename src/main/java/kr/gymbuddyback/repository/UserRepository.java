package kr.gymbuddyback.repository;


import kr.gymbuddyback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByEmail (String email);

    UserEntity findByUserEmail(String email);
}
