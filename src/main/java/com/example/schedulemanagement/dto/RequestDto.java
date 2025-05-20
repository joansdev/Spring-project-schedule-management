package com.example.schedulemanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestDto {

    private String title;
    private String username;
    private String password;

    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

}
