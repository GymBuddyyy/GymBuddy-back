package kr.gymbuddyback.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepDataModel {

    private Long id;
    private Long user_id;

    private LocalDate date;
    private Long steps;
    private Double calories;
    private LocalDate create_date;
    private Double distance;
    private Long height;

}
