package com.popova.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class RequestDataInput {

    @NotBlank
    private String url;

    @Min(0)
    private int maxPages;
}
