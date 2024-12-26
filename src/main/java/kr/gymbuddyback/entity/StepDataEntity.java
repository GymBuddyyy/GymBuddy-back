package kr.gymbuddyback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="step")
public class StepDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    private LocalDate date;
    private Long steps;
    private Double calories;
    private LocalDate create_date;
    private Double distance;
    private Long height;


}
