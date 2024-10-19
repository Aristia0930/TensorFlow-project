package org.example.classifier.controller;

import org.example.classifier.dto.CategoryDto;
import org.example.classifier.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("")
    public String manager_page(Model model){
        List<CategoryDto> categoryNames= managerService.names();

        model.addAttribute("category_list",categoryNames);



        return "managerPage";
    }

    @PostMapping("/add")
    public String addCategory(CategoryDto categoryDto,Model model){
        int create = managerService.addName(categoryDto.getName());
        if (create==0){
            return "redirect:/admin?rs="+"false";
        }

        return "redirect:/admin";
    }

}
