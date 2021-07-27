package com.adecco.mentenance.controller;

import com.adecco.mentenance.domain.ComponentType;
import com.adecco.mentenance.service.ComponentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
@RequestMapping("/componentType")
public class ComponentTypeController {
    @Autowired
    ComponentTypeService componentTypeService;

    @GetMapping(value = "/index")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView();
        List<ComponentType> componentTypes = componentTypeService.listAll();
        modelAndView.addObject("componentTypes", componentTypes);
        modelAndView.setViewName("componentType/index");
        return modelAndView;
    }

    @GetMapping(value = "/create")
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView("componentType/create");
        modelAndView.addObject("componentType", new ComponentType());
        return modelAndView;
    }


    @PostMapping(value = "/create")
    public String create(ComponentType componentType) {
        componentTypeService.save(componentType);
        return "redirect:/componentType/index";
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndVie = new ModelAndView("componentType/edit");
        ComponentType componentType = componentTypeService.findById(id);
        modelAndVie.addObject("componentType", componentType);
        return modelAndVie;
    }

    @PostMapping(value = "/edit")
    public String edit(@ModelAttribute("componentType") ComponentType componentType) {
        componentTypeService.saveEdit(componentType);
        return "redirect:/componentType/index";
    }

    @RequestMapping("/delete/{id}")
    public String deletelaboratory(@PathVariable (name="id") Long id) {
        componentTypeService.delete(id);
        return "redirect:/componentType/index";
    }

}


