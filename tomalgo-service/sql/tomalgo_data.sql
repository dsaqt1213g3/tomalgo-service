DROP DATABASE IF EXISTS tomalgo;
CREATE DATABASE tomalgo;
USE tomalgo;	

CREATE TABLE USER (
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username varchar(30) NOT NULL UNIQUE KEY,
  password binary(20) NOT NULL,
  mail varchar (40) NOT NULL,
  street TEXT,
  birth DATE,
  enterprise BOOLEAN NOT NULL,
  enable BOOLEAN NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE EVENT (
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  enterprise INTEGER NOT NULL,
  text varchar (160) NOT NULL,
  inidate DATETIME NOT NULL,
  enddate DATETIME NOT NULL,
  promo BOOLEAN NOT NULL,
  FOREIGN KEY (enterprise) REFERENCES USER (id)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE TAG (
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name varchar (30) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE RL_TAG (
  user INTEGER NOT NULL,
  tag INTEGER NOT NULL,
  PRIMARY KEY (user, tag),
  FOREIGN KEY (user) REFERENCES USER(id),
  FOREIGN KEY (tag) REFERENCES TAG(id)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE RL_EVENT (
  user INTEGER NOT NULL,
  event INTEGER NOT NULL,
  PRIMARY KEY (user, event),
  FOREIGN KEY (user) REFERENCES USER(id),
  FOREIGN KEY (event) REFERENCES EVENT(id)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------

/* Clientes */
INSERT INTO `user` VALUES ('1', 'uri', 0x2C6D680F5C570BA21D22697CD028F230E9F4CD56, 'uri@foo.com', null, '9999-12-31', '0', '1');
INSERT INTO `user` VALUES ('2', 'ainowa', 0xCB9493842C4A4AAD79BC6C8060A07B57944D4C81, 'ainowa@foo.com', null, '9999-12-30', '0', '1');
INSERT INTO `user` VALUES ('3', 'fernando', 0xEC3E661D7BC7BFBF5334E7DFAD309F947DACE5F7, 'fernando@foo.com', null, '9999-11-30', '0', '1');
INSERT INTO `user` VALUES ('4', 'sergio', 0x6ED32EDF4E92AB3C0A4DC6F90242953C344051AD, 'sergio@foo.com', null, '9998-12-31', '0', '1');
INSERT INTO `user` VALUES ('5', 'albert', 0x7AA129F67FDE68C6D88AA58B8B8C5C28EB7DD3A3, 'albert@foo.com', null, '9989-12-31', '0', '1');

/* Empresas */
INSERT INTO `user` VALUES ('6', 'tomalgo', 0x394ED584A2984E057FFC59F99FAB7EACDA4B6C7D, 'tomalgo@foo.com', "C:Viva la Birra;n 7", null, '1', '1');
INSERT INTO `user` VALUES ('7', 'nocturnus', 0x394ED584A2984E057FFC59F99FAB7EACDA4B6C7D, 'nocturnus@foo.com', "C:antigua;n 11", null, '1', '1');

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('1', 'Jawaiano');
INSERT INTO `tag` VALUES ('2', 'Nocturno');
INSERT INTO `tag` VALUES ('3', 'Copas');
INSERT INTO `tag` VALUES ('4', 'Romantico');
INSERT INTO `tag` VALUES ('5', 'Negocios');

-- ----------------------------
-- Records of event
-- ----------------------------
INSERT INTO `event` VALUES ('1', '6', 'copas gratis en tomamos algo', '2012-12-05 13:49:54', '2013-01-23 13:49:58', '0');
INSERT INTO `event` VALUES ('2', '7', 'ambiente tetrico y chupito de bienvenida', '2012-12-05 13:49:54', '2013-01-23 13:49:58', '0');
INSERT INTO `event` VALUES ('3', '6', 'Viernes 11 Enero - BirrenFest!!', '2012-12-03 13:49:54', '2013-01-10 13:49:58', '1');

-- ----------------------------
-- Records of rl_tag
-- ----------------------------
INSERT INTO `rl_tag` VALUES ('1', '2');
INSERT INTO `rl_tag` VALUES ('1', '3');
INSERT INTO `rl_tag` VALUES ('3', '1');
INSERT INTO `rl_tag` VALUES ('3', '3');
INSERT INTO `rl_tag` VALUES ('6', '3');
INSERT INTO `rl_tag` VALUES ('7', '2');