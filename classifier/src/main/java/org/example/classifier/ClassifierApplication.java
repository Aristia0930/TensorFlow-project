package org.example.classifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassifierApplication.class, args);
	}

}


//1. 오류 이미지 파일 임시폴더로 저장하고 db에도 저장
//2. 정답인경우에도 위와 같은걸 함
//현재 필요한 db는 카데고리만 기록할 db 와
// 사진 이름/이미지저장한주소/날짜/카데고리/승인(승인/미승인/거절)/상태(오류수정/정답)
