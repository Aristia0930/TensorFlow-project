package org.example.classifier.controller;

import org.example.classifier.dto.CategoryDto;
import org.example.classifier.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public String addCategory(CategoryDto categoryDto, Model model){
        int create = adminService.addName(categoryDto.getName());
        if (create==0){
            return "false";
        }

        return "success";
    }

    @PatchMapping("/edit")
    public String edit(@RequestParam("name") String name, @RequestParam("newName") String newName) {
        int rs=adminService.editName(name,newName);
        if (rs==0){
            return "success";
        }
        else{
            return "false";
        }





    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("name") String name) {
        adminService.delete(name);
        return "";


    }
}
