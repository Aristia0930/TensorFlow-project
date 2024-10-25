package org.example.classifier.controller;

import org.example.classifier.dto.CategoryDto;
import org.example.classifier.dto.NewImageDto;
import org.example.classifier.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("")
    public String manager_page(Model model){
        List<CategoryDto> categoryNames= adminService.names();

        model.addAttribute("category_list",categoryNames);



        return "managerPage";
    }



    //훈력 선별페이지
    @GetMapping("/train")
    public String trainPage(Model model){
       List<NewImageDto> imges= adminService.imageTrain();
       model.addAttribute("imges",imges);
       List<CategoryDto> categoryNames= adminService.names();
        model.addAttribute("category_list",categoryNames);


        return "trainPage";
    }




}
