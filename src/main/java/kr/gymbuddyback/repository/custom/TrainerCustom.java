package kr.gymbuddyback.repository.custom;

import kr.gymbuddyback.entity.TrainerOffDays;

import java.time.LocalDate;
import java.util.List;

public interface TrainerCustom {
     List<TrainerOffDays> insertOffDays(Long trainerId, List<LocalDate> offDays);
}
