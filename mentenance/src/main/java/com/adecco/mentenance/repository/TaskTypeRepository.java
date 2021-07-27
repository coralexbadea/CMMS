package com.adecco.mentenance.repository;

import com.adecco.mentenance.domain.Component;
import com.adecco.mentenance.domain.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {
    TaskType findByTtname(String name);
}
