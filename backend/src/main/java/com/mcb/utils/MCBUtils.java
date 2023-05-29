package com.mcb.utils;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
public class MCBUtils {
    public static ResponseEntity<String> getResponse(String responseMes, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\""+responseMes+"\"}",httpStatus);
    }


}
