package kr.gymbuddyback.service.impl;

import kr.gymbuddyback.repository.TrainerRepository;
import kr.gymbuddyback.service.TrainerService;
import lombok.RequiredArgsConstructor;
import kr.gymbuddyback.entity.TrainerOffDays;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;

    @Override
    public List<TrainerOffDays> addOffDays(Long trainerId, List<LocalDate> offDays) {
        return trainerRepository.insertOffDays(trainerId, offDays);
    }
}
