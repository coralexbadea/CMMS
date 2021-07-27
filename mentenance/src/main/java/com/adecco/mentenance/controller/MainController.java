package com.adecco.mentenance.controller;


import com.adecco.mentenance.domain.User;
import com.adecco.mentenance.viewmodel.RegistrationComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    private RegistrationComponent registrationModel;

    @GetMapping(value = {"/login"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = {"/admin"})
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping(value = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("registration", registrationModel.create(user, bindingResult));
        return modelAndView;
    }

    @GetMapping(value="/")
    public String pageDefault(){
        return "redirect:/raport/current";
    }



    @GetMapping(value="/view/{tid}")
    public ModelAndView view(@PathVariable(name="tid")Long tid){
        ModelAndView modelAndView = new ModelAndView("raport/createimage");
//        System.out.println("here0");
//
//        List<String> strings = storageService.loadAll(tid).map(path->path.toString()).filter(s->s.contains(".jpg")||s.contains(".jpeg")||s.contains(".png")).collect(Collectors.toList());
//        System.out.println("here2");
//
//        modelAndView.addObject("files", strings);
//        System.out.println("here3");
        return modelAndView;
    }
}

