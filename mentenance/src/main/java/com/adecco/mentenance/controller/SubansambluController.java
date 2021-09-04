package com.adecco.mentenance.controller;

import com.adecco.mentenance.domain.Subansamblu;
import com.adecco.mentenance.service.SubansambluService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/subansamblu")
public class SubansambluController {
    @Autowired
    SubansambluService subansambluService;

    @GetMapping(value = "/index")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView();
        List<Subansamblu> subansambluri = subansambluService.listAll();
        modelAndView.addObject("subansambluri", subansambluri);
        modelAndView.setViewName("subansamblu/index");
        return modelAndView;
    }

    @GetMapping(value = "/create")
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView("subansamblu/create");
        modelAndView.addObject("subansamblu", new Subansamblu());
        return modelAndView;
    }


    @PostMapping(value = "/create")
    public String create(Subansamblu subansamblu) {
        subansambluService.save(subansamblu);
        return "redirect:/subansamblu/index";
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndVie = new ModelAndView("subansamblu/edit");
        Subansamblu subansamblu = subansambluService.findById(id);
        modelAndVie.addObject("subansamblu", subansamblu);
        return modelAndVie;
    }

    @PostMapping(value = "/edit")
    public String edit(@ModelAttribute("subansamblu") Subansamblu subansamblu) {
        subansambluService.saveEdit(subansamblu);
        return "redirect:/subansamblu/index";
    }

    @RequestMapping("/delete/{id}")
    public String deletelaboratory(@PathVariable (name="id") Long id) {
        subansambluService.delete(id);
        return "redirect:/subansamblu/index";
    }

}
