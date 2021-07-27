package com.adecco.mentenance.domain;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="taskTypes")
public class TaskType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ttid;
    private String ttname;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "taskType")
    private Set<Task> tasks = new HashSet<>();

    public TaskType(){}
    public TaskType(String name){
        this.ttname = name;
    }
    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Long getTtid() {
        return ttid;
    }

    public void setTtid(Long ttid) {
        this.ttid = ttid;
    }

    public String getTtname() {
        return ttname;
    }

    public void setTtname(String ttname) {
        this.ttname = ttname;
    }


}
