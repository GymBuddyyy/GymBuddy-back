package org.example.gymbuddyback.repository.custom;

import org.example.gymbuddyback.entity.TrainerOffDays;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TrainerCustom {
     List<TrainerOffDays> insertOffDays(Long trainerId, List<LocalDate> offDays);
}
