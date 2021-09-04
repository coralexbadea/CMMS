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
    private boolean active;
    private String code;

    @ManyToOne
    @JoinColumn(name="mid")
    private Machine machine;

    @ManyToOne
    @JoinColumn(name="ctid")
    private ComponentType componentType;

    @ManyToOne
    @JoinColumn(name="stid")
    private Subansamblu subansamblu;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "component")
    private Set<Task> tasks = new HashSet<>();

    public Component(){
        this.active = true;
    }
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

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Subansamblu getSubansamblu() {
        return subansamblu;
    }

    public void setSubansamblu(Subansamblu subansamblu) {
        this.subansamblu = subansamblu;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void generateCode(){
        this.code = this.machine.getCode() + this.componentType.getCtname().charAt(0) + String.format("%5s", this.cid).replace(" ", "0");
    }
}
