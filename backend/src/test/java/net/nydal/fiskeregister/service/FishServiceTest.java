package net.nydal.fiskeregister.service;

import net.nydal.fiskeregister.model.Fish;
import net.nydal.fiskeregister.repository.FishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FishServiceTest {

    @Mock
    private FishRepository fishRepository;

    @InjectMocks
    private FishService fishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFish() {
        Fish fish1 = new Fish();
        fish1.setName("Torsk");
        fish1.setSpecies("Gadus morhua");
        fish1.setLengthCm(60);
        fish1.setWeightKg(3.2);

        Fish fish2 = new Fish();
        fish2.setName("Laks");
        fish2.setSpecies("Salmo salar");
        fish2.setLengthCm(75);
        fish2.setWeightKg(4.5);

        when(fishRepository.findAll()).thenReturn(Arrays.asList(fish1, fish2));

        List<Fish> fishList = fishService.getAllFish();

        assertThat(fishList).hasSize(2);
        assertThat(fishList).extracting(Fish::getName).containsExactly("Torsk", "Laks");
        verify(fishRepository, times(1)).findAll();
    }

    @Test
    void testGetFishByIdFound() {
        Fish fish = new Fish();
        fish.setName("Torsk");
        fish.setSpecies("Gadus morhua");
        fish.setLengthCm(60);
        fish.setWeightKg(3.2);

        when(fishRepository.findById(1L)).thenReturn(Optional.of(fish));

        Optional<Fish> result = fishService.getFishById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Torsk");
        verify(fishRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFishByIdNotFound() {
        when(fishRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Fish> result = fishService.getFishById(1L);

        assertThat(result).isEmpty();
        verify(fishRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateFish() {
        Fish fish = new Fish();
        fish.setName("Laks");
        fish.setSpecies("Salmo salar");
        fish.setLengthCm(70);
        fish.setWeightKg(4.0);

        when(fishRepository.save(fish)).thenReturn(fish);

        Fish created = fishService.createFish(fish);

        assertThat(created.getName()).isEqualTo("Laks");
        verify(fishRepository, times(1)).save(fish);
    }

    @Test
    void testDeleteFish() {
        Fish fish = new Fish();
        fish.setName("Torsk");
        fish.setSpecies("Gadus morhua");
        fish.setLengthCm(60);
        fish.setWeightKg(3.2);

        when(fishRepository.findById(1L)).thenReturn(Optional.of(fish));

        fishService.deleteFish(1L);

        verify(fishRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateFishWhenFound() {
        Fish existingFish = new Fish();
        existingFish.setName("Torsk");

        Fish updatedDetails = new Fish();
        updatedDetails.setName("Laks");

        when(fishRepository.findById(1L)).thenReturn(Optional.of(existingFish));
        when(fishRepository.save(any(Fish.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Fish> result = fishService.updateFish(1L, updatedDetails);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Laks");

        verify(fishRepository, times(1)).findById(1L);
        verify(fishRepository, times(1)).save(existingFish);
    }

    @Test
    void testUpdateFishWhenNotFound() {
        Fish updatedDetails = new Fish();
        updatedDetails.setName("Laks");

        when(fishRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Fish> result = fishService.updateFish(1L, updatedDetails);

        assertThat(result).isEmpty();
        verify(fishRepository, times(1)).findById(1L);
        verify(fishRepository, never()).save(any(Fish.class));
    }

    @Test
    void testGetFishBySpecies() {
        // Arrange: create some mock Fish objects
        Fish fish1 = new Fish();
        fish1.setName("Torsk");
        fish1.setSpecies("Gadus morhua");
        fish1.setLengthCm(60);
        fish1.setWeightKg(3.2);

        Fish fish2 = new Fish();
        fish2.setName("Torsk Junior");
        fish2.setSpecies("Gadus morhua");
        fish2.setLengthCm(30);
        fish2.setWeightKg(1.2);

        when(fishRepository.findBySpecies("Gadus morhua"))
                .thenReturn(Arrays.asList(fish1, fish2));

        List<Fish> result = fishService.getFishBySpecies("Gadus morhua");

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Fish::getName)
                .containsExactly("Torsk", "Torsk Junior");

        verify(fishRepository, times(1)).findBySpecies("Gadus morhua");
    }

    @Test
    void testGetAllSpecies() {
        List<String> speciesList = Arrays.asList("Gadus morhua", "Salmo salar", "Clupea harengus");
        when(fishRepository.findAllSpecies()).thenReturn(speciesList);

        List<String> result = fishService.getAllSpecies();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly("Gadus morhua", "Salmo salar", "Clupea harengus");

        verify(fishRepository, times(1)).findAllSpecies();
    }
}