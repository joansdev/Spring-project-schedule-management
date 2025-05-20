package com.example.schedulemanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    // 속성
    private Long id;
    private String title;
    private String username;
    private String password;

    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

}
