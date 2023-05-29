package com.mcb.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkRequest {

    private LocalDateTime date;
    private int mark;
    private int studentId;
    private int subjectId;
}
