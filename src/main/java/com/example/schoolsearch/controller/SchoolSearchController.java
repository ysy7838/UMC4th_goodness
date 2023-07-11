package com.example.schoolsearch.controller;

import com.example.schoolsearch.domain.School;
import com.example.schoolsearch.domain.Search;
import com.example.schoolsearch.dto.SearchDto;
import com.example.schoolsearch.service.SchoolSearchService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api")
public class SchoolSearchController {
    private final SchoolSearchService searchService;

    @GetMapping("/auth/sign-up/club/search")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="동아리 검색 결과", notes = "동아리 소속 학교 검색 결과를 노출하기 위한 로직")
    public List<School> searchResult(@RequestBody @Valid SearchDto searchDto) {
        Search search = searchDto.toSearch();
        return searchService.getSchool(search);
    }
}
