package com.example.arcGIS.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunalResponse implements Serializable {

    private String code;
    private String message;
    private String ojbID;
}
