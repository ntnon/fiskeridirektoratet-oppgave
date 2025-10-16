package net.nydal.fiskeregister.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Navn må fylles ut")
    private String name;

    @NotBlank(message = "Art må fylles ut")
    private String species;

    @Positive(message = "Lengde må være positiv")
    private double lengthCm;

    @Positive(message = "Vekt må være positiv")
    private double weightKg;

    // Konstruktører
    public Fish() {}

    public Fish(String name, String species, double lengthCm, double weightKg) {
        this.name = name;
        this.species = species;
        this.lengthCm = lengthCm;
        this.weightKg = weightKg;
    }

    // Gettere og settere
    public Long getId() { return id; }
    public Long setId(Long id) { return this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    public double getLengthCm() { return lengthCm; }
    public void setLengthCm(double lengthCm) { this.lengthCm = lengthCm; }
    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
}