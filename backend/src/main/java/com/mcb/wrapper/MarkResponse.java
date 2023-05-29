package com.mcb.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkResponse {

    private int markId;
    private LocalDateTime date;
    private int mark;
    private int studentId;
    private int subjectId;

    public MarkResponse(int mark) {
        this.mark = mark;
    }
}
