package kr.gymbuddyback.service;

import kr.gymbuddyback.entity.TrainerOffDays;

import java.time.LocalDate;
import java.util.List;

public interface TrainerService {
    List<TrainerOffDays> addOffDays(Long trainerId, List<LocalDate> offDays);

}
