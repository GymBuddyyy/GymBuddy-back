package org.example.gymbuddyback.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gymbuddyback.entity.TrainerOffDays;
import org.example.gymbuddyback.repository.TrainerRepository;
import org.example.gymbuddyback.repository.custom.TrainerCustom;
import org.example.gymbuddyback.service.TrainerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
