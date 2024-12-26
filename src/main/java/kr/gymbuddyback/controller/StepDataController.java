package kr.gymbuddyback.controller;

import kr.gymbuddyback.entity.StepDataEntity;
import kr.gymbuddyback.repository.StepDataRepository;
import kr.gymbuddyback.service.StepDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/stepData")
@RequiredArgsConstructor
public class StepDataController {

    private StepDataService stepDataService;

    @GetMapping("")
    public List<StepDataEntity> findAll() {
        return stepDataService.findAll();
    }

    @PostMapping("")
    public StepDataEntity save(StepDataEntity stepDataEntity) {
        return stepDataService.save(stepDataEntity);
    }

    @GetMapping("/stepData/{id}")
    public Optional<StepDataEntity> findById(@PathVariable Long id) {
        return stepDataService.findById(id);
    }

    @GetMapping("/{id}")
    public Boolean existsById(@PathVariable Long id) {
        return stepDataService.existsById(id);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable Long id) {
        return stepDataService.deleteById(id);
    }

    @GetMapping("/count")
    public Long count() {
        return stepDataService.count();
    }
}
