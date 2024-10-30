package org.example.classifier.controller;

import org.example.classifier.dto.CategoryDto;
import org.example.classifier.dto.NewImageDto;
import org.example.classifier.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.LogOutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @Value("${custom.py-path}")
    private String pyPath;


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

    //트레인쪽 수락
    @PatchMapping("/train/add")
    public String addtrain(NewImageDto newImageDto,@RequestParam("categoryName") String name){
        int rs=adminService.addtrain(newImageDto,name);

        if (rs==1){
            return "success";
        }
        else{
            return "false";
        }

    }

    //트레인쪽 거절
    @PatchMapping("/train/refuse")
    public String retrain(@RequestParam("id") int id){
        int rs=adminService.retrain(id);

        if (rs==1){
            return "success";
        }
        else{
            return "false";
        }

    }

    //파이선 코드 실행하기
    @GetMapping("/pycode")
    public String pyCOde() throws IOException, InterruptedException, TimeoutException {
        System.out.println("py코드 진입");
        //카테고리수
        int classCount=adminService.getClassNum();
//
//        try{
//            ProcessBuilder py = new ProcessBuilder("python", pyPath, String.valueOf(classCount));
//            Process process =py.start();
//
//            int exitCode = process.waitFor();
//
//            // Python 스크립트의 표준 출력 읽기
//            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            StringBuilder output = new StringBuilder();
//            String line;
//            while ((line = outputReader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//            System.out.println("Python Output: " + output.toString());
//
//            // Python 스크립트의 오류 출력 읽기
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            StringBuilder errorOutput = new StringBuilder();
//            while ((line = errorReader.readLine()) != null) {
//                errorOutput.append(line).append("\n");
//            }
//            System.out.println("Python Error Output: " + errorOutput.toString());
//
//            // 실행 결과에 따른 반환 처리
//            if (exitCode == 0) {
//                return "success";
//            } else {
//                return "failed with exit code " + exitCode + " - Error Output: " + errorOutput.toString();
//            }
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//
//            }
        new ProcessExecutor().command("python", pyPath,String.valueOf(classCount))
                .redirectOutput(new LogOutputStream() {
                    @Override
                    protected void processLine(String line) {
                        System.out.println(line);
                    }
                })
                .execute();
        return "success";


    }


}
