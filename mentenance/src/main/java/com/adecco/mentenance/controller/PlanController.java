package com.adecco.mentenance.controller;

import com.adecco.mentenance.domain.*;
import com.adecco.mentenance.export.ExcelExport;
import com.adecco.mentenance.export.ExcelExportFactory;
import com.adecco.mentenance.service.MachineService;
import com.adecco.mentenance.service.RaportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    RaportService raportService;
    @Autowired
    MachineService machineService;
    @GetMapping(value = "/{year}/{mname}")
    public ModelAndView show(@PathVariable(name="year")int year, @PathVariable(name="mname")String mname) {
        ModelAndView modelAndView = new ModelAndView();
        String[][] strings = raportService.getPlan(year, mname);
        modelAndView.addObject("strings", strings);
        modelAndView.addObject("mname", mname);
        modelAndView.setViewName("plan/show");
        return modelAndView;
    }

    @GetMapping(value = "/{year}")
    public ModelAndView showMachines(@PathVariable(name="year")int year) {
        ModelAndView modelAndView = new ModelAndView();
        List<Machine> machines = machineService.listAllActive();
        modelAndView.addObject("machines", machines);
        modelAndView.addObject("year", year);
        modelAndView.setViewName("plan/machines");
        return modelAndView;
    }

    @GetMapping(value = "/current")
    public String currentPlan(){
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        //int month = currentDate.getMonthValue();
        return "redirect:/plan/"+year;
    }

    @GetMapping(value = "/index")
    public ModelAndView allPlans(){
        ModelAndView modelAndView = new ModelAndView();
        List<Integer> years = raportService.getYears();
        modelAndView.addObject("years", years);
        modelAndView.setViewName("plan/index");
        return modelAndView;
    }

    @GetMapping("/{year}/delete")
    public ModelAndView deleteCheck(@PathVariable(name = "year") int year) {
        ModelAndView modelAndVie = new ModelAndView("plan/check");
        modelAndVie.addObject("year", year);
        return modelAndVie;
    }

    @PostMapping("/{year}/delete")
    public String delete(@PathVariable(name = "year") int year) {
        raportService.deletePlan(year);
        return "redirect:/plan/index";
    }

    @GetMapping(value = "/excel/{year}")
    public void excel(@PathVariable(name="year")int year, HttpServletResponse response) throws IOException {
        List<Task> tasks = raportService.getTasksForExcel(year);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Costs_"+year + ".xlsx";
        response.setHeader(headerKey, headerValue);
        ExcelExport<List<Raport>> planExcelExporter = ExcelExportFactory.getExportType("costs", tasks);
        planExcelExporter.export(response);
    }


//    @GetMapping("/create")
//    public ModelAndView create(){
//        ModelAndView modelAndVie = new ModelAndView("plan/create");
//        LocalDate currentDate = LocalDate.now();
//        int year = currentDate.getYear();
//        int[] years = new int[10];
//        for(int i = 0; i < 10 ; i++){
//            years[i] = year + i;
//        }
//        modelAndVie.addObject("years", years);
//        return modelAndVie;
//    }

}
