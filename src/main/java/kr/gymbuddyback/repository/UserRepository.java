package kr.gymbuddyback.repository;


import kr.gymbuddyback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByEmail (String email);



}
