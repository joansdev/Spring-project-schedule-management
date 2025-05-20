package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.dto.RequestDto;
import com.example.schedulemanagement.dto.ResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedules")
public class Controller {

    // 일정 리스트
    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    // 일정 작성 - Post API
    @PostMapping
    public ResponseDto createSchedule(@RequestBody RequestDto dto) {

        // 식별자 +1
        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;

        // Schedule 객체 생성
        Schedule schedule = new Schedule(scheduleId, dto.getTitle(), dto.getUsername(), dto.getPassword(), dto.getDateCreated(), dto.getDateModified());
        // JDBC 환경임에 따라, 날짜 관련 데이터들을 명시적으로 기재/요청 -> JPA 환경에선 @CreatedDate, @LastModifiedDate 등 어노테이션 사용

        // 데이터 저장
        scheduleList.put(scheduleId, schedule);

        return new ResponseDto(schedule);
    }

    // 일정 전체 조회 - Get API
    @GetMapping
    public List<ResponseDto> getSchedules(
            @RequestParam(required = false) String modifiedDate,
            @RequestParam(required = false) String username
    ) {
        return scheduleList.values().stream()
                .filter(schedule -> {
                    boolean matchDate = true;
                    boolean matchUser = true;

                    if (modifiedDate != null && !modifiedDate.isBlank()) {
                        matchDate = schedule.getDateModified().toLocalDate().toString().equals(modifiedDate);
                    }
                    if (username != null && !username.isBlank()) {
                        matchUser = schedule.getUsername().equals(username);
                    }

                    return matchDate && matchUser;
                })
                .sorted(Comparator.comparing(Schedule::getDateModified).reversed())
                .map(ResponseDto::new)
                .collect(Collectors.toList());
    }

    // 일정 단건 조회 - Get API
    @GetMapping("/{id}")
    public ResponseDto getSchedule(@PathVariable Long id) {
        Schedule schedule = scheduleList.get(id);
        return new ResponseDto(schedule);
    }

    // 일정 수정 - Patch API
    @PatchMapping("/{id}")
    public ResponseDto updateSchedule(
            @PathVariable Long id,
            @RequestBody RequestDto dto
    ) {
        Schedule schedule = scheduleList.get(id);
        if (schedule == null) {
            throw new IllegalArgumentException("해당 일정이 존재하지 않습니다.");
        }

        // 비밀번호 확인
        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 수정 가능한 필드만 업데이트
        if (dto.getTitle() != null) {
            schedule.setTitle(dto.getTitle());
        }
        if (dto.getUsername() != null) {
            schedule.setUsername(dto.getUsername());
        }

        // 수정일 갱신
        schedule.setDateModified(LocalDateTime.now());

        return new ResponseDto(schedule);
    }

    // 일정 삭제 - Delete API
    @DeleteMapping("/{id}")
    public String deleteSchedule(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        Schedule schedule = scheduleList.get(id);
        if (schedule == null) {
            throw new IllegalArgumentException("해당 일정이 존재하지 않습니다.");
        }

        String password = request.get("password");
        if (password == null || !schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        scheduleList.remove(id);
        return "일정이 성공적으로 삭제되었습니다.";
    }
}