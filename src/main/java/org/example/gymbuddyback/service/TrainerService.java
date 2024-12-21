package org.example.gymbuddyback.service;

import org.example.gymbuddyback.entity.TrainerOffDays;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TrainerService {
    List<TrainerOffDays> addOffDays(Long trainerId, List<LocalDate> offDays);

}
