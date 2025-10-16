package net.nydal.fiskeregister.repository;

import net.nydal.fiskeregister.model.Fish;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class FishRepositoryTest {

    @Autowired
    public FishRepository fiskerRepository;

    @Test
    void testSaveAndFindFisker() {
        Fish fisk = new Fish();
        fisk.setName("Torsk");
        fisk.setSpecies("Gadus morhua");
        fisk.setLengthCm(60);
        fisk.setWeightKg(3.2);

        Fish saved = fiskerRepository.save(fisk);

        assertThat(fiskerRepository.findById(saved.getId())).isPresent();
        assertThat(fiskerRepository.findById(saved.getId()).get().getName()).isEqualTo("Torsk");
    }

    @Test
    void testUpdateFish() {
        Fish fish = new Fish();
        fish.setName("Laks");
        fish.setSpecies("Salmo salar");
        fish.setLengthCm(70);
        fish.setWeightKg(4.5);

        Fish saved = fiskerRepository.save(fish);
        saved.setWeightKg(5.0);

        Fish updated = fiskerRepository.save(saved);

        assertThat(updated.getWeightKg()).isEqualTo(5.0);
    }

    @Test
    void testDeleteFish() {
        Fish fish = new Fish();
        fish.setName("Makrell");
        fish.setSpecies("Scomber scombrus");
        fish.setLengthCm(30);
        fish.setWeightKg(0.5);

        Fish saved = fiskerRepository.save(fish);
        fiskerRepository.deleteById(saved.getId());

        assertThat(fiskerRepository.findById(saved.getId())).isNotPresent();
    }

    @Test
    void testIdIsGenerated() {
        Fish fish = new Fish();
        fish.setName("Sei");
        fish.setSpecies("Pollachius virens");
        fish.setLengthCm(50);
        fish.setWeightKg(2.5);

        Fish saved = fiskerRepository.save(fish);

        assertThat(saved.getId()).isNotNull();
    }
}