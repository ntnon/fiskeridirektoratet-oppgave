package net.nydal.fiskeregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import net.nydal.fiskeregister.model.Fish;
import net.nydal.fiskeregister.repository.FishRepository;

@SpringBootApplication
public class FiskeregisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiskeregisterApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(FishRepository fishRepository) {
        return args -> {
            fishRepository.save(new Fish("Torsk", "Gadus morhua", 60, 3.2));
            fishRepository.save(new Fish("Laks", "Salmo salar", 70, 4.5));
            fishRepository.save(new Fish("Sild", "Clupea harengus", 25, 0.2));
            fishRepository.save(new Fish("Makrell", "Scomber scombrus", 40, 0.5));
            fishRepository.save(new Fish("Sei", "Pollachius virens", 55, 2.0));     
            fishRepository.save(new Fish("Hyse", "Melanogrammus aeglefinus", 50, 1.5)); 
            fishRepository.save(new Fish("Rødspette", "Pleuronectes platessa", 30, 0.3));
            fishRepository.save(new Fish("Kveite", "Hippoglossus hippoglossus", 80, 5.0));
            fishRepository.save(new Fish("Blåkveite", "Reinhardtius hippoglossoides", 75, 4.0));
            fishRepository.save(new Fish("Lyr", "Pollachius pollachius", 45, 1.8));         
        };
    }
}