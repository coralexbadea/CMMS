package com.adecco.mentenance.service;

import com.adecco.mentenance.domain.Component;
import com.adecco.mentenance.domain.Machine;
import com.adecco.mentenance.domain.Raport;
import com.adecco.mentenance.domain.Task;
import com.adecco.mentenance.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("componentService")
public class ComponentService {
    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    MachineService machineService;
    @Autowired
    RaportService raportService;
    @Autowired
    TaskTypeService taskTypeService;
    public void save(Component c, Long id) {
        Machine machine = machineService.findById(id);
        c.setMachine(machine);
        List<Raport> raports = raportService.findByMachine(machine);
        for(Raport raport : raports){
            Task t = new Task(c, taskTypeService.findByName(" "), raport);
            raportService.addTask(raport.getRid(), t);
        }
        componentRepository.save(c);
    }

    public void save(Component c) {
        componentRepository.save(c);
    }

    public Component findById(Long id) {
        return componentRepository.findById(id).get();
    }

    public Component saveEdit(Component component) {
        Component a = componentRepository.findById(component.getCid()).get();
        a.setName(component.getName());
        return componentRepository.save(a);
    }

    @Transactional
    public void delete(Long id) {

        componentRepository.deleteById(id);
    }
    public List<Component> listAllByMachineId(Long id) {
        return componentRepository.findAllByMachine_Mid(id);
    }
}

