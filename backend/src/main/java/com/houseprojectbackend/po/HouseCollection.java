package com.houseprojectbackend.po;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table(name = "HouseCollection")
public class HouseCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="uid")
    private Integer uid;

    @Column(name="hid")
    private Integer hid;
}
