package com.example.schedulemanagement.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RequestDto {

    private String title;
    private String username;
    private String password;

    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

}
