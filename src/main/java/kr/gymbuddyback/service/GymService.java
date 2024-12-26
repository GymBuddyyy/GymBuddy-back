package kr.gymbuddyback.service;

import kr.gymbuddyback.entity.GymEntity;
import kr.gymbuddyback.model.GymModel;

import java.util.List;
import java.util.Optional;

public interface GymService {

    List<GymEntity> findAll();

    GymEntity save(GymEntity GymEntity);

    Optional<GymEntity> findById(Long id);

    Boolean existsById(Long id);

    Boolean deleteById(Long id);

    Long count();

}
