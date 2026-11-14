package com.houseprojectbackend.service;

import com.houseprojectbackend.po.Housetable;
import com.houseprojectbackend.po.RequestResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AvailabilityService {

    RequestResponse findHousetableById(Integer id);
    RequestResponse findDistinctHouseCity(String province);
    RequestResponse findDistinctHouseRentForm(String province);
    RequestResponse findDistinctHouseType(String province);
    RequestResponse ComplexQueries(Map hash);
    RequestResponse findDistinctProvince();
}
