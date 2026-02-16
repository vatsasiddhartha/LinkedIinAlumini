package com.siddhartha.Alumini_Searcher.dto;

public class RequestDto {
    private String designation;     // mandatory
    private String university;      // mandatory
    private Integer passoutYear;    // optional

    // ✅ getters and setters
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }

    public Integer getPassoutYear() { return passoutYear; }
    public void setPassoutYear(Integer passoutYear) { this.passoutYear = passoutYear; }
}
