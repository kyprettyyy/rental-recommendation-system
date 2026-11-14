package com.houseprojectbackend.dao;

import com.houseprojectbackend.po.Housetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HousetableRepository extends JpaRepository<Housetable,Integer>, QuerydslPredicateExecutor<Housetable> {

    @Query(value = "select distinct houseCity from housetable where province =?1 and houseCity is not null",nativeQuery = true)
    List<String> findDistinctHouseCity(String province);

    @Query(value = "select distinct houseRentForm from housetable where province =?1 and houseRentForm is not null ",nativeQuery = true)
    List<String> findDistinctHouseRentForm(String province);

    @Query(value = "select distinct houseType from housetable where province =?1 and  province is not null ",nativeQuery = true)
    List<String> findDistinctHouseType(String province);

    @Query(value = "select distinct province from  housetable",nativeQuery = true)
    List<String> findDistinctProvince();

}
