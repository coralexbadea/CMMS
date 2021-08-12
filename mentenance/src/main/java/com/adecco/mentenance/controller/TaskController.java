package com.adecco.mentenance.controller;

import com.adecco.mentenance.repository.TaskRepository;
import com.adecco.mentenance.storage.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    FileSystemStorageService storageService;
    @Autowired
    TaskRepository taskRepository;
    @Value("${server.address}")
    private String serverAddress;

    @PostMapping(value = "/image/{tid}")
    public String create(@PathVariable(name="tid") Long tid,@RequestParam("file") MultipartFile file,
                         RedirectAttributes redirectAttributes) throws IOException {
        try{
            storageService.store(file, tid);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("message",
                    "An error occured: " + e.getMessage());
        }

        return "redirect:/task/image/"+tid;
    }

    @GetMapping(value="/image/{tid}")
    public ModelAndView image(@PathVariable(name="tid") Long tid) {
        ModelAndView modelAndView = new ModelAndView("raport/image");
        List<String> strings = storageService.loadAll(tid).map(path->path.toString()).filter(s->s.contains(".jpg")||s.contains(".jpeg")||s.contains(".png")).collect(Collectors.toList());
        modelAndView.addObject("files", strings);
        List<String> videos = storageService.loadAll(tid).map(path->path.toString()).filter(s->s.contains(".mp4")||s.contains(".mov")).collect(Collectors.toList());
        modelAndView.addObject("videos", videos);
        modelAndView.addObject("ip_address",serverAddress);
        return modelAndView;
    }


    @GetMapping("/{id}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, @PathVariable Long id) {
        Resource file = storageService.loadAsResource(""+id+"/"+filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/delete/{id}/{filename}")
    public String delete(@PathVariable Long id, @PathVariable String filename){
        filename = "" + id + "/" + filename;
        storageService.deleteFile(filename);
        return "redirect:/task/image/"+id;
    }

}
