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
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long order_id;//결제자
    private String gym_name; // 결제 gym

    private String pay_key; // 결제 키
    private String pay_order_id; // 주문자??
    private Long amount;
    private String pg; //결제 대행사
    private LocalDate approved_time;
}
