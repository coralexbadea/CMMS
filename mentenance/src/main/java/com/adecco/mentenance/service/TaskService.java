package com.adecco.mentenance.service;

import com.adecco.mentenance.domain.*;
import com.adecco.mentenance.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskTypeService taskTypeService;

    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).get();
    }

    public Task saveEdit(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }


    public List<Task> createTasks(Machine machine, Raport r){
        List<Component> components= new ArrayList<>(machine.getComponents());
        List<Task> tasks = new ArrayList<>();
        TaskType taskType = taskTypeService.findByName(" ");
        for (Component c: components){
            tasks.add(new Task(c, taskType, r));
        }

        return tasks;
    }



    //    public List<Task> createTasks(Machine machine){
//        List<Component> components= new ArrayList<>(machine.getComponents());
//        List<Task> tasks = new ArrayList<>();
//        for (Component c: components){
//            Task t = new Task();
//            Set<Task> ctasks = c.getTasks();
//            ctasks.add(t);
//            c.setTasks(ctasks);
//            componentService.save(c);
//            tasks.add(t);
//        }
//        System.out.println("HERE BOS!!!");
//        System.out.println(tasks.size());
//        return tasks;
//    }
}
