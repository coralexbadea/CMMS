package com.adecco.mentenance.controller;

import com.adecco.mentenance.domain.Machine;
import com.adecco.mentenance.domain.Raport;
import com.adecco.mentenance.domain.Task;
import com.adecco.mentenance.domain.TaskType;
import com.adecco.mentenance.service.MachineService;
import com.adecco.mentenance.service.RaportService;
import com.adecco.mentenance.service.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.Mac;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/raport")
public class RaportController {
    @Autowired
    MachineService machineService;
    @Autowired
    RaportService raportService;
    @Autowired
    TaskTypeService taskTypeService;

    @GetMapping(value = "/{year}/{month}")
    public ModelAndView showMachines(@PathVariable(name="year")int year, @PathVariable(name="month")String month) {
        ModelAndView modelAndView = new ModelAndView();
        List<Machine> machines = machineService.listAll();
        modelAndView.addObject("machines", machines);
        modelAndView.addObject("year", year);
        modelAndView.addObject("month", month);
        modelAndView.setViewName("raport/machines");
        return modelAndView;
    }

    @GetMapping(value = "/{year}/{month}/{mname}")
    public ModelAndView showRaport(@PathVariable(name="year")int year, @PathVariable(name="month")String month,@PathVariable(name="mname")String mname) {
        ModelAndView modelAndView = new ModelAndView();
        Raport raport = raportService.getRaport(year, month, mname);
        List<TaskType> taskTypes = taskTypeService.listAll();
        modelAndView.addObject("raport", raport);
        modelAndView.addObject("taskTypes", taskTypes);
        modelAndView.setViewName("raport/show");
        return modelAndView;
    }

    @PostMapping(value = "/save")
    public String edit(@ModelAttribute("raport") Raport raport) {
        raportService.saveEdit(raport);
        return ("redirect:/raport/"+raport.getYear()+"/"
                +raport.getMonth()+"/"+raport.getMachine().getMname());
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable (name="id") Long id) {
        raportService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping(value="/current")
    public String currentRaport(){
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        return "redirect:/raport/"+year+'/'+month;
    }

}
