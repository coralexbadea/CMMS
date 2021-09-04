package com.adecco.mentenance.controller;

import com.adecco.mentenance.domain.*;
import com.adecco.mentenance.export.ExcelExport;
import com.adecco.mentenance.export.ExcelExportFactory;
import com.adecco.mentenance.service.*;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


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
    @Autowired
    SubansambluService subansambluService;
    @GetMapping(value = "/{year}/{month}")
    public ModelAndView showMachines(@PathVariable(name="year")int year, @PathVariable(name="month")String month) {
        ModelAndView modelAndView = new ModelAndView();
        List<Machine> machines = machineService.listAllActive();
        modelAndView.addObject("machines", machines);
        modelAndView.addObject("year", year);
        modelAndView.addObject("month", month);
        modelAndView.setViewName("raport/machines");
        return modelAndView;
    }
    @GetMapping(value = "/{year}/{month}/{mname}")
    public ModelAndView showSubsansambluri(@PathVariable(name="year")int year, @PathVariable(name="month")String month, @PathVariable(name="mname")String mname) {
        ModelAndView modelAndView = new ModelAndView();
        List<Subansamblu> subansambluri = subansambluService.listAllByMachine(mname);
        modelAndView.addObject("subansambluri", subansambluri);
        modelAndView.addObject("mname", mname);
        modelAndView.addObject("year", year);
        modelAndView.addObject("month", month);
        modelAndView.setViewName("raport/subansambluri");
        return modelAndView;
    }

    @GetMapping(value = "/{year}/{month}/{mname}/{sid}")
    public ModelAndView showRaport(@PathVariable(name="year")int year, @PathVariable(name="month")String month,@PathVariable(name="mname")String mname, @PathVariable(name="sid")Long sid) {
        ModelAndView modelAndView = new ModelAndView();
        Raport raport = raportService.getRaport(year, month, mname);
        List<TaskType> taskTypes = taskTypeService.listAll();
        modelAndView.addObject("id", sid);
        modelAndView.addObject("raport", raport);
        modelAndView.addObject("taskTypes", taskTypes);
        modelAndView.setViewName("raport/show");
        return modelAndView;
    }

    @PostMapping(value = "/save/{sid}")
    public String edit(@ModelAttribute("raport") Raport raport, @PathVariable Long sid) {
        List<Task> tasks = raport.getTasks();
        raportService.updateTasksDate(tasks);
        raport.setTasks(tasks);
        raportService.saveEdit(raport);
        return ("redirect:/raport/"+raport.getYear()+"/"
                +raport.getMonth()+"/"+raport.getMachine().getMname()+"/"+sid);
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

    @GetMapping(value="/chart/{id}")
    public ModelAndView chart(@PathVariable(name="id")Long id) {
        ModelAndView modelAndView = new ModelAndView();
        List<String> labels = taskTypeService.listAll().stream().map(s->s.getTtname()).collect(Collectors.toList());
        List<Integer> list1 = raportService.getTodoTasks(id);
        List<Integer> list2 = raportService.getDoneTasks(id);
        labels.remove(0);
        list1.remove(0);
        list2.remove(0);
        modelAndView.addObject("labelsX", labels);
        modelAndView.addObject("list1", list1);
        modelAndView.addObject("list2", list2);
        modelAndView.setViewName("widgets/chart");

        return modelAndView;
    }

}
