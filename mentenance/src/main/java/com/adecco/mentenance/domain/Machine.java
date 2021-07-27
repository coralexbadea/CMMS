package com.adecco.mentenance.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="machines")
public class Machine {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long mid;
    private String mname;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "machine")
    private Set<Component> components = new HashSet<>();

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "machine")
    private Set<Component> raports = new HashSet<>();

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    public Set<Component> getRaports() {
        return raports;
    }

    public void setRaports(Set<Component> raports) {
        this.raports = raports;
    }
}
