CREATE DATABASE if NOT EXISTS `children`;

use `children`;

DROP TABLE if EXISTS `basic_info`;

CREATE TABLE `basic_info` (
  `id` bigint(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `grade` varchar(10),
  `class` varchar(10),
  `sex` tinyint(1),
  `nation` varchar(10),
  `birthday` varchar(20),
  `id_card_no` varchar(20),
  `hukou` varchar(10),
  `hukou_addr` varchar(50),
  `migration` tinyint(1),
  `only_child` tinyint(1),
  `min_living` tinyint(1),
  `imburse` tinyint(1),
  `orphan` tinyint(1),
  `pathography` tinyint(1),
  `special_performance` longtext,
  `other_announcement` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE if EXISTS `contact_info`;

CREATE TABLE `contact_info` (
  `id` bigint(11) NOT NULL,
  `mother_name` varchar(10),
  `mother_company` varchar(50),
  `mother_contact` varchar(20),
  `mother_id_card` varchar(20),
  `father_name` varchar(10),
  `father_company` varchar(50),
  `father_contact` varchar(20),
  `father_id_card` varchar(20),
  `living_addr` varchar(50),
  `other_contact` varchar(50),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE if EXISTS `body_info`;

CREATE TABLE `body_info` (
  `id` bigint(11) NOT NULL,
  `doff_don` tinyint(1),
  `eating` tinyint(1),
  `toileting` tinyint(1),
  `sleeping` varchar(50),
  `sleeping_info` tinyint(1),
  `eating_speed` tinyint(1),
  `appetite` tinyint(1),
  `picky_eating` tinyint(1),
  `picky_eating_info` VARCHAR(50),
  `eating_ability` tinyint(1),
  `food_allergy` tinyint(1),
  `food_allergy_info` varchar(50),
  `health_status` tinyint(1),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;