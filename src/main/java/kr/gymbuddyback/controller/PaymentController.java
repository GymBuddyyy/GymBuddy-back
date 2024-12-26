package kr.gymbuddyback.controller;

import kr.gymbuddyback.entity.PaymentEntity;
import kr.gymbuddyback.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController  {

    private final PaymentService paymentService;

    @GetMapping("")
    public List<PaymentEntity> findAll() {
        return paymentService.findAll();
    }

    @PostMapping("")
    public PaymentEntity save(PaymentEntity payment) {
        return paymentService.save(payment);
    }

    @GetMapping("/payment/{id}")
    public Optional<PaymentEntity> findById(@PathVariable Long id) {
        return paymentService.findById(id);
    }

    @GetMapping("/{id}")
    public Boolean existsById(@PathVariable Long id) {
        return paymentService.existsById(id);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable Long id) {
        return paymentService.deleteById(id);
    }

    @GetMapping("/count")
    public Long count() {
        return paymentService.count();
    }
}
