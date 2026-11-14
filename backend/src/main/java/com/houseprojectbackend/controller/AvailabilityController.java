package com.houseprojectbackend.controller;

import com.houseprojectbackend.po.RequestResponse;
import com.houseprojectbackend.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    //省份
    private String province;

    /**
     * 根据 Id 查找房屋信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public RequestResponse findHouseById(@PathVariable("id") Integer id) {
        return availabilityService.findHousetableById(id);
    }

    @GetMapping("/HouseCity")
    public RequestResponse findDistinctHouseCity() {
        return availabilityService.findDistinctHouseCity(province);
    }

    @GetMapping("/HouseRentForm")
    public RequestResponse findDistinctHouseRentForm() {
        return availabilityService.findDistinctHouseRentForm(province);
    }

    @GetMapping("/HouseType")
    public RequestResponse findDistinctHouseType() {
        return availabilityService.findDistinctHouseType(province);
    }

    @GetMapping("/Province")
    public RequestResponse findDistinctProvince() {
        return availabilityService.findDistinctProvince();
    }

    @GetMapping("/choose")
    public void choose(@RequestParam String province) {
        this.province = province;
    }

    @RequestMapping("/ComplexQueries")
    public RequestResponse ComplexQueries(@RequestParam HashMap hash) {
        hash.put("province", province);
        return availabilityService.ComplexQueries(hash);
    }


}
