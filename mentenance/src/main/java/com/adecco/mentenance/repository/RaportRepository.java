package com.adecco.mentenance.repository;

import com.adecco.mentenance.domain.Machine;
import com.adecco.mentenance.domain.Raport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaportRepository extends JpaRepository<Raport, Long> {
    Raport findByYearAndMonthAndMachine_Mname(int year, String month, String mname);

    List<Raport> findAllByYearAndMachine_Mname(int year, String mname);

    List<Raport> findByMachine(Machine machine);

    @Query("SELECT DISTINCT r.year FROM Raport r")
    int[] findDistinctYear();

    List<Raport> findAllByYear(int year);
}
