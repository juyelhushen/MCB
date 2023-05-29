package com.mcb.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    private String firstName;
    private String lastName;
    private int groupId;
}
