package org.example.classifier.controller;

import org.example.classifier.dto.CategoryDto;
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

    @PostMapping("/add")
    public String addCategory(CategoryDto categoryDto,Model model){
        int create = adminService.addName(categoryDto.getName());
        if (create==0){
            return "redirect:/admin?rs="+"false";
        }

        return "redirect:/admin?rs="+"success";
    }

    @PatchMapping("/edit")
    public String edit(@RequestParam("name") String name, @RequestParam("newName") String newName) {
       int rs=adminService.editName(name,newName);
       if (rs==0){
           return "redirect:/admin";
       }
       else{
           return "redirect:/admin";
       }





    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("name") String name) {
        adminService.delete(name);
        return "redirect:/admin";


    }



}
