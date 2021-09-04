package com.adecco.mentenance.service;


import com.adecco.mentenance.domain.Component;
import com.adecco.mentenance.domain.Machine;
import com.adecco.mentenance.domain.Subansamblu;
import com.adecco.mentenance.repository.SubansambluRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("subansambluService")
public class SubansambluService {
    @Autowired
    SubansambluRepository subansambluRepository;
    @Autowired
    MachineService machineService;

    public List<Subansamblu> listAll() {
        return subansambluRepository.findAll();
    }

    public void save(Subansamblu subansamblu) {
        subansambluRepository.save(subansamblu);
    }

    public Subansamblu findById(Long id) {
        return subansambluRepository.findById(id).get();
    }

    public Subansamblu saveEdit(Subansamblu subansamblu) {
        return subansambluRepository.save(subansamblu);
    }

    public void delete(Long sid) {
        subansambluRepository.delete(subansambluRepository.findById(sid).get());
    }


    public List<Subansamblu> listAllByMachine(String mname) {
        Machine m = machineService.findByMname(mname).get(0);
        return m.getComponents().stream().map(c -> c.getSubansamblu()).distinct().collect(Collectors.toList());
    }
}
