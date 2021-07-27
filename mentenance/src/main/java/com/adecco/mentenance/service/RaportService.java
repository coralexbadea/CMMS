package com.adecco.mentenance.service;

import com.adecco.mentenance.domain.*;
import com.adecco.mentenance.repository.RaportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RaportService {
    @Autowired
    RaportRepository raportRepository;
    @Autowired
    MachineService machineService;
    @Autowired
    TaskService taskService;
    public void createRaports(int year, String month){
        List<Machine> machines = machineService.listAll();
        for (Machine machine: machines){
            Raport r = new Raport(year, month);
            List<Task> tasks =  taskService.createTasks(machine, r);
            r.setMachine(machine);
            r.setTasks(tasks);
            this.save(r);
        }
    }
    public List<Raport> listAll() {
        return raportRepository.findAll();
    }

    public void save(Raport raport) {
        raportRepository.save(raport);
    }

    public Raport findById(Long id) {
        return raportRepository.findById(id).get();
    }
    public Raport saveEdit(Raport raport) {
        return raportRepository.save(raport);
    }

    public void delete(Long sid) {
        Raport raport = raportRepository.findById(sid).get();
        for(Task t: raport.getTasks()){
            taskService.delete(t.getTid());
        }
        raportRepository.delete(raportRepository.findById(sid).get());
    }

    public Raport getRaport(int year, String month, String mname) {
        Raport raport = raportRepository.findByYearAndMonthAndMachine_Mname(year, month, mname);
        if(raport == null){
            //this.createRaports(year, month);
            for(int i = 1; i <= 12; i++){
                this.createRaports(year, String.valueOf(i));
            }
            return  raportRepository.findByYearAndMonthAndMachine_Mname(year, month, mname);
        }
        List<Task> tasks = raport.getTasks();
        List<Task> newtasks = new ArrayList<>();
        List<ComponentType> componentTypes = taskService.getComponentTypes(tasks);
        for(ComponentType componentType: componentTypes){
            for(Task t: tasks){
                if(t.getComponent().getComponentType().equals(componentType)){
                    newtasks.add(t);
                }
            }
        }
        raport.setTasks(newtasks);
        return raport;
    }

    public String[][] getPlan(int year, String mname) {
        List<Raport> raports = raportRepository.findAllByYearAndMachine_Mname(year, mname);
        if(raports.size() == 0){
            System.out.println("HRER!@@@");
            for(int i = 1; i <= 12; i++){
                this.createRaports(year, String.valueOf(i));
            }
        }
        raports = raportRepository.findAllByYearAndMachine_Mname(year, mname);
        int noTasks = raports.get(0).getTasks().size();
        int noRows = 13;
        String[][] strings = new String[noTasks][noRows];
        for(int i = 0; i < noTasks; i++){
            for(int j = 0; j < noRows; j++){
                if(j==0){
                    strings[i][j] = raports.get(0).getTasks().get(i).getComponent().getName();
                }
                else{
                    strings[i][j] = raports.get(j-1).getTasks().get(i).getTaskType().getTtname();
                }
            }
        }
        return strings;
    }

    public void addTask(Long rid, Task task){
        Raport raport = this.findById(rid);
        List<Task> tasks = raport.getTasks();
        tasks.add(task);
        raport.setTasks(tasks);
        this.save(raport);
    }

    public List<Raport> findByMachine(Machine machine) {
        return raportRepository.findByMachine(machine);
    }

    public List<Integer> getYears() {
        List<Integer> years = Arrays.stream(raportRepository.findDistinctYear())
                                    .boxed().collect(Collectors.toList());
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        for(int i = 0; i < 10 ; i++){
            if(!years.contains(year + i)) {
                years.add(year+i);
            }
        }

        return years;
    }

    public void deletePlan(int year) {
        List<Raport> raports = raportRepository.findAllByYear(year);
        for(Raport r: raports){
            this.delete(r.getRid());
        }
    }

    public void updateTasksDate(List<Task> tasks) {
        for(Task t: tasks){
            Task last = taskService.findById(t.getTid());
            if(!t.getAction1().equals(last.getAction1()) ||
                    !t.getAction2().equals(last.getAction2()) ||
                            !t.getAction3().equals(last.getAction3())){
                        t.setDate(LocalDate.now());
            }
        }
    }
}
