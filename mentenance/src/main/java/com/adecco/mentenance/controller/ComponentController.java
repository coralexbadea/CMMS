package com.adecco.mentenance.controller;

import com.adecco.mentenance.domain.Component;
import com.adecco.mentenance.domain.ComponentType;
import com.adecco.mentenance.service.ComponentService;
import com.adecco.mentenance.service.ComponentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/component")
public class ComponentController {
    @Autowired
    ComponentService componentService;
    @Autowired
    ComponentTypeService componentTypeService;
    @GetMapping(value = "/index/{id}")
    public ModelAndView getcomponents(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        List<Component> components = componentService.listAllByMachineId(id);
        modelAndView.addObject("components", components);
        modelAndView.addObject("mid", id);
        modelAndView.setViewName("component/index");
        return modelAndView;
    }

    @GetMapping(value = "/create/{id}")
    public ModelAndView create(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("component/create");
        modelAndView.addObject("component", new Component());
        modelAndView.addObject("componentTypes", componentTypeService.listAll());
        modelAndView.addObject( "mid", id);
        return modelAndView;
    }

    @PostMapping(value = "/create/{id}")
    public String create(@PathVariable(name = "id") Long id, Component component) {
        componentService.save(component, id);
        return "redirect:/component/index/"+ id;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndVie = new ModelAndView("component/edit");
        Component component = componentService.findById(id);
        List<ComponentType> componentTypes = componentTypeService.listAll();
        modelAndVie.addObject("componentTypes", componentTypes);
        modelAndVie.addObject("component", component);
        modelAndVie.addObject("mid", component.getMachine().getMid());
        return modelAndVie;
    }

    @PostMapping(value = "/edit/{mid}")
    public String edit(@PathVariable(name = "mid") Long mid, @ModelAttribute("component") Component component) {
        componentService.saveEdit(component);
        return "redirect:/component/index/"+mid;
    }


    @RequestMapping("/delete/{mid}/{cid}")
    public String delete(@PathVariable (name="cid") Long cid,@PathVariable (name="mid") Long mid) {
        componentService.delete(cid);
        return "redirect:/component/index/"+mid;
    }
}

