package kr.gymbuddyback.repository;

import kr.gymbuddyback.entity.GymEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends JpaRepository<GymEntity, Long> {


}
