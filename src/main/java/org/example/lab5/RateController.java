package org.example.lab5;

import from_lab_2.Rate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RateController {
    private final RateRepo rateRepo;
    public RateController(RateRepo rateRepo) {
        this.rateRepo = rateRepo;
    }
    @PostMapping
    public ResponseEntity<Rate> addRate(@RequestBody Rate rate) {
        return ResponseEntity.ok(rateRepo.save(rate));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        if(!rateRepo.existsById(id)) return ResponseEntity.notFound().build();
        rateRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/upd")
    public ResponseEntity<Rate> updateRate(@PathVariable Long id, @RequestBody Rate rate) {
        if(!rateRepo.existsById(id)) return ResponseEntity.notFound().build();
        rate.setId(id);
        return ResponseEntity.ok(rateRepo.save(rate));
    }
}
