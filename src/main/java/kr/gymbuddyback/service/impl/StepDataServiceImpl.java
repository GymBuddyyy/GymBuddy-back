package kr.gymbuddyback.service.impl;

import kr.gymbuddyback.entity.StepDataEntity;
import kr.gymbuddyback.repository.StepDataRepository;
import kr.gymbuddyback.service.StepDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StepDataServiceImpl implements StepDataService {
    private StepDataRepository stepDataRepository;

    @Override
    public List<StepDataEntity> findAll() {
        return stepDataRepository.findAll();
    }

    @Override
    public StepDataEntity save(StepDataEntity stepDataEntity) {
        return stepDataRepository.save(stepDataEntity);
    }

    @Override
    public Optional<StepDataEntity> findById(Long id) {
        return stepDataRepository.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return stepDataRepository.existsById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        if(stepDataRepository.existsById(id)) {
            stepDataRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Long count() {
        return stepDataRepository.count();
    }
}
