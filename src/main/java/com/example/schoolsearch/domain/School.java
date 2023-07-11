package com.example.schoolsearch.domain;


import lombok.ToString;

@ToString
public class School {
    private String name;        // 학교명
    private String region;      // 위치한 지역
    private String adres;       // 정확한 주소

    public School(String name, String region, String adres) {
        this.name = name;
        this.region = region;
        this.adres = adres;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getAdres() {
        return adres;
    }
}
