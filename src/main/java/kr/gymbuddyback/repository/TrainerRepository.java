package kr.gymbuddyback.repository;

import kr.gymbuddyback.entity.Trainer;
import kr.gymbuddyback.repository.custom.TrainerCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long>, TrainerCustom {

}
