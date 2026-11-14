package com.houseprojectbackend.service.impl;

import com.houseprojectbackend.dao.HousetableRepository;
import com.houseprojectbackend.po.Housetable;
import com.houseprojectbackend.po.QHousetable;
import com.houseprojectbackend.po.RequestResponse;
import com.houseprojectbackend.service.AvailabilityService;
import com.houseprojectbackend.service.ConfigService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLExpressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

@Service
public class AvailabilityServiceImpl extends ConfigService implements AvailabilityService {
    @Autowired
    private HousetableRepository housetableRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public RequestResponse findDistinctProvince() {
        return RequestResponse.setData(housetableRepository.findDistinctProvince()).success("成功");
    }

    @Override
    public RequestResponse findHousetableById(Integer id) {
        return RequestResponse.setData(housetableRepository.findById(id)).success("成功");
    }

    @Override
    public RequestResponse findDistinctHouseCity(String province) {
        return RequestResponse.setData(housetableRepository.findDistinctHouseCity(province)).success("成功");
    }

    @Override
    public RequestResponse findDistinctHouseRentForm(String province) {
        return RequestResponse.setData(housetableRepository.findDistinctHouseRentForm(province)).success("成功");
    }

    @Override
    public RequestResponse findDistinctHouseType(String province) {
        return RequestResponse.setData(housetableRepository.findDistinctHouseType(province)).success("成功");
    }

    @Override
    public RequestResponse ComplexQueries(Map hash) {
        JPAQueryFactory fa = new JPAQueryFactory(em);
        QHousetable column = QHousetable.housetable;
        out.println(hash);
        BooleanExpression gt = column.houseCity.like("%").and(column.province.eq((String) hash.get("province")));
        out.println(hash);
        if (hash.containsKey("HouseCity") && hash.get("HouseCity").toString() != "") {
            gt = gt.and(column.houseCity.eq((String) hash.get("HouseCity")));
        }
        if (hash.containsKey("HouseTotalMoney") && hash.get("HouseTotalMoney").toString() != "") {
            //将Between 内容 从String 转换为Int类型
            String[] array = ((String) hash.get("HouseTotalMoney")).split("-");
            gt = gt.and(Expressions.numberTemplate(Integer.class, "REPLACE(SUBSTRING_INDEX({0}, '/', 1), '元', '')",
                    column.houseTotalMoney).between(Integer.parseInt(array[0]), Integer.parseInt(array[1])));
        }
        if (hash.containsKey("HouseRentForm") && hash.get("HouseRentForm").toString() != "") {
            gt = gt.and(column.houseRentForm.eq((String) hash.get("HouseRentForm")));
        }
        if (hash.containsKey("HouseBuildingArea") && hash.get("HouseBuildingArea").toString() != "") {
            //将Between 内容 从String 转换为Int类型
            String[] array = ((String) hash.get("HouseBuildingArea")).split("-");
            gt = gt.and(Expressions.numberTemplate(Integer.class, "replace({0}, '㎡', '')",
                            column.houseBuildingArea)
                    .between(Integer.parseInt(array[0]), Integer.parseInt(array[1])));
        }
        if (hash.containsKey("HouseType") && hash.get("HouseType").toString() != "") {
            gt = gt.and(column.houseType.eq((String) hash.get("HouseType")));
        }
        return RequestResponse.setData(fa.select(column).from(column).where(gt).limit(getLimitSize()).offset(Integer.parseInt(hash.get("itemSize").toString())).fetch()).success("成功");
    }


}
