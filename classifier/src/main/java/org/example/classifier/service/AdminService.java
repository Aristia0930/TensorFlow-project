package org.example.classifier.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.example.classifier.dto.CategoryDto;
import org.example.classifier.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
//분류된 이미지를 관리자가 선별하여 모델 업데이트
public class AdminService {

    @Value("${custom.train}")
    private String trainPath;

    @Value("${custom.validation}")
    private String validationPath;
    private final CategoryMapper categoryMapper;

    public AdminService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    //카테고리 이름 조회
    public List<CategoryDto> names(){
        return categoryMapper.categoryName();
    }

    //카테고리 추가
    public int addName(String name){

        //같은이름존재하는지 확인
        int count=categoryMapper.countName(name);


        if (count>0){
            System.out.println("중복입니다");
            return 0;
        }
        else{
            System.out.println("입력가능");
            //카테고리 추가하자
            categoryMapper.addName(name);
            String path1=trainPath+"/"+name;
            String path2=validationPath+"/"+name;
            File folder1 = new File(path1);
            File folder2= new File(path2);
            makeFile(folder1);
            makeFile(folder2);

            //폴더 생성하기
            return 1;
        }

    }
    public void makeFile(File folder){
        if (!folder.exists()) {
            // 폴더 생성
            if (folder.mkdir()) {
                System.out.println("폴더가 성공적으로 생성되었습니다: " + folder.getPath());
            } else {
                System.out.println("폴더 생성에 실패했습니다.");
            }
        } else {
            System.out.println("폴더가 이미 존재합니다: " + folder.getPath());
        }

        // 폴더 객체 반환
    }
    @Transactional
    public int editName(String name,String newName){
        String path1=trainPath+"/"+name;
        String path2=validationPath+"/"+name;

        File folder1 = new File(path1);
        File newfolder1 = new File(trainPath+"/"+newName);
        File folder2= new File(path2);
        File newfolder2 = new File(validationPath+"/"+newName);
        try{


        int updatedRows = categoryMapper.editName(name,newName);
        if (updatedRows > 0) {
            System.out.println("카테고리가 성공적으로 업데이트되었습니다.");
            folder1.renameTo(newfolder1);
            folder2.renameTo(newfolder2);
            return 0;
        } else {
            System.out.println("업데이트 실패: 해당 ID의 카테고리가 존재하지 않거나 변경 사항이 없습니다.");
            return 1;
        }
    } catch (Exception e) {
        System.err.println("업데이트 중 오류가 발생했습니다: " + e.getMessage());
        return 2;
    }


    }

    //삭제
    @Transactional
    public int delete(String name){

        String path1=trainPath+"/"+name;
        String path2=validationPath+"/"+name;
        File folder1 = new File(path1);
        File folder2= new File(path2);

        try {
            // folder1이 존재하는 경우
            if (folder1.exists() && folder1.isDirectory()) {
                FileUtils.cleanDirectory(folder1); // 하위 폴더와 파일 모두 삭제
                folder1.delete(); // 대상 폴더 삭제
            }

            // folder2가 존재하는 경우
            if (folder2.exists() && folder2.isDirectory()) {
                FileUtils.cleanDirectory(folder2); // 하위 폴더와 파일 모두 삭제
                folder2.delete(); // 대상 폴더 삭제
            }

            categoryMapper.deleteCa(name);

            return 1; // 성공적으로 삭제된 경우
        } catch (IOException e) {
            e.printStackTrace(); // 예외 로그 출력
            return 0; // 삭제 실패
        } catch (Exception e) {
            e.printStackTrace(); // 다른 예외에 대한 로그 출력
            return 0; // 삭제 실패
        }

    }








}
