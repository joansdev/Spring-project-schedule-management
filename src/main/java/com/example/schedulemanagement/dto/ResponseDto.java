package com.example.schedulemanagement.dto;

import com.example.schedulemanagement.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseDto {

    private Long id;
    private String title;
    private String username;
    private String password;

    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

    public ResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.username = schedule.getUsername();
        this.password = schedule.getPassword();
        this.dateCreated = schedule.getDateCreated();
        this.dateModified = schedule.getDateModified();
    }

}
