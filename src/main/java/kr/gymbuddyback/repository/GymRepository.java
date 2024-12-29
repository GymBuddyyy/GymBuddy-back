package kr.gymbuddyback.repository;

import kr.gymbuddyback.entity.GymEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GymRepository extends JpaRepository<GymEntity, Long> {

    @Query("SELECT g.name FROM GymEntity g")
    List<String> findAllNames();



}
