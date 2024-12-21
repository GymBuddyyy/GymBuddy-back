package org.example.gymbuddyback.repository;

import org.example.gymbuddyback.entity.Trainer;
import org.example.gymbuddyback.repository.custom.TrainerCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long>, TrainerCustom {

}
