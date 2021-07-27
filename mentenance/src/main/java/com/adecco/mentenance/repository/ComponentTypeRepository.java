package com.adecco.mentenance.repository;

import com.adecco.mentenance.domain.ComponentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentTypeRepository extends JpaRepository<ComponentType, Long> {
}
