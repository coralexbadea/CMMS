package com.adecco.mentenance.repository;

import com.adecco.mentenance.domain.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findAllByMachine_Mid(Long id);
}
