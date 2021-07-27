package com.adecco.mentenance.service;

import com.adecco.mentenance.domain.TaskType;
import com.adecco.mentenance.repository.TaskTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TaskTypeService")
public class TaskTypeService {
    @Autowired
    TaskTypeRepository taskTypeRepository;


    public List<TaskType> listAll() {
        return taskTypeRepository.findAll();
    }

    public void save(TaskType taskType) {
        taskTypeRepository.save(taskType);
    }

    public TaskType findById(Long id) {
        return taskTypeRepository.findById(id).get();
    }

    public TaskType saveEdit(TaskType taskType) {
        return taskTypeRepository.save(taskType);
    }

    public void delete(Long sid) {
        taskTypeRepository.delete(taskTypeRepository.findById(sid).get());
    }

    public TaskType findByName(String name) {
        return taskTypeRepository.findByTtname(name);
    }
}

