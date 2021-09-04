package com.adecco.mentenance.domain;

import javax.persistence.*;
import javax.swing.*;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    @ManyToOne
    @JoinColumn(name="ttid")
    private TaskType taskType;

    @ManyToOne
    @JoinColumn(name="rid")
    private Raport raport;

    @ManyToOne
    @JoinColumn(name="cid")
    private Component component;
    private String quantity;
    private String price;
    private String action1;
    private String action2;
    private String action3;
    private String obsWorker;
    private String pintern;
    private String pextern;
    private String realSituation;
    private LocalDate date;

    public Task(Component c, TaskType t, Raport r) {
        this.component = c;
        this.taskType = t;
        this.action1 = t.getTtname();
        this.action2 = t.getTtname();
        this.action3 = t.getTtname();
        this.raport = r;
        this.date = LocalDate.now();
    }

    public Task(){
    }


    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Raport getRaport() {
        return raport;
    }

    public void setRaport(Raport raport) {
        this.raport = raport;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getObsWorker() {
        return obsWorker;
    }

    public void setObsWorker(String obsWorker) {
        this.obsWorker = obsWorker;
    }

    public String getPintern() {
        return pintern;
    }

    public void setPintern(String pintern) {
        this.pintern = pintern;
    }

    public String getPextern() {
        return pextern;
    }

    public void setPextern(String pextern) {
        this.pextern = pextern;
    }

    public String getRealSituation() {
        return realSituation;
    }

    public void setRealSituation(String realSituation) {
        this.realSituation = realSituation;
    }

    public String getAction1() {
        return action1;
    }

    public void setAction1(String action1) {
        this.action1 = action1;
    }

    public String getAction2() {
        return action2;
    }

    public void setAction2(String action2) {
        this.action2 = action2;
    }

    public String getAction3() {
        return action3;
    }

    public void setAction3(String action3) {
        this.action3 = action3;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getQuantity() {
        if (quantity != null )
            return quantity;
        return "";
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        if (price != null)
            return price;
        return "";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice(){
        try {
            return Integer.toString(Integer.parseInt(this.quantity) * Integer.parseInt(this.price));
        }
        catch (NumberFormatException nfe){
            return "";
        }
    }

    public boolean checkIfDone(String action) {
        return (action1.equals(action) ||
                action2.equals(action) ||
                action3.equals(action));
    }
}
