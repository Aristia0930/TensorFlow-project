package org.example.classifier.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewImageDto {
    private int id;
    private String name;
    private String path;
    private String category;
    private int approval;
    private int status;
    private LocalDateTime createdAt;  // created_at 필드


}
