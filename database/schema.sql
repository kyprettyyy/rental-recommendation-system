-- Extracted schema only. No original data or user accounts.
SET NAMES utf8mb4;
CREATE TABLE `housetable` (
  `id` int NOT NULL AUTO_INCREMENT,
  `houseCity` varchar(255) DEFAULT NULL COMMENT '地址区域',
  `houseTitle` varchar(255) DEFAULT NULL COMMENT '小区名称',
  `houseTotalMoney` varchar(255) DEFAULT NULL COMMENT '房屋总价',
  `houseAdvange` varchar(255) DEFAULT NULL COMMENT '房屋优势',
  `houseRentForm` varchar(255) DEFAULT NULL COMMENT '租聘方式',
  `houseType` varchar(255) DEFAULT NULL COMMENT '房屋类型',
  `houseBuildingArea` varchar(255) DEFAULT NULL COMMENT '房屋建筑面积',
  `houseOrientation` varchar(255) DEFAULT NULL COMMENT '房屋朝向',
  `houseDecoration` varchar(255) DEFAULT NULL COMMENT '维护情况',
  `houseDate` varchar(255) DEFAULT NULL COMMENT '入住时间',
  `houseFloor` varchar(255) DEFAULT NULL COMMENT '所在楼层',
  `houseElevator` varchar(255) DEFAULT NULL COMMENT '电梯配备',
  `houseFuelGas` varchar(255) DEFAULT NULL COMMENT '有无燃气',
  `houseRentTerm` varchar(255) DEFAULT NULL COMMENT '租期',
  `houseWatch` varchar(255) DEFAULT NULL COMMENT '看房方式',
  `houseSupporting` varchar(255) DEFAULT NULL COMMENT '配套设施',
  `houseImg` varchar(2000) DEFAULT NULL COMMENT '房屋照片',
  `houseUrl` varchar(255) DEFAULT NULL COMMENT '房屋URL地址',
  `houseCrawlTime` varchar(255) DEFAULT NULL COMMENT '爬取时间',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `userInformation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '账户',
  `pass` varchar(18) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Missing from the supplied dump; reconstructed from the JPA entity.
CREATE TABLE `HouseCollection` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `uid` int NOT NULL,
  `hid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
