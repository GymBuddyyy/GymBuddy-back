package kr.gymbuddyback.service.impl;

import kr.gymbuddyback.entity.GymEntity;
import kr.gymbuddyback.repository.GymRepository;
import kr.gymbuddyback.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GymServiceImpl implements GymService {
    private final GymRepository gymRepository;

    @Override
    public List<GymEntity> findAll() {
        return gymRepository.findAll();

    }

    @Override
    public GymEntity save(GymEntity gymEntity) {
        return gymRepository.save(gymEntity);
    }

    @Override
    public Optional<GymEntity> findById(Long id) {
        return gymRepository.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return gymRepository.existsById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        if(gymRepository.existsById(id)) {
            gymRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Long count() {
        return gymRepository.count();
    }

}
