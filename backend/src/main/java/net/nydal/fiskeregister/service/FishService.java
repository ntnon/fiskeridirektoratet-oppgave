package net.nydal.fiskeregister.service;

import net.nydal.fiskeregister.model.Fish;
import net.nydal.fiskeregister.repository.FishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FishService {

    private final FishRepository fishRepository;

    public FishService(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    public Fish createFish(Fish fish) {
        return fishRepository.save(fish);
    }

    public List<Fish> getAllFish() {
        return fishRepository.findAll();
    }

    public List<Fish>getFishBySpecies(String species) {
        return fishRepository.findBySpecies(species);
    }

    public List<String> getAllSpecies() {
        return fishRepository.findAllSpecies();
    }

    public Optional<Fish> getFishById(Long id) {
        return fishRepository.findById(id);
    }
    

    public Optional<Fish> updateFish(Long id, Fish fishDetails) {
        return fishRepository.findById(id)
                .map(fish -> {
                    fish.setName(fishDetails.getName());
                    fish.setSpecies(fishDetails.getSpecies());
                    fish.setLengthCm(fishDetails.getLengthCm());
                    fish.setWeightKg(fishDetails.getWeightKg());
                    return fishRepository.save(fish);
                });
    }

    public boolean deleteFish(Long id) {
        return fishRepository.findById(id)
                .map(fish -> {
                    fishRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}