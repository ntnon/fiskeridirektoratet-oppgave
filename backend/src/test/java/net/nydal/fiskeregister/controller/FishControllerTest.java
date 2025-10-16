package net.nydal.fiskeregister.controller;

import net.nydal.fiskeregister.model.Fish;
import net.nydal.fiskeregister.service.FishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

class FishControllerTest {

    private MockMvc mockMvc; // Mock MVC to simulate HTTP requests to the controller

    @Mock
    private FishService fishService; // Mocked service layer

    @InjectMocks
    private FishController fishController; // Controller under test

    private ObjectMapper objectMapper = new ObjectMapper(); // For converting objects to JSON

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fishController).build();
    }

    @Test
    void testCreateFish() throws Exception {
        // Create a sample fish
        Fish fish = new Fish();
        fish.setName("Torsk");
        fish.setSpecies("Gadus morhua");
        fish.setLengthCm(60);
        fish.setWeightKg(3.2);

        // Mock the service to return the same fish when createFish is called
        when(fishService.createFish(any(Fish.class))).thenReturn(fish);

        // Perform a POST request to /api/fish with the fish JSON
        mockMvc.perform(post("/api/fish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fish)))
                .andExpect(status().isCreated()) // Expect HTTP 201 Created
                .andExpect(jsonPath("$.name").value("Torsk")) // Verify JSON response
                .andExpect(jsonPath("$.species").value("Gadus morhua"));

        // Verify that the service was called once
        verify(fishService, times(1)).createFish(any(Fish.class));
    }

    @Test
    void testGetAllFish() throws Exception {
        Fish fish1 = new Fish();
        fish1.setName("Torsk");
        Fish fish2 = new Fish();
        fish2.setName("Laks");

        List<Fish> fishList = Arrays.asList(fish1, fish2);

        when(fishService.getAllFish()).thenReturn(fishList);

        mockMvc.perform(get("/api/fish"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Torsk"))
                .andExpect(jsonPath("$[1].name").value("Laks"));

        verify(fishService, times(1)).getAllFish();
    }

    @Test
    void testGetFishByIdFound() throws Exception {
        Fish fish = new Fish();
        fish.setName("Torsk");

        when(fishService.getFishById(1L)).thenReturn(Optional.of(fish));

        mockMvc.perform(get("/api/fish/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Torsk"));

        verify(fishService, times(1)).getFishById(1L);
    }

    @Test
    void testGetFishByIdNotFound() throws Exception {
        when(fishService.getFishById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/fish/1"))
                .andExpect(status().isNotFound());

        verify(fishService, times(1)).getFishById(1L);
    }

    @Test
    void testUpdateFishFound() throws Exception {
        Fish updatedFish = new Fish();
        updatedFish.setName("Laks");
        updatedFish.setSpecies("Salmo salar");
        updatedFish.setLengthCm(70);
        updatedFish.setWeightKg(4.5);

        when(fishService.updateFish(eq(1L), any(Fish.class))).thenReturn(Optional.of(updatedFish));

        mockMvc.perform(put("/api/fish/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFish)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laks"))
                .andExpect(jsonPath("$.species").value("Salmo salar"))
                .andExpect(jsonPath("$.lengthCm").value(70))
                .andExpect(jsonPath("$.weightKg").value(4.5));


        verify(fishService, times(1)).updateFish(eq(1L), any(Fish.class));
    }


    @Test
    void testUpdateFishNotFound() throws Exception {
        Fish updatedFish = new Fish();
        updatedFish.setName("Laks");
        updatedFish.setSpecies("Salmo salar");
        updatedFish.setLengthCm(70);
        updatedFish.setWeightKg(4.5);

        when(fishService.updateFish(eq(1L), any(Fish.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/fish/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFish)))
                .andExpect(status().isNotFound());

        verify(fishService, times(1)).updateFish(eq(1L), any(Fish.class));
    }

    @Test
    void testDeleteFishFound() throws Exception {
        when(fishService.deleteFish(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/fish/1"))
                .andExpect(status().isNoContent());

        verify(fishService, times(1)).deleteFish(1L);
    }

    @Test
    void testDeleteFishNotFound() throws Exception {
        when(fishService.deleteFish(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/fish/1"))
                .andExpect(status().isNotFound());

        verify(fishService, times(1)).deleteFish(1L);
    }

    @Test
    void testGetAllSpecies() throws Exception {
        List<String> species = Arrays.asList("Gadus morhua", "Salmo salar");

        when(fishService.getAllSpecies()).thenReturn(species);

        mockMvc.perform(get("/api/fish/species"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value("Gadus morhua"))
                .andExpect(jsonPath("$[1]").value("Salmo salar"));

        verify(fishService, times(1)).getAllSpecies();
    }

    @Test
    void testGetFishBySpecies() throws Exception {
        Fish fish1 = new Fish();
        fish1.setName("Torsk");
        Fish fish2 = new Fish();
        fish2.setName("Laks");

        List<Fish> fishList = Arrays.asList(fish1, fish2);

        when(fishService.getFishBySpecies("Gadus morhua")).thenReturn(fishList);

        mockMvc.perform(get("/api/fish/species/Gadus morhua"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Torsk"))
                .andExpect(jsonPath("$[1].name").value("Laks"));

        verify(fishService, times(1)).getFishBySpecies("Gadus morhua");
    }
}