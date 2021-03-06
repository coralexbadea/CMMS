package com.adecco.mentenance.service;

import com.adecco.mentenance.domain.*;
import com.adecco.mentenance.repository.TaskRepository;
import com.adecco.mentenance.storage.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskTypeService taskTypeService;
    @Autowired
    FileSystemStorageService storageService;
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
        storageService.deleteFolder(id);
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

    public List<ComponentType> getComponentTypes(List<Task> tasks) {
        List<ComponentType> componentTypes = new ArrayList<>();
        for(Task t: tasks){
            ComponentType ctype = t.getComponent().getComponentType();
            if(!componentTypes.contains(ctype)){
                componentTypes.add(ctype);
            }
        }
        return componentTypes;
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
