package kr.gymbuddyback.repository;

import kr.gymbuddyback.entity.StepDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepDataRepository extends JpaRepository<StepDataEntity, Long> {

}
