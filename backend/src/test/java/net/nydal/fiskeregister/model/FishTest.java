package net.nydal.fiskeregister.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FishTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testGettersAndSetters() {
        Fish fish = new Fish();
        fish.setId(2L);
        fish.setName("Cod");
        fish.setSpecies("Gadus morhua");
        fish.setLengthCm(50.5);
        fish.setWeightKg(2.3);

        assertEquals("Cod", fish.getName());
        assertEquals("Gadus morhua", fish.getSpecies());
        assertEquals(50.5, fish.getLengthCm());
        assertEquals(2.3, fish.getWeightKg());
            assertEquals(2L, fish.getId());
    }

    @Test  
    public void testDoubleSet() {
        Fish fish = new Fish();
          fish.setId(1L);
            fish.setId(2L);
        fish.setName("codfsih");
        fish.setName("Cod");
        fish.setSpecies("Gadus morhua");
        fish.setLengthCm(2.2);
        fish.setLengthCm(50.5);
         fish.setWeightKg(0.3);
        fish.setWeightKg(2.3);

        assertEquals("Cod", fish.getName());
        assertEquals("Gadus morhua", fish.getSpecies());
        assertEquals(50.5, fish.getLengthCm());
        assertEquals(2.3, fish.getWeightKg());
        assertEquals(2L, fish.getId());
    }

    @Test
    public void testValidationSuccess() {
        Fish fish = new Fish();
          fish.setId(1L);
        fish.setName("Salmon");
        fish.setSpecies("Salmo salar");
        fish.setLengthCm(75);
        fish.setWeightKg(4.5);

        Set<ConstraintViolation<Fish>> violations = validator.validate(fish);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testValidationFailsOnBlankName() {
        Fish fish = new Fish();
        fish.setId(2L);
        fish.setName(""); // invalid
        fish.setSpecies("Salmo salar");
        fish.setLengthCm(75);
        fish.setWeightKg(4.5);

        Set<ConstraintViolation<Fish>> violations = validator.validate(fish);
        assertFalse(violations.isEmpty(), "Name cannot be blank");
    }

    @Test
    public void testValidationFailsOnNegativeLength() {
        Fish fish = new Fish();
        fish.setId(3L);
        fish.setName("Trout");
        fish.setSpecies("Oncorhynchus mykiss");
        fish.setLengthCm(-10); // invalid
        fish.setWeightKg(1.5);

        Set<ConstraintViolation<Fish>> violations = validator.validate(fish);
        assertFalse(violations.isEmpty(), "Length cannot be negative");
    }
}