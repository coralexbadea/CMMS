package com.adecco.mentenance.service;

import com.adecco.mentenance.domain.ComponentType;
import com.adecco.mentenance.repository.ComponentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("componentTypeService")
public class ComponentTypeService {
    @Autowired
    ComponentTypeRepository componentTypeRepository;


    public List<ComponentType> listAll() {
        return componentTypeRepository.findAll();
    }

    public void save(ComponentType componentType) {
        componentTypeRepository.save(componentType);
    }

    public ComponentType findById(Long id) {
        return componentTypeRepository.findById(id).get();
    }

    public ComponentType saveEdit(ComponentType componentType) {
        return componentTypeRepository.save(componentType);
    }

    public void delete(Long sid) {
        componentTypeRepository.delete(componentTypeRepository.findById(sid).get());
    }
}

