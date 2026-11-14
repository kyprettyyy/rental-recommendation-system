package com.houseprojectbackend.dao;

import com.houseprojectbackend.po.HouseCollection;
import com.houseprojectbackend.po.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseCollectionRepository extends JpaRepository<HouseCollection,Long> {
}
