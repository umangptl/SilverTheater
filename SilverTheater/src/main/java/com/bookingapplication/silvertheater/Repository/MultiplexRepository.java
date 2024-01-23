package com.bookingapplication.silvertheater.Repository;

import com.bookingapplication.silvertheater.Model.Multiplex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultiplexRepository extends JpaRepository<Multiplex, Long> {
    List<Multiplex> findByLocation(String location);

    @Query("SELECT DISTINCT m.location FROM Multiplex m")
    List<String> findAllLocations();

}
