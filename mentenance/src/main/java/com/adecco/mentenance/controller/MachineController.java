package com.adecco.mentenance.controller;


import com.adecco.mentenance.domain.Component;
import com.adecco.mentenance.domain.Machine;
import com.adecco.mentenance.domain.Raport;
import com.adecco.mentenance.domain.Task;
import com.adecco.mentenance.export.ExcelExport;
import com.adecco.mentenance.export.ExcelExportFactory;
import com.adecco.mentenance.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/machine")
public class MachineController {
    @Autowired
    MachineService machineService;

    @GetMapping(value = "/index")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView();
        List<Machine> machines = machineService.listAll();
        modelAndView.addObject("machines", machines);
        modelAndView.setViewName("machine/index");
        return modelAndView;
    }

    @GetMapping(value = "/create")
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView("machine/create");
        modelAndView.addObject("machine", new Machine());
        return modelAndView;
    }


    @PostMapping(value = "/create")
    public String create(Machine machine) {
        machineService.save(machine);
        return "redirect:/machine/index";
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndVie = new ModelAndView("machine/edit");
        Machine machine = machineService.findById(id);
        modelAndVie.addObject("machine", machine);
        return modelAndVie;
    }

    @PostMapping(value = "/edit")
    public String edit(@ModelAttribute("machine") Machine machine) {
        machineService.saveEdit(machine);
        return "redirect:/machine/index";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable (name="id") Long id) {
        machineService.delete(id);
        return "redirect:/machine/index";
    }

    @GetMapping(value = "/excel/{id}")
    public void excel(@PathVariable(name="id")Long id, HttpServletResponse response) throws IOException {
        Machine m = machineService.findById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Componets_"+m.getMname() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        ExcelExport<List<Component>> machineExcelExporter = ExcelExportFactory.getExportType("machine", m);
        machineExcelExporter.export(response);
    }



}

