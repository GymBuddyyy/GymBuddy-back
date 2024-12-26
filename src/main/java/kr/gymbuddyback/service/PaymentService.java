package kr.gymbuddyback.service;

import kr.gymbuddyback.entity.PaymentEntity;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentEntity> findAll();

    PaymentEntity save(PaymentEntity payment);

    Optional<PaymentEntity> findById(Long id);

    Boolean existsById(Long id);

    Boolean deleteById(Long id);

    Long count();


}
