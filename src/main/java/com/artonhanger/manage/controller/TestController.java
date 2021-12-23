package com.artonhanger.manage.controller;

import com.artonhanger.manage.enums.ErrorEnum;
import com.artonhanger.manage.exception.AOHException;
import com.artonhanger.manage.model.dto.ArtworkUploadDto;
import com.artonhanger.manage.model.dto.RegisterUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/test")
public class TestController {
    @GetMapping(value = "")
    public String index(){
        return "test/testUpload";
    }
    @PostMapping(value = "/submit")
    public String submit(ArtworkUploadDto artworkUploadDto){
        for (String description : artworkUploadDto.getDescriptions()) {
            System.out.println(description);
        }
        return"test/testUpload";
    }
    @ResponseBody
    @PostMapping(value = "/reg")
    public ResponseEntity registerTest(RegisterUserDto registerUserDto) {
        System.out.println("regitser");
        return ResponseEntity.ok("new");
    }
    @GetMapping(value = "/error")
    public String error(){
        throw new RuntimeException("의도된 예외입니다");
    }
}
