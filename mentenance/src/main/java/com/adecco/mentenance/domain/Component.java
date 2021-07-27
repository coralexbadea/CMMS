package com.adecco.mentenance.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="components")
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private String name;

    @ManyToOne
    @JoinColumn(name="mid")
    private Machine machine;

    @ManyToOne
    @JoinColumn(name="ctid")
    private ComponentType componentType;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "machine")
    private Set<Component> tasks = new HashSet<>();

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public Set<Component> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Component> tasks) {
        this.tasks = tasks;
    }
}
