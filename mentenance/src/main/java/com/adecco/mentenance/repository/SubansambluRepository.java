package com.adecco.mentenance.repository;

import com.adecco.mentenance.domain.Subansamblu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubansambluRepository extends JpaRepository<Subansamblu, Long> {
}
