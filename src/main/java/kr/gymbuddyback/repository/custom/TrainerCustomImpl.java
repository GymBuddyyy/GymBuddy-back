package kr.gymbuddyback.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.gymbuddyback.entity.QTrainerOffDays;
import kr.gymbuddyback.entity.TrainerOffDays;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;


@RequiredArgsConstructor
public class TrainerCustomImpl implements TrainerCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public List<TrainerOffDays> insertOffDays(Long trainerId, List<LocalDate> offDays) {
        QTrainerOffDays qTrainerOffDays = QTrainerOffDays.trainerOffDays;

        List<LocalDate> existingOffDays = jpaQueryFactory.select(qTrainerOffDays.date)
                .from(qTrainerOffDays)
                .where(qTrainerOffDays.trainerId.eq(trainerId)
                        .and(qTrainerOffDays.date.in(offDays)))
                .fetch();

        // offDays에 대해 중복되지 않는 날짜들만 삽입
        for (LocalDate offDay : offDays) {
            if (!existingOffDays.contains(java.sql.Date.valueOf(offDay))) {
                TrainerOffDays trainerOffDay = TrainerOffDays.builder()
                        .trainerId(trainerId)
                        .date(Date.valueOf(offDay).toLocalDate())
                        .time(Time.valueOf(LocalTime.MIDNIGHT))
                        .build();
                entityManager.persist(trainerOffDay);
            }
        }
        entityManager.flush();

        // trainerId에 해당하는 모든 휴무일들을 반환
        return jpaQueryFactory.selectFrom(qTrainerOffDays)
                .where(qTrainerOffDays.trainerId.eq(trainerId))
                .fetch();
    }
}
