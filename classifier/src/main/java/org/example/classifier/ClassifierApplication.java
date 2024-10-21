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
									//	   1     0   2           1    2

//카데고리 생성하기 만들기
// 생성하기 만들기 (현재 여기까지 하는중) 카테고리 만든다면 그 폴도 또한 마들어야한다.
//그다음 수정삭 만들기
//여기까지 완성




//오류정정 하는거 만들기
//오류 정정한것들 목록 보여줘수 수락할지 거절하지 선택
//수락한경우 랜덤으로 데이터셋 폴더로 넘어가게 만들기


//파이썬 모델 만드는거 재실행하기 이걸로 끝
//최종적으로 웹서버에 올리기