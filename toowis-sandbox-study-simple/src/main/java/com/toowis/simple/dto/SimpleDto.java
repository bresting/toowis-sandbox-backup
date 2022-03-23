package com.toowis.simple.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String email;
    private int age;
}
