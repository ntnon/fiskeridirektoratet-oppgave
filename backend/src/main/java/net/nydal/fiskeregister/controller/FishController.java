package net.nydal.fiskeregister.controller;

import net.nydal.fiskeregister.model.Fish;
import net.nydal.fiskeregister.service.FishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/fish")
@Validated
public class FishController {

    private final FishService fishService;

    public FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @PostMapping
    public ResponseEntity<Fish> createFish(@Valid @RequestBody Fish fish) {
        Fish savedFish = fishService.createFish(fish);
        return new ResponseEntity<>(savedFish, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Fish> getAllFish() {
        return fishService.getAllFish();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fish> getFishById(@PathVariable Long id) {
        return fishService.getFishById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fish> updateFish(@PathVariable Long id, @Valid @RequestBody Fish fishDetails) {
        return fishService.updateFish(id, fishDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFish(@PathVariable Long id) {
        boolean deleted = fishService.deleteFish(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/species")
    public List<String> getAllSpecies() {
        return fishService.getAllSpecies();
    }

    @GetMapping("/species/{species}")
    public List<Fish> getFishBySpecies(@PathVariable String species) {
        return fishService.getFishBySpecies(species);
    }
}