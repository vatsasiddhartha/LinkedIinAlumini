package com.siddhartha.Alumini_Searcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response {

    private String name;
    private String designation;
    private String university;
    private String location;
    private String linkedinHeadline;
    private Integer passoutYear;
}
