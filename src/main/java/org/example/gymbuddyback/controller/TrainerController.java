package org.example.gymbuddyback.controller;


import lombok.RequiredArgsConstructor;
import org.example.gymbuddyback.entity.TrainerOffDays;
import org.example.gymbuddyback.service.TrainerService;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService trainerService;


    @PostMapping("/off-days")
    public List<TrainerOffDays> addOffDays(
            @RequestHeader("trainerId") Long trainerId,
            @RequestBody List<OffDayRequest> offDays
    ) {

        List<LocalDate> parsedOffDays = offDays.stream()
                .map(offDayRequest -> LocalDate.parse(offDayRequest.getDate()))
                .collect(Collectors.toList());

        return trainerService.addOffDays(trainerId, parsedOffDays);
    }

    public static class OffDayRequest {
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}