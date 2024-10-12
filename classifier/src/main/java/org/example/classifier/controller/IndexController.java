package org.example.classifier.controller;

import lombok.Getter;
import org.example.classifier.service.ClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;


@Controller
public class IndexController {
    @Autowired
    private ClassifierService classifierService;



    @GetMapping({"","/"})
    public String main_page(){
        return "mainPage";
    }

    //오류정보 수정요청
    @PostMapping("/edite")
    public String edite(@RequestParam("file") MultipartFile file , String text, Model rs){
        boolean check=classifierService.make_dir();
        if (check){
            System.out.println("파일생성완료");
            boolean fileMake= classifierService.makeFile(file,text);
            if (fileMake) {
                System.out.println("파일생성성공");
                rs.addAttribute("rs", "서버에 파일전송 완료");
            }
            else{
                System.out.println("파일생성실패");
                rs.addAttribute("rs","서버에 파일전송 실패");
            }
        }
        else{
            System.out.println("이미생성됨");
            boolean fileMake= classifierService.makeFile(file,text);
            if (fileMake) {
                System.out.println("파일생성성공");
                rs.addAttribute("rs", "서버에 파일전송 완료");
            }
            else{
                System.out.println("파일생성실패");
                rs.addAttribute("rs","서버에 파일전송 실패");
            }
        }




        return "redirect:/";
    }

}
