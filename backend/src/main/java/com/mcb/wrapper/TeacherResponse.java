package com.mcb.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {
    private int teacherId;
    private int groupId;
    private int subjectId;
}
