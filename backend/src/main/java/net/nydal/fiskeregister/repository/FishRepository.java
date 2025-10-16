package net.nydal.fiskeregister.repository;

import net.nydal.fiskeregister.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {

    // Find all fish with a specific species name
    List<Fish> findBySpecies(String species);
    
    // Find all unique species names
    @Query("SELECT DISTINCT f.species FROM Fish f")
    List<String> findAllSpecies();
}