package com.adecco.mentenance.service;

import com.adecco.mentenance.domain.Component;
import com.adecco.mentenance.domain.Machine;
import com.adecco.mentenance.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("machineService")
public class MachineService {
    @Autowired
    MachineRepository machineRepository;

    public List<Machine> findByMname(String mname){ return machineRepository.findByMname(mname);}

    public List<Machine> listAll() {
        return machineRepository.findAll();
    }

    public void save(Machine machine) {
        machineRepository.save(machine);
    }

    public Machine findById(Long id) {
        return machineRepository.findById(id).get();
    }

    public Machine saveEdit(Machine machine) {
        return machineRepository.save(machine);
    }

    public void delete(Long sid) {
        Machine m = machineRepository.getById(sid);
        m.setActive(!m.isActive());
        machineRepository.save(m);
//        machineRepository.delete(machineRepository.findById(sid).get());
    }

    public List<Machine> listAllActive() {
        return machineRepository.findAll().stream().filter(m -> m.isActive()).collect(Collectors.toList());
    }

    public List<Component> getComponentsForExcel(Long id) {
        Machine m = machineRepository.getById(id);
        return m.getComponents().stream().filter(c -> c.isActive()).collect(Collectors.toList());
    }
}

