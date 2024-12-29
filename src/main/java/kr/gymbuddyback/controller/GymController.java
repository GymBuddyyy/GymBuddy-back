package kr.gymbuddyback.controller;

import kr.gymbuddyback.entity.GymEntity;
import kr.gymbuddyback.service.GymService;
import kr.gymbuddyback.service.impl.GymServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController {

    private final GymService gymService;



    @GetMapping("")
    public List<GymEntity> findAll() {
        return gymService.findAll();
    }

    @PostMapping("")
    public GymEntity save(GymEntity gymEntity) {
        return gymService.save(gymEntity);
    }

    @GetMapping("/gym/{id}")
    public Optional<GymEntity> findById(@PathVariable Long id) {
        return gymService.findById(id);
    }

    @GetMapping("/{id}")
    public Boolean existsById(@PathVariable Long id) {
        return gymService.existsById(id);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable Long id) {
        return gymService.deleteById(id);
    }

    @GetMapping("/count")
    public Long count() {
        return gymService.count();
    }
}
