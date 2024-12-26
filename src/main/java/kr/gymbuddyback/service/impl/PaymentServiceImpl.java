package kr.gymbuddyback.service.impl;

import kr.gymbuddyback.entity.PaymentEntity;
import kr.gymbuddyback.repository.PaymentRepository;
import kr.gymbuddyback.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;


    @Override
    public List<PaymentEntity> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public PaymentEntity save(PaymentEntity payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<PaymentEntity> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return paymentRepository.existsById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        if(paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Long count() {
        return paymentRepository.count();
    }
}
