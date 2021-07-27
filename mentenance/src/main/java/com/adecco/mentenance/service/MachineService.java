package com.adecco.mentenance.service;

import com.adecco.mentenance.domain.Machine;
import com.adecco.mentenance.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        machineRepository.delete(machineRepository.findById(sid).get());
    }
}

