package com.example.schoolsearch.service;

import com.example.schoolsearch.domain.School;
import com.example.schoolsearch.domain.Search;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolSearchService {
    private Search search;
    private School school;

    public List<School> getSchool(Search search) {
        List<School> schoolList = new ArrayList<School>();
        try {
            StringBuilder urlBuilder = new StringBuilder("https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=b8c6c464ae74677db1f6f0253a38f066&svcType=api&svcCode=SCHOOL&contentType=json&gubun="); /*URL*/
            String gubun = "";
            // 입력 받은 학교 유형에 따라 조건문 실행
            switch (search.getType().toString()) {
                case "초등학교": gubun = "elem_list";   break;
                case "중학교": gubun = "midd_list";    break;
                case "고등학교": gubun = "high_list";   break;
                case "대학교": gubun = "univ_list";    break;
                case "기타": gubun = "alte_list";     break;
                default: gubun = "univ_list";       break;
            }

            urlBuilder.append("" + URLEncoder.encode(gubun,"UTF-8"));
            urlBuilder.append("&searchSchulNm=" + URLEncoder.encode(search.getKeyword(),"UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            BufferedReader rd;

            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                JSONObject schoolJSON = new JSONObject(line);
                JSONObject dataSearch = schoolJSON.getJSONObject("dataSearch");
                JSONArray content = dataSearch.getJSONArray("content");
                for (int i=0; i<content.length(); i++) {
                    JSONObject c = content.getJSONObject(i);
                    System.out.println(c.optString("schoolName"));
                    School school = new School(c.optString("schoolName"), c.optString("region"), c.optString("adres"));
                    schoolList.add(school);
                }
                return schoolList;
            }
            rd.close();
            conn.disconnect();
        } catch (IOException e) {
                System.out.println (e.toString());
        }
        return schoolList;
    }
}
