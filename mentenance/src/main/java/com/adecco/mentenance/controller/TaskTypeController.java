package com.adecco.mentenance.controller;

import com.adecco.mentenance.domain.TaskType;
import com.adecco.mentenance.service.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/taskType")
public class TaskTypeController {
    @Autowired
    TaskTypeService taskTypeService;

    @GetMapping(value = "/index")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView();
        List<TaskType> taskTypes = taskTypeService.listAll();
        modelAndView.addObject("taskTypes", taskTypes);
        modelAndView.setViewName("taskType/index");
        return modelAndView;
    }

    @GetMapping(value = "/create")
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView("taskType/create");
        modelAndView.addObject("taskType", new TaskType());
        return modelAndView;
    }


    @PostMapping(value = "/create")
    public String create(TaskType taskType) {
        taskTypeService.save(taskType);
        return "redirect:/taskType/index";
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndVie = new ModelAndView("taskType/edit");
        TaskType taskType = taskTypeService.findById(id);
        modelAndVie.addObject("taskType", taskType);
        return modelAndVie;
    }

    @PostMapping(value = "/edit")
    public String edit(@ModelAttribute("taskType") TaskType taskType) {
        taskTypeService.saveEdit(taskType);
        return "redirect:/taskType/index";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable (name="id") Long id) {
        taskTypeService.delete(id);
        return "redirect:/taskType/index";
    }



}


