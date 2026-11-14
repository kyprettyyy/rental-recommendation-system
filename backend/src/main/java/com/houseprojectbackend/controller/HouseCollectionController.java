package com.houseprojectbackend.controller;

import com.houseprojectbackend.dao.HouseCollectionRepository;
import com.houseprojectbackend.po.*;
import com.houseprojectbackend.service.userService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
@RequestMapping("/collection")
public class HouseCollectionController {
    @Autowired
    private HouseCollectionRepository hou;

    @PersistenceContext
    private EntityManager em;

    /**
     * @param
     * @return
     */
    @GetMapping("/{username}/{hid}")
    public RequestResponse houseCollectionSe(@PathVariable("username") String username, @PathVariable("hid") int hid) {
        JPAQueryFactory jpa = new JPAQueryFactory(em);
        QHouseCollection column = QHouseCollection.houseCollection;
        QUserInformation uColumn = QUserInformation.userInformation;
        List<Integer> uData = jpa.select(uColumn.id).from(uColumn).where(uColumn.username.eq(username)).fetch();
        QHousetable houseColumn = QHousetable.housetable;
        List<Integer> fetch = jpa.select(column.hid).from(column).where(column.uid.eq(uData.size() > 0?uData.get(0):0)).fetch();
        System.out.println(fetch);
        List<Housetable> houseData = null;
        if (hid == 0) {
            houseData = jpa.select(houseColumn).from(houseColumn).where(houseColumn.id.in(fetch)).fetch();
            System.out.println(houseData);
        } else {
            System.out.println("?????");
            houseData = jpa.select(houseColumn).from(houseColumn).where(houseColumn.id.in(fetch.contains(hid) ? hid : 0)).fetch();
        }
        return RequestResponse.setData(houseData).success("成功");
    }

    /**
     * @param
     * @return
     */
    @PostMapping("/{uid}/{hid}")
    public RequestResponse houseCollectionIn(@PathVariable("uid") String uid, @PathVariable("hid") int hid) {
        JPAQueryFactory jpa = new JPAQueryFactory(em);
        QUserInformation uColumn = QUserInformation.userInformation;
        QHouseCollection column = QHouseCollection.houseCollection;
        List<Integer> fetch = jpa.select(uColumn.id).from(uColumn).where(uColumn.username.eq(uid)).fetch();
        HouseCollection collection = new HouseCollection();
        collection.setUid(fetch.size() > 0 ? fetch.get(0) : 0);
        collection.setHid(hid);
        return RequestResponse.setData(hou.save(collection)).success("收藏成功");
    }

    /**
     * @param
     * @return
     */
    @DeleteMapping("/{username}/{id}")
    @Commit
    public RequestResponse houseCollectionDE(@PathVariable("username") String username, @PathVariable("id") int id) {
        JPAQueryFactory jpa = new JPAQueryFactory(em);
        QUserInformation uColumn = QUserInformation.userInformation;
        QHouseCollection column = QHouseCollection.houseCollection;
        List<Integer> fetch = jpa.select(uColumn.id).from(uColumn).where(uColumn.username.eq(username)).fetch();
        List<Integer> fetch1 = jpa.select(column.id).from(column).where(column.uid.eq(fetch.size() > 0 ? fetch.get(0) : 0).and(column.hid.eq(id))).fetch();
        System.out.println(fetch1);
        HouseCollection collection = new HouseCollection();
        collection.setId(fetch1.size() > 0 ? fetch1.get(0) : 0);
        hou.delete(collection);
        return RequestResponse.setData("成功").success("取消收藏成功");
    }

}
