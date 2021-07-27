package com.adecco.mentenance.controller;

import com.adecco.mentenance.domain.*;
import com.adecco.mentenance.export.ExcelExport;
import com.adecco.mentenance.export.ExcelExportFactory;
import com.adecco.mentenance.export.RaportExcelExporter;
import com.adecco.mentenance.service.*;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;

@Controller
@RequestMapping("/raport")
public class RaportController {
    @Autowired
    MachineService machineService;
    @Autowired
    RaportService raportService;
    @Autowired
    TaskTypeService taskTypeService;
    @Autowired
    TaskService taskService;
    @Autowired
    PdfService pdfService;
    @Autowired
    ExcelExportFactory excelExportFactory;
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
        List<Task> tasks = raport.getTasks();
        List<TaskType> taskTypes = taskTypeService.listAll();

        modelAndView.addObject("raport", raport);
        modelAndView.addObject("taskTypes", taskTypes);
        modelAndView.setViewName("raport/show");
        return modelAndView;
    }

    @PostMapping(value = "/save")
    public String edit(@ModelAttribute("raport") Raport raport) {
        List<Task> tasks = raport.getTasks();
        raportService.updateTasksDate(tasks);
        raport.setTasks(tasks);
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

    @GetMapping(value = "/pdf/{rid}")
    public ResponseEntity<InputStreamResource> pdf(@PathVariable(name="rid")Long rid, HttpServletResponse response) throws DocumentException, IOException, URISyntaxException {
        Raport raport = raportService.findById(rid);
        String filename = raport.toString();
        pdfService.createPdf(filename, raport);
        filename = filename+".pdf";
        //this part downloads the pdf
        return pdfService.downloadPdf(filename);
    }

    @GetMapping(value = "/excel/{rid}")
    public void excel(@PathVariable(name="rid")Long rid, HttpServletResponse response) throws IOException {
        Raport raport = raportService.findById(rid);

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+raport.toString() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        ExcelExport<Raport> raportExcelExporter = ExcelExportFactory.getExportType("raport", raport);
        raportExcelExporter.export(response);

    }



}
