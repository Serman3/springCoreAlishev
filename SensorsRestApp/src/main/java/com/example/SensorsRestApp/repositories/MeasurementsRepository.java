package com.example.SensorsRestApp.repositories;

import com.example.SensorsRestApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

    @Query(value = """
            select count(*) from measurement where raining > 0;
            """, nativeQuery = true)
    int countRainyDaysTrue();
}
