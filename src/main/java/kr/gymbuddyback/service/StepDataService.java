package kr.gymbuddyback.service;

import kr.gymbuddyback.entity.StepDataEntity;
import kr.gymbuddyback.repository.StepDataRepository;

import java.util.List;
import java.util.Optional;

public interface StepDataService {

    List<StepDataEntity> findAll();

    StepDataEntity save(StepDataEntity stepDataEntity);

    Optional<StepDataEntity> findById(Long id);

    Boolean existsById(Long id);

    Boolean deleteById(Long id);

    Long count();

}
