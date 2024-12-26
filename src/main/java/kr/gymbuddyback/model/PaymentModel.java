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
public class PaymentModel {

    private Long id;
    private Long order_id;//결제자
    private String gym_name; // 결제 gym

    private String pay_key; // 결제 키
    private String pay_order_id; // 주문자??
    private Long amount;
    private String pg; //결제 대행사
    private LocalDate approved_time;

}
