package com.adecco.mentenance.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="raports")
public class Raport {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long rid;

    private String month;
    private int year;

    @ManyToOne
    @JoinColumn(name="mid")
    private Machine machine;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "raport")
    private List<Task> tasks = new ArrayList<>();

    public Raport(int year, String month) {
        this.month = month;
        this.year = year;
    }

    public Raport() {
    }


    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString(){
        return "Raport_"+this.getMonth()+"_"+this.getYear();
    }


}
