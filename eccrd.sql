/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : eccrd

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-04-21 14:57:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `announ_info`
-- ----------------------------
DROP TABLE IF EXISTS `announ_info`;
CREATE TABLE `announ_info` (
  `ANNOUN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ANNOUN_TITLE` varchar(100) DEFAULT NULL COMMENT '标题',
  `ANNOUN_CONTENT` longtext COMMENT '内容',
  `ANNOUN_COUNT` int(11) DEFAULT NULL COMMENT '浏览次数',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间',
  `ANNOUN_FROM` varchar(200) DEFAULT NULL COMMENT '来源',
  `ANNOUN_URL` varchar(200) DEFAULT NULL COMMENT '引用链接',
  `ANNOUN_STATUS` smallint(3) DEFAULT NULL COMMENT '状态',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `USER_ID` int(11) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`ANNOUN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of announ_info
-- ----------------------------

-- ----------------------------
-- Table structure for `cl_info`
-- ----------------------------
DROP TABLE IF EXISTS `cl_info`;
CREATE TABLE `cl_info` (
  `CL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CL_TITLE` varchar(100) DEFAULT NULL COMMENT '标题',
  `CL_TITLE_EN` varchar(500) DEFAULT NULL COMMENT '英文标题',
  `CL_URL` varchar(200) DEFAULT NULL COMMENT 'URL',
  `FILE_ID` int(11) DEFAULT NULL COMMENT '文件ID',
  `CL_TYPE` smallint(3) DEFAULT NULL COMMENT '类型',
  `MARK` varchar(500) DEFAULT NULL,
  `MARK_EN` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`CL_ID`),
  KEY `CL_INFO_FILE` (`FILE_ID`),
  KEY `CL_INFO_TYPE` (`CL_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cl_info
-- ----------------------------

-- ----------------------------
-- Table structure for `cnt_info`
-- ----------------------------
DROP TABLE IF EXISTS `cnt_info`;
CREATE TABLE `cnt_info` (
  `CNT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CNT_TITLE` varchar(200) DEFAULT NULL COMMENT '标题',
  `CNT_TITLE_EN` varchar(500) DEFAULT NULL,
  `CNT_MODEL` int(11) DEFAULT NULL COMMENT '模块',
  `CNT_TYPE` int(11) DEFAULT NULL COMMENT '类别',
  `CNT_CONTENT` longtext COMMENT '内容',
  `CNT_CONTENT_EN` longtext,
  `CNT_COUNT` int(11) DEFAULT NULL COMMENT '浏览次数',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间',
  `CNT_FROM` varchar(200) DEFAULT NULL COMMENT '来源',
  `CNT_URL` varchar(200) DEFAULT NULL COMMENT '引用连接',
  `USER_ID` int(11) DEFAULT NULL COMMENT '操作用户',
  `CNT_STATUS` smallint(3) DEFAULT NULL COMMENT '状态',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`CNT_ID`),
  KEY `CNT_INFO_MODEL` (`CNT_MODEL`),
  KEY `CNT_INFO_TYPE` (`CNT_TYPE`),
  KEY `CNT_INFO_COUNT` (`CNT_COUNT`),
  KEY `CNT_INFO_INSERT_TIME` (`INSERT_TIME`),
  KEY `CNT_INFO_USER` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cnt_info
-- ----------------------------

-- ----------------------------
-- Table structure for `con_info`
-- ----------------------------
DROP TABLE IF EXISTS `con_info`;
CREATE TABLE `con_info` (
  `CON_ID` int(3) NOT NULL AUTO_INCREMENT,
  `CON_CONTENT` longtext COMMENT '文本信息',
  PRIMARY KEY (`CON_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of con_info
-- ----------------------------
INSERT INTO `con_info` VALUES ('1', '<h3 style=\"text-align: justify;\" microsoft=\"\" box-sizing:=\"\" font-weight:=\"\" line-height:=\"\" margin-top:=\"\" margin-bottom:=\"\" font-size:=\"\" white-space:=\"\" background-color:=\"\" margin-left:=\"\" text-align:=\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系方式</h3><table><tbody style=\"box-sizing: border-box;\"><tr style=\"box-sizing: border-box;\" class=\"firstRow\"><td style=\"box-sizing: border-box; padding: 0px; text-align: center; word-break: break-all;\" height=\"42\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;社 长：</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"42\">胡 炜</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"42\" width=\"129\">0371-65998844</td></tr><tr style=\"box-sizing: border-box;\"><td style=\"box-sizing: border-box; padding: 0px; text-align: center; word-break: break-all;\" height=\"41\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;副总编辑：</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"41\">宋先锋</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"41\" width=\"29\">0371-65957007</td></tr><tr style=\"box-sizing: border-box;\"><td style=\"box-sizing: border-box; padding: 0px; text-align: center; word-break: break-all;\" height=\"38\">&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 副书记：<br/></td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"38\">蒋洪杰</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"38\" width=\"29\">0371-65996869</td></tr><tr style=\"box-sizing: border-box;\"><td style=\"box-sizing: border-box; padding: 0px; text-align: center; word-break: break-all;\" height=\"44\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;副总编辑：</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"44\">安红伟</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"44\" width=\"29\">0371-65993062</td></tr><tr style=\"box-sizing: border-box;\"><td style=\"box-sizing: border-box; padding: 0px; text-align: center; word-break: break-all;\" height=\"41\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 总编办主任：</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"41\">姜春燕</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"41\" width=\"29\">0371-61316590</td></tr><tr style=\"box-sizing: border-box;\"><td style=\"box-sizing: border-box; padding: 0px; text-align: center; word-break: break-all;\" height=\"44\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 副主任：</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"44\">刘 罡</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"44\" width=\"29\">0371-65994366</td></tr><tr style=\"box-sizing: border-box;\"><td style=\"box-sizing: border-box; padding: 0px; text-align: center; word-break: break-all;\" height=\"44\">&nbsp; &nbsp;科研财务部主任：</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"44\">王黎春</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"44\" width=\"29\">0371-53690693</td></tr><tr style=\"box-sizing: border-box;\"><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"45\">科技宣传中心主任：</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"45\">贾志远（驻北京）</td><td style=\"box-sizing: border-box; padding: 0px;\" height=\"45\" width=\"29\"><br/></td></tr><tr style=\"box-sizing: border-box;\"><td style=\"box-sizing: border-box; padding: 0px; text-align: center; word-break: break-all;\" height=\"43\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 副主任：</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"43\">张 瑞</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"43\" width=\"29\">0371-53690695</td></tr><tr style=\"box-sizing: border-box;\"><td style=\"box-sizing: border-box; padding: 0px;\" height=\"43\"><br/></td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"43\">欧阳曦</td><td style=\"box-sizing: border-box; padding: 0px; text-align: center;\" height=\"43\" width=\"29\">0371-65528536</td></tr></tbody></table><p style=\"text-align: center;\"><br/></p>');

-- ----------------------------
-- Table structure for `dict_info`
-- ----------------------------
DROP TABLE IF EXISTS `dict_info`;
CREATE TABLE `dict_info` (
  `DICT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DICT_COLUMN` varchar(50) DEFAULT NULL COMMENT '字典列名',
  `DICT_NAME` varchar(100) DEFAULT NULL COMMENT '字典名称',
  `DICT_NAME_EN` varchar(100) DEFAULT NULL,
  `DICT_VALUE` smallint(3) DEFAULT NULL COMMENT '字典值',
  `DICT_SORT` smallint(3) DEFAULT NULL COMMENT '排序值',
  PRIMARY KEY (`DICT_ID`),
  KEY `DICT_VALUE_INDEX` (`DICT_VALUE`),
  KEY `DICT_SORT_INDEX` (`DICT_SORT`),
  KEY `DICT_COLUMN_INDEX` (`DICT_COLUMN`) USING HASH
) ENGINE=MyISAM AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict_info
-- ----------------------------
INSERT INTO `dict_info` VALUES ('1', 'FILE_TYPE', '图片', null, '1', '1');
INSERT INTO `dict_info` VALUES ('2', 'FILE_TYPE', '文件', null, '2', '2');
INSERT INTO `dict_info` VALUES ('3', 'USER_STATUS', '正常', null, '0', '1');
INSERT INTO `dict_info` VALUES ('4', 'USER_STATUS', '禁用', null, '1', '2');
INSERT INTO `dict_info` VALUES ('86', 'PS_TYPE', '技术预见', null, '6', '6');
INSERT INTO `dict_info` VALUES ('87', 'PS_TYPE', '培训服务', null, '7', '7');
INSERT INTO `dict_info` VALUES ('46', 'CNT_STATUS', '正常', null, '1', '1');
INSERT INTO `dict_info` VALUES ('47', 'CNT_STATUS', '禁用', null, '2', '2');
INSERT INTO `dict_info` VALUES ('48', 'CL_TYPE', '首页轮播', null, '1', '1');
INSERT INTO `dict_info` VALUES ('49', 'CL_TYPE', '其他轮播', null, '2', '2');
INSERT INTO `dict_info` VALUES ('50', 'PDF_TYPE', '创新科技', null, '1', '1');
INSERT INTO `dict_info` VALUES ('51', 'PDF_TYPE', '河南科技', null, '2', '2');
INSERT INTO `dict_info` VALUES ('52', 'PDF_TYPE', '乡村科技', null, '3', '3');
INSERT INTO `dict_info` VALUES ('58', 'PDF_STATUS', '正常', null, '1', '1');
INSERT INTO `dict_info` VALUES ('59', 'PDF_STATUS', '禁用', null, '2', '2');
INSERT INTO `dict_info` VALUES ('68', 'ANNOUN_STATUS', '正常', null, '1', '1');
INSERT INTO `dict_info` VALUES ('69', 'ANNOUN_STATUS', '禁用', null, '2', '2');
INSERT INTO `dict_info` VALUES ('71', 'INST_TYPE', 'ECCRD致辞', null, '1', '1');
INSERT INTO `dict_info` VALUES ('72', 'INST_TYPE', '名誉主席', null, '2', '2');
INSERT INTO `dict_info` VALUES ('73', 'INST_TYPE', '高级顾问', null, '3', '3');
INSERT INTO `dict_info` VALUES ('74', 'INST_TYPE', '领导构成', null, '4', '4');
INSERT INTO `dict_info` VALUES ('75', 'INST_TYPE', '机构设置', null, '5', '5');
INSERT INTO `dict_info` VALUES ('53', 'PDF_TYPE', '研究报告', null, '4', '4');

-- ----------------------------
-- Table structure for `file_info`
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `FILE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FILE_HASH` varchar(150) DEFAULT NULL COMMENT '文件HASH值',
  `FILE_TYPE` smallint(3) DEFAULT NULL COMMENT '文件类型：1-图片，2-文件',
  `FILE_NAME` varchar(300) DEFAULT NULL COMMENT '文件名',
  `FILE_UUID` varchar(100) DEFAULT NULL COMMENT 'UUID',
  `FILE_PATH` varchar(100) DEFAULT NULL COMMENT '文件路径',
  `FILE_EXTE` varchar(20) DEFAULT NULL COMMENT '文件扩展名',
  `FILE_LENGTH` int(11) unsigned DEFAULT NULL COMMENT '文件长度',
  `FILE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `FILE_LENGTH_STR` varchar(50) DEFAULT NULL COMMENT '长度描述',
  `FILE_STATUS` smallint(3) DEFAULT NULL COMMENT '文件状态',
  PRIMARY KEY (`FILE_ID`),
  KEY `FILE_HASH_INDEX` (`FILE_HASH`),
  KEY `FILE_TYPE_INDEX` (`FILE_TYPE`),
  KEY `FILE_NAME_INDEX` (`FILE_NAME`(255)),
  KEY `FILE_UUID_INDEX` (`FILE_UUID`),
  KEY `FILE_STATUS_INDEX` (`FILE_STATUS`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_info
-- ----------------------------

-- ----------------------------
-- Table structure for `inst_info`
-- ----------------------------
DROP TABLE IF EXISTS `inst_info`;
CREATE TABLE `inst_info` (
  `INST_ID` int(3) NOT NULL AUTO_INCREMENT,
  `INST_TITLE` varchar(200) DEFAULT NULL COMMENT '标题',
  `INST_TYPE` int(1) DEFAULT NULL COMMENT '类型',
  `INST_CONTENT` longtext COMMENT '内容',
  `INST_COUNT` int(11) DEFAULT NULL COMMENT '浏览次数',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `USER_ID` int(3) DEFAULT NULL COMMENT '操作人',
  `INST_TITLE_EN` varchar(200) DEFAULT NULL,
  `INST_CONTENT_EN` longtext,
  PRIMARY KEY (`INST_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inst_info
-- ----------------------------
INSERT INTO `inst_info` VALUES ('1', 'ECCRD致辞', '1', '<p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">欧中发展研究中心(ECCRD)系国际智库组织 。现任主席为希腊老一辈社会活动家阿玛丽亚·马提亚都（Amalia Matiatou）女士。</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><img src=\"/ECCRD/showFile/fd875250eeda4d70977ea1c7d79eec2c.shtml\" title=\"b90e7bec54e736d10fa6eb7a94504fc2d562690e.jpg.jpg\" alt=\"b90e7bec54e736d10fa6eb7a94504fc2d562690e\" style=\"float: right;\"/><span style=\"font-size: 16px;\"></span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">早在1956年代，马提亚都经莫斯科到达北京，毛主席亲切接见了马提亚都一行 。</span></p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">1956年，毛主席亲切接见马提亚都</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">1956年，毛主席亲切接见马提亚都&nbsp;</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">称赞他们是两国人民的友好使者，鼓励他们为两国人民的友好交往做工作。马提亚都是唯一健在的希中友协创会领导人&nbsp; 。 希中友协是希腊乃至整个欧洲最早与中国建立友好的民间组织，为推动希中于1972年正式建交做出了积极贡献&nbsp; &nbsp;。</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">2019年11月10日，中国国家主席习近平对希腊共和国进行国事访问之际，在希腊《每日报》发表的《让古老文明的智慧照鉴未来》的署名文章中称：希腊中国友好协会“60年如一日坚持“为两国人民的友好交往工作到老”&nbsp; 。</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">2019年12月1日，希中友协第一副主席、欧中发展研究中心主席马提亚都与执行主席赵文超以《发挥中希文化优势 共倡东西方文明对话——创立文明对话新平台携手建设人类命运共同体》为题撰文对习主席的称赞作出回应。《中国发展观察》、人民网、环球网、欧联网、希腊《中希时报》等媒体刊发或转载了这一新闻。&nbsp;</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">60多年来，马提亚都秉承这一理念，致力于传播中国文化、增进两国人民民心相通。&nbsp;&nbsp;</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">自ECCRD成立以来，为欧盟与中国、尤其为希腊和中国的公共外交，贸易、投资和文化合作多次搭建平台，并与中国对外友协开展合作，分别在欧盟和中国举办了多届国际研讨会和大型展览活动等。&nbsp;&nbsp;</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">2018年11月，中心正式增补赵文超担任执行主席，这是中国友人第一次正式进入ECCRD担任高管。&nbsp;&nbsp;</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">世界格局正在发生重大变化，欧盟和中国在新的国际格局中必将扮演更重要的角色，新的征程中，ECCRD将携手更多的伙伴为欧中双边关系发展、经济合作和文化交流做出新的贡献。</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">推动的项目介绍</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">中希文化园：2017年5月13日，推动中希文化小镇(下称中希文化园&nbsp; &nbsp;)项目签署。2017年7月，《中国发展观察》、人民网、中国共产党新闻网&nbsp; 、凤凰网、河南卫视多家媒体与主流网站刊发或转载了这一新闻。这是世界上第一个将东西方文明发展历程系统建设的项目。目的是以中希文化为主线，把东西方文明和谐共生的发展历程展示给世人&nbsp; &nbsp;。</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">项目在首届“一带一路”国际合作高峰论坛(BRF)开幕前夕举办的中希地方政府友好对话会上，由希腊经济发展部与中国地方人民政府签订。&nbsp; 这是当日习主席会见来华出席BRF的希腊总理，双方会谈后诞生的又一成果，也是继比雷埃夫斯港合作项目后又一具有时代影响力的项目，填补世界空白。不仅对东西方文明对话，而且对扩展希中、欧中乃至国际经济合作是一个具有广泛支撑意义的新平台。&nbsp;&nbsp;</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">项目的签订是对中希两国领导人讲话的及时回应与落实&nbsp; &nbsp;。希腊时任总理与中国友协李小林会长见签了这一项目。&nbsp; 在中方推进项目的同时，随着希腊新总理米佐塔基斯政府内阁的高效运行，希方加大了对项目的推进工作。&nbsp;&nbsp;</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">文明古国论坛：ECCRD是该项目的主要策划者。策划源起于2015年。2016年，欧中发展研究中心两次拜访中国国务院发展研究中心前主任王梦奎先生，这位中南海的前高级智囊评论“这个策划意义重大”。王梦奎先生对该项目的完善具有贡献。2017年4月，在希中外交部共同倡议下，第一届文明古国论坛会议在雅典召开。</span></p><p style=\"text-indent: 2em; line-height: 3em;\"><span style=\"font-size: 16px;\">作为东西方文明对话的另一的重要平台，如今，世界上最具代表性的古文明国家均加入了该论坛。</span></p><p><br/></p>', '0', '2018-05-05 18:22:40', '2020-04-21 11:28:08', '1', null, null);
INSERT INTO `inst_info` VALUES ('2', '名誉主席', '2', '<p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">主 席：</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">阿玛利亚·马提亚都&nbsp;</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">马提亚都穷其一生在致力于推动希中、欧中友好。早在1956年，她和希腊著名科学家、参众两会议员克兹基斯及克兹基斯的夫人白阿塔发起创立了希中友协。&nbsp; 为促进中希两国的友好合作作出了不可磨灭的贡献。2006年2月12日，中华人民共和国中央人民政府在官网刊发新闻称“为双边关系的发展做出了宝贵的贡献”。</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">2018年3月18日，马提亚都获“中希友谊贡献奖”。时任中国驻希腊大使邹肖力称她“几十年如一日，坚定的与中国人民站在一起，风雨同舟、携手并进，是中国人民真诚的、久经考验的朋友”。&nbsp;</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">60多年来，马提亚都女士坚持在希腊开展汉语教育至今。每逢中国年与希腊政界、文化界代表、中国驻希腊大使等，坚持在雅典组织盛大的庆祝晚会，庆祝中国农历新年，传播中国文化，增进两国人民民心相通。&nbsp;</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">马提亚都一生深爱中国，视中国为第二故乡。2019年10月1日，在代表希中友协、欧中发展研究中心致新中国成立70周年华诞的贺信中&nbsp; ，她称赞“70年风雨兼程，伟大中国为世界增添无限绚丽光彩”。&nbsp; 2019年10月5日《人民日报》又以《伟大中国为世界增添无限绚丽光彩》刊发了贺信内容 。人民网、人民日报海外网、光明网、</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">《人民日报》要闻版刊载贺信部分内容的截图</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">《人民日报》要闻版刊载贺信部分内容的截图</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">中国新闻网等数十家主流媒体对此有报道。&nbsp;</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">为迎接习近平主席对希腊的国事访问，2019年11月10日， ECCRD特稿《希腊中国共倡文明对话对共建欧中开放经济具有重要意义》、《希腊人民热切期待习近平主席早日访问希腊成为现实》分别在希腊《中希时报》&nbsp; &nbsp;、欧洲联合通讯社官网欧联网刊发&nbsp; &nbsp;。</span></p><p style=\"line-height: 3em; text-indent: 2em;\"><span style=\"font-size: 16px;\">2019年11月12日，人民日报海外网以《希腊媒体聚焦习近平到访 “新”成了高频词》为题刊载了希腊前总统顾问与欧中发展研究中心研究员、执行主席赵文超对习主席访希的赞誉和评论。&nbsp;</span></p><p><br/></p>', '0', '2018-05-05 18:23:11', '2020-04-21 11:25:30', '1', null, null);
INSERT INTO `inst_info` VALUES ('3', '高级顾问', '3', '<p style=\"text-align:center\"><img src=\"/softpt/showFile/93989c9a8fa242acbbd17ec64641c5bf.shtml\" title=\"河南省软科学研究会组织架构.png.png\" alt=\"河南省软科学研究会组织架构\"/></p>', '0', '2018-05-05 18:24:32', '2019-01-18 15:18:43', '1', null, null);
INSERT INTO `inst_info` VALUES ('4', '领导构成', '4', '<p>领导构成:</p><p><br/></p><p>&nbsp;&nbsp;&nbsp;&nbsp;历任主席:<br/></p><p>&nbsp;&nbsp;&nbsp;&nbsp;季奥尼西亚斯·亚特拉斯 Dionysius Atalos</p><p>&nbsp;&nbsp;&nbsp;&nbsp;<br/></p>', '0', '2019-01-18 15:20:41', '2020-04-21 11:15:25', '1', null, null);
INSERT INTO `inst_info` VALUES ('5', '机构设置', '5', '<p><span style=\"font-size:19px;font-family:宋体\">会员代表大会</span></p><p><span style=\"font-size:19px;font-family:宋体\">理事会</span></p><p><span style=\"font-size:19px;font-family:宋体\">办公厅</span></p><p><span style=\"font-size:19px;font-family:宋体\">秘书处</span></p><p><span style=\"font-size:19px;font-family:宋体\">外交关系研究部</span></p><p><span style=\"font-size:19px;font-family:宋体\">文化交流部</span></p><p><span style=\"font-size:19px;font-family:宋体\">经济促进部</span></p><p><span style=\"font-size:19px;font-family:宋体\">欧中发展观察通讯社（筹）</span></p><p><span style=\"font-size:19px;font-family:宋体\">财务部</span></p><p><br/></p>', '0', '2019-01-18 15:21:05', '2019-01-29 11:15:51', '1', null, null);

-- ----------------------------
-- Table structure for `link_info`
-- ----------------------------
DROP TABLE IF EXISTS `link_info`;
CREATE TABLE `link_info` (
  `LINK_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LINK_TITLE` varchar(200) DEFAULT NULL COMMENT '标题',
  `LINK_URL` varchar(255) DEFAULT NULL COMMENT '地址',
  `LINK_TITLE_EN` varchar(500) DEFAULT NULL COMMENT '英文标题',
  PRIMARY KEY (`LINK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of link_info
-- ----------------------------

-- ----------------------------
-- Table structure for `mt_info`
-- ----------------------------
DROP TABLE IF EXISTS `mt_info`;
CREATE TABLE `mt_info` (
  `MT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MT_NAME` varchar(200) DEFAULT NULL COMMENT '模块名称',
  `MT_PARENT` int(11) DEFAULT NULL COMMENT '父级',
  `MT_NTOTE` varchar(500) DEFAULT NULL COMMENT '备注',
  `MT_NAME_EN` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`MT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mt_info
-- ----------------------------
INSERT INTO `mt_info` VALUES ('1', '新闻动态', '0', null, 'News');
INSERT INTO `mt_info` VALUES ('2', '欧中外交', '0', null, 'Diplomatic Research');
INSERT INTO `mt_info` VALUES ('3', '人文交流', '0', null, 'Cultural＆Economicmore');
INSERT INTO `mt_info` VALUES ('4', '经济合作', '0', null, null);
INSERT INTO `mt_info` VALUES ('5', 'OUTLOOK', '0', null, 'OUTLOOK');
INSERT INTO `mt_info` VALUES ('6', '过往成就', '0', null, 'Past Achievements');
INSERT INTO `mt_info` VALUES ('7', '玛蒂亚杜专栏', '0', null, 'Amalia Column');
INSERT INTO `mt_info` VALUES ('8', '大师专栏', '0', null, 'Special Column');
INSERT INTO `mt_info` VALUES ('9', '文明探源', '0', null, 'Grasp of civilization');

-- ----------------------------
-- Table structure for `pdf_info`
-- ----------------------------
DROP TABLE IF EXISTS `pdf_info`;
CREATE TABLE `pdf_info` (
  `PDF_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PDF_TITLE` varchar(100) DEFAULT NULL COMMENT '标题',
  `PDF_URL` varchar(200) DEFAULT NULL COMMENT 'URL',
  `FILE_ID` int(11) DEFAULT NULL COMMENT '文件ID',
  `PDF_TYPE` smallint(3) DEFAULT NULL COMMENT '类型',
  `PDF_STATUS` smallint(3) DEFAULT NULL COMMENT '状态',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `USER_ID` int(11) DEFAULT NULL COMMENT '操作人',
  `PDF_PATH` varchar(100) DEFAULT NULL COMMENT 'PDF略缩图路径',
  PRIMARY KEY (`PDF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pdf_info
-- ----------------------------

-- ----------------------------
-- Table structure for `permission_info`
-- ----------------------------
DROP TABLE IF EXISTS `permission_info`;
CREATE TABLE `permission_info` (
  `PER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PER_URL` varchar(200) DEFAULT NULL COMMENT '权限url',
  `PER_TITLE` varchar(200) DEFAULT NULL COMMENT '权限标题',
  `PER_NAME` varchar(200) DEFAULT NULL COMMENT '权限名称',
  `PER_STATUS` smallint(3) DEFAULT NULL COMMENT '权限状态',
  `PER_NOTE` varchar(1000) DEFAULT NULL COMMENT '备注',
  `PER_PARENT` int(11) DEFAULT NULL COMMENT '父级id',
  `PER_PARAMS` varchar(1000) DEFAULT NULL COMMENT '参数',
  `PER_SORT` smallint(3) DEFAULT NULL COMMENT '排序',
  `PER_ICON` varchar(50) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`PER_ID`),
  KEY `PER_STATUS_INDEX` (`PER_STATUS`),
  KEY `PER_PARENT_INDEX` (`PER_PARENT`),
  KEY `PER_NAME_INDEX` (`PER_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission_info
-- ----------------------------
INSERT INTO `permission_info` VALUES ('1', null, '系统管理', 'hc.sys', '0', '系统管理模块', '0', null, '1', 'fa-cogs');
INSERT INTO `permission_info` VALUES ('2', '/user/index.do', '用户管理', 'hc.sys.user', '0', '用户管理模块', '1', null, '1', 'fa-user');
INSERT INTO `permission_info` VALUES ('3', '/user/list.do', '用户列表', 'hc.sys.user.list', '0', '获取用户信息列表', '2', null, '1', null);
INSERT INTO `permission_info` VALUES ('4', '/user/status.do', '用户状态', 'hc.sys.user.status', '0', '修改用户状态', '2', null, '2', null);
INSERT INTO `permission_info` VALUES ('5', '/user/add.do', '添加用户', 'hc.sys.user.add', '0', '添加新用户', '2', null, '3', null);
INSERT INTO `permission_info` VALUES ('6', '/user/edit.do', '编辑用户', 'hc.sys.user.edit', '0', '编辑用户信息', '2', null, '4', null);
INSERT INTO `permission_info` VALUES ('7', '/role/index.do', '角色管理', 'hc.sys.role', '0', '角色管理模块', '1', null, '2', 'fa-user-o ');
INSERT INTO `permission_info` VALUES ('8', '/role/add.do', '添加角色', 'hc.sys.role.add', '0', '添加角色信息', '7', null, '2', null);
INSERT INTO `permission_info` VALUES ('9', '/role/edit.do', '编辑角色', 'hc.sys.role.edit', '0', '编辑角色信息', '7', null, '3', null);
INSERT INTO `permission_info` VALUES ('10', '/role/per.do', '查看权限', 'hc.sys.role.per', '0', '查看当前角色的权限信息', '7', null, '4', null);
INSERT INTO `permission_info` VALUES ('11', '/role/perEdit.do', '编辑权限', 'hc.sys.role.perEdit', '0', '编辑当前角色的权限信息', '7', null, '5', null);
INSERT INTO `permission_info` VALUES ('12', '/role/list.do', '角色列表', 'hc.sys.role.list', '0', '获取角色的列表信息', '7', null, '1', null);
INSERT INTO `permission_info` VALUES ('13', '/session/index.do', 'Session管理', 'hc.sys.session', '0', '管理所有的已登录的用户信息', '1', null, '3', ' fa-podcast');
INSERT INTO `permission_info` VALUES ('14', '/session/list.do', '用户列表', 'hc.sys.session.list', '0', '在线的用户列表', '13', null, '1', null);
INSERT INTO `permission_info` VALUES ('15', '/session/logout.do', '强制退出', 'hc.sys.session.logout', '0', '强制用户下线', '13', null, '2', null);
INSERT INTO `permission_info` VALUES ('17', null, '文章管理', 'hc.cnt', '0', '文章信息管理模块', '0', null, '2', 'fa-indent');
INSERT INTO `permission_info` VALUES ('18', '/cntMgr/index.do', '文本管理', 'hc.cnt.cntMgr', '0', '文本信息管理', '17', null, '1', 'fa-server');
INSERT INTO `permission_info` VALUES ('19', '/cntMgr/list.do', '文本列表', 'hc.cnt.cntMgr.list', '0', '文本列表', '18', null, '1', null);
INSERT INTO `permission_info` VALUES ('20', '/cntMgr/add.do', '添加文本', 'hc.cnt.cntMgr.add', '0', '添加文本', '18', null, '2', null);
INSERT INTO `permission_info` VALUES ('21', '/cntMgr/edit.do', '编辑文本', 'hc.cnt.cntMgr.edit', '0', '编辑文本', '18', null, '3', null);
INSERT INTO `permission_info` VALUES ('22', '/cntMgr/del.do', '删除文本', 'hc.cnt.cntMgr.del', '0', '删除文本', '18', null, '4', null);
INSERT INTO `permission_info` VALUES ('23', '/cntMgr/status.do', '文本状态', 'hc.cnt.cntMgr.status', '0', '文本状态', '18', null, '5', null);
INSERT INTO `permission_info` VALUES ('24', '/clMgr/index.do', '图片管理', 'hc.cnt.clMgr', '0', '轮播等图片模块管理', '17', null, '2', 'fa-file-image-o');
INSERT INTO `permission_info` VALUES ('25', '/clMgr/list.do', '图片列表', 'hc.cnt.clMgr.list', '0', '图片列表', '24', null, '1', null);
INSERT INTO `permission_info` VALUES ('26', '/clMgr/add.do', '添加图片', 'hc.cnt.clMgr.add', '0', '批量添加图片', '24', null, '2', null);
INSERT INTO `permission_info` VALUES ('27', '/clMgr/edit.do', '编辑图片', 'hc.cnt.clMgr.edit', '0', '单个编辑图片', '24', null, '3', null);
INSERT INTO `permission_info` VALUES ('28', '/clMgr/del', '删除图片', 'hc.cnt.clMgr/del', '0', '删除图片', '24', null, '4', null);
INSERT INTO `permission_info` VALUES ('29', '/mtUtil/index.do', '栏目管理', 'hc.sys.mtUtil', '0', '栏目管理模块', '1', null, '1', 'fa-columns');
INSERT INTO `permission_info` VALUES ('30', '/mtUtil/list.do', '栏目列表', 'hc.sys.mtUtil.list', '0', '获取栏目信息列表', '29', null, '1', null);
INSERT INTO `permission_info` VALUES ('31', '/mtUtil/add.do', '添加栏目', 'hc.sys.mtUtil.add', '0', '添加新栏目', '29', null, '2', null);
INSERT INTO `permission_info` VALUES ('32', '/mtUtil/edit.do', '编辑栏目', 'hc.sys.mtUtil.edit', '0', '编辑栏目信息', '29', null, '3', null);
INSERT INTO `permission_info` VALUES ('33', '/mtUtil/del.do', '删除栏目', 'hc.sys.mtUtil.del', '0', '删除栏目', '29', null, '4', null);
INSERT INTO `permission_info` VALUES ('34', '/user/rePws.do', '修改密码', 'hc.sys.user.rePws', '0', '修改用户密码', '2', null, '5', null);
INSERT INTO `permission_info` VALUES ('35', '/pdfMgr/index.do', 'PDF管理', 'hc.cnt.pdfMgr', '0', 'PDF模块管理', '17', null, '1', 'fa-book');
INSERT INTO `permission_info` VALUES ('36', '/pdfMgr/list.do', 'PDF列表', 'hc.cnt.pdfMgr.list', '0', 'PDF列表', '35', null, '1', null);
INSERT INTO `permission_info` VALUES ('37', '/pdfMgr/add.do', '添加PDF', 'hc.cnt.pdfMgr.add', '0', '添加PDF信息', '35', null, '2', null);
INSERT INTO `permission_info` VALUES ('38', '/pdfMgr/edit.do', '编辑PDF', 'hc.cnt.pdfMgr.edit', '0', '编辑PDF信息', '35', null, '3', null);
INSERT INTO `permission_info` VALUES ('39', '/pdfMgr/del.do', '删除PDF', 'hc.cnt.pdfMgr.del', '0', '删除PDF信息', '35', null, '4', null);
INSERT INTO `permission_info` VALUES ('46', '/link/index.do', '友情链接', 'hc.sys.link', '0', '友情链接管理模块', '1', null, '1', 'fa-link');
INSERT INTO `permission_info` VALUES ('47', '/link/list.do', '链接列表', 'hc.sys.link.list', '0', '友情链接列表', '46', null, '1', null);
INSERT INTO `permission_info` VALUES ('48', '/link/add.do', '添加链接', 'hc.sys.link.add', '0', '添加友情链接信息', '46', null, '2', null);
INSERT INTO `permission_info` VALUES ('49', '/link/edit.do', '编辑链接', 'hc.sys.link.edit', '0', '编辑友情链接信息', '46', null, '3', null);
INSERT INTO `permission_info` VALUES ('50', '/link/del.do', '删除链接', 'hc.sys.linl.del', '0', '删除友情链接信息', '46', null, '4', null);
INSERT INTO `permission_info` VALUES ('51', '/instMgr/index.do', '机构概况', 'hc.cnt.instMgr', '0', '机构概况管理', '17', null, '1', 'fa-institution');
INSERT INTO `permission_info` VALUES ('52', '/instMgr/list.do', '简介列表', 'hc.cnt.instMgr.list', '0', '机构简介列表', '51', null, '1', null);
INSERT INTO `permission_info` VALUES ('53', '/instMgr/add.do', '添加简介', 'hc.cnt.instMgr.add', '0', '添加机构概况', '51', null, '2', null);
INSERT INTO `permission_info` VALUES ('54', '/instMgr/edit.do', '编辑简介', 'hc.cnt.instMgr.edit', '0', '编辑简介信息', '51', null, '3', null);
INSERT INTO `permission_info` VALUES ('55', '/videoMgr/index.do', '视频管理', 'hc.cnt.videoMgr', '0', '视频模块管理', '17', null, '1', 'fa-video-camera');
INSERT INTO `permission_info` VALUES ('56', '/videoMgr/add.do', '添加视频', 'hc.cnt.videoMgr.add', '0', '添加视频信息', '55', null, '1', null);
INSERT INTO `permission_info` VALUES ('57', '/videoMgr/edit.do', '编辑视频', 'hc.cnt.videoMgr.edit', '0', '编辑视频信息', '55', null, '2', null);
INSERT INTO `permission_info` VALUES ('58', '/videoMgr/del.do', '删除视频', 'hc.cnt.videoMgr.del', '0', '删除视频信息', '55', null, '3', null);

-- ----------------------------
-- Table structure for `role_info`
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `ROLE_NOTE` varchar(1000) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_info
-- ----------------------------
INSERT INTO `role_info` VALUES ('1', 'admin', '超级管理员');
INSERT INTO `role_info` VALUES ('2', '管理员', '系统管理员');
INSERT INTO `role_info` VALUES ('3', '编辑部', '编辑部使用');

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `ROLE_ID` int(11) NOT NULL,
  `PER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`PER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('2', '1');
INSERT INTO `role_permission` VALUES ('2', '2');
INSERT INTO `role_permission` VALUES ('2', '3');
INSERT INTO `role_permission` VALUES ('2', '4');
INSERT INTO `role_permission` VALUES ('2', '5');
INSERT INTO `role_permission` VALUES ('2', '6');
INSERT INTO `role_permission` VALUES ('2', '7');
INSERT INTO `role_permission` VALUES ('2', '8');
INSERT INTO `role_permission` VALUES ('2', '9');
INSERT INTO `role_permission` VALUES ('2', '10');
INSERT INTO `role_permission` VALUES ('2', '11');
INSERT INTO `role_permission` VALUES ('2', '12');
INSERT INTO `role_permission` VALUES ('2', '13');
INSERT INTO `role_permission` VALUES ('2', '14');
INSERT INTO `role_permission` VALUES ('2', '15');
INSERT INTO `role_permission` VALUES ('2', '17');
INSERT INTO `role_permission` VALUES ('2', '18');
INSERT INTO `role_permission` VALUES ('2', '19');
INSERT INTO `role_permission` VALUES ('2', '20');
INSERT INTO `role_permission` VALUES ('2', '21');
INSERT INTO `role_permission` VALUES ('2', '22');
INSERT INTO `role_permission` VALUES ('2', '23');
INSERT INTO `role_permission` VALUES ('2', '24');
INSERT INTO `role_permission` VALUES ('2', '25');
INSERT INTO `role_permission` VALUES ('2', '26');
INSERT INTO `role_permission` VALUES ('2', '27');
INSERT INTO `role_permission` VALUES ('2', '28');
INSERT INTO `role_permission` VALUES ('2', '29');
INSERT INTO `role_permission` VALUES ('2', '30');
INSERT INTO `role_permission` VALUES ('2', '31');
INSERT INTO `role_permission` VALUES ('2', '32');
INSERT INTO `role_permission` VALUES ('2', '33');
INSERT INTO `role_permission` VALUES ('2', '34');
INSERT INTO `role_permission` VALUES ('2', '35');
INSERT INTO `role_permission` VALUES ('2', '36');
INSERT INTO `role_permission` VALUES ('2', '37');
INSERT INTO `role_permission` VALUES ('2', '38');
INSERT INTO `role_permission` VALUES ('2', '39');
INSERT INTO `role_permission` VALUES ('2', '40');
INSERT INTO `role_permission` VALUES ('2', '41');
INSERT INTO `role_permission` VALUES ('2', '42');
INSERT INTO `role_permission` VALUES ('2', '43');
INSERT INTO `role_permission` VALUES ('2', '44');
INSERT INTO `role_permission` VALUES ('2', '45');
INSERT INTO `role_permission` VALUES ('2', '46');
INSERT INTO `role_permission` VALUES ('2', '47');
INSERT INTO `role_permission` VALUES ('2', '48');
INSERT INTO `role_permission` VALUES ('2', '49');
INSERT INTO `role_permission` VALUES ('2', '50');
INSERT INTO `role_permission` VALUES ('2', '51');
INSERT INTO `role_permission` VALUES ('2', '52');
INSERT INTO `role_permission` VALUES ('2', '53');
INSERT INTO `role_permission` VALUES ('2', '54');
INSERT INTO `role_permission` VALUES ('2', '56');
INSERT INTO `role_permission` VALUES ('2', '57');
INSERT INTO `role_permission` VALUES ('2', '58');
INSERT INTO `role_permission` VALUES ('2', '59');
INSERT INTO `role_permission` VALUES ('2', '60');
INSERT INTO `role_permission` VALUES ('2', '61');
INSERT INTO `role_permission` VALUES ('2', '62');
INSERT INTO `role_permission` VALUES ('2', '63');
INSERT INTO `role_permission` VALUES ('2', '64');
INSERT INTO `role_permission` VALUES ('2', '65');
INSERT INTO `role_permission` VALUES ('2', '66');
INSERT INTO `role_permission` VALUES ('2', '67');
INSERT INTO `role_permission` VALUES ('2', '68');
INSERT INTO `role_permission` VALUES ('2', '69');
INSERT INTO `role_permission` VALUES ('2', '70');
INSERT INTO `role_permission` VALUES ('2', '71');
INSERT INTO `role_permission` VALUES ('2', '72');
INSERT INTO `role_permission` VALUES ('2', '73');
INSERT INTO `role_permission` VALUES ('2', '74');
INSERT INTO `role_permission` VALUES ('2', '75');
INSERT INTO `role_permission` VALUES ('2', '76');
INSERT INTO `role_permission` VALUES ('2', '77');
INSERT INTO `role_permission` VALUES ('2', '78');
INSERT INTO `role_permission` VALUES ('2', '79');
INSERT INTO `role_permission` VALUES ('2', '80');
INSERT INTO `role_permission` VALUES ('3', '17');
INSERT INTO `role_permission` VALUES ('3', '18');
INSERT INTO `role_permission` VALUES ('3', '19');
INSERT INTO `role_permission` VALUES ('3', '20');
INSERT INTO `role_permission` VALUES ('3', '21');
INSERT INTO `role_permission` VALUES ('3', '22');
INSERT INTO `role_permission` VALUES ('3', '23');
INSERT INTO `role_permission` VALUES ('3', '35');
INSERT INTO `role_permission` VALUES ('3', '36');
INSERT INTO `role_permission` VALUES ('3', '37');
INSERT INTO `role_permission` VALUES ('3', '38');
INSERT INTO `role_permission` VALUES ('3', '39');

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_NAME` varchar(100) DEFAULT NULL COMMENT '用户名',
  `USER_NAME_EN` varchar(200) DEFAULT NULL,
  `USER_PASS` varchar(200) DEFAULT NULL COMMENT '密码',
  `USER_STATUS` smallint(3) DEFAULT NULL COMMENT '状态：0正常，1停用',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最后登录时间',
  `USER_SALT` varchar(100) DEFAULT NULL COMMENT '盐',
  `USER_NOTE` varchar(1000) DEFAULT NULL COMMENT '备注信息',
  `CERT_SN` varchar(100) DEFAULT NULL COMMENT '签名证书SN',
  `USER_CARD_ID` varchar(18) DEFAULT NULL COMMENT '身份证',
  PRIMARY KEY (`USER_ID`),
  KEY `USER_NAME_INDEX` (`USER_NAME`),
  KEY `USER_STATUS_INDEX` (`USER_STATUS`),
  KEY `USER_SALT_INDEX` (`USER_SALT`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'admin', null, '21232F297A57A5A743894A0E4A801FC3', '0', '2017-12-13 10:19:04', '2020-04-21 11:18:54', null, null, '74aead1cd3b7843a', null);

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  KEY `USER_ID_INDEX` (`USER_ID`),
  KEY `ROLE_ID_INDEX` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '2');
INSERT INTO `user_role` VALUES ('4', '2');
INSERT INTO `user_role` VALUES ('3', '2');
INSERT INTO `user_role` VALUES ('5', '2');
INSERT INTO `user_role` VALUES ('6', '3');

-- ----------------------------
-- Table structure for `video_info`
-- ----------------------------
DROP TABLE IF EXISTS `video_info`;
CREATE TABLE `video_info` (
  `VIDEO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `VIDEO_TITLE` varchar(200) DEFAULT NULL,
  `FILE_ID` int(11) DEFAULT NULL,
  `VIDEO_TYPE` int(11) DEFAULT NULL,
  `VIDEO_IMAGE` int(11) DEFAULT NULL,
  `VIDEO_STATUS` int(11) DEFAULT NULL,
  `INSERT_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  `VIDEO_TITLE_EN` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`VIDEO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video_info
-- ----------------------------

-- ----------------------------
-- Function structure for `F_DICT_NAME`
-- ----------------------------
DROP FUNCTION IF EXISTS `F_DICT_NAME`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `F_DICT_NAME`(`P_COLUMN` varchar(50),`P_VALUE` smallint) RETURNS varchar(100) CHARSET utf8
BEGIN
	-- 定义变量
	DECLARE VAL varchar(100) CHARACTER SET utf8;
	-- 设置默认值
	SET VAL = '';
	-- 赋值
	SELECT DICT_NAME INTO VAL FROM DICT_INFO WHERE DICT_COLUMN = P_COLUMN AND DICT_VALUE = P_VALUE;
	-- 返回结果
	RETURN VAL;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `F_FILE_PATH`
-- ----------------------------
DROP FUNCTION IF EXISTS `F_FILE_PATH`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `F_FILE_PATH`(`P_ID` smallint) RETURNS varchar(200) CHARSET utf8
BEGIN
	-- 定义变量
	DECLARE VAL varchar(200) CHARACTER SET utf8;
	-- 设置默认值
	SET VAL = '';
	-- 赋值
	SELECT FILE_PATH INTO VAL FROM FILE_INFO WHERE FILE_STATUS = 1 AND FILE_ID = P_ID;
	-- 返回结果
	RETURN VAL;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `F_FILE_URL`
-- ----------------------------
DROP FUNCTION IF EXISTS `F_FILE_URL`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `F_FILE_URL`(`P_ID` smallint) RETURNS varchar(200) CHARSET utf8
BEGIN
	-- 定义变量
	DECLARE VAL varchar(200) CHARACTER SET utf8;
	-- 设置默认值
	SET VAL = '';
	-- 赋值
	SELECT CONCAT('/showFile/', FILE_UUID, '.shtml') INTO VAL FROM FILE_INFO WHERE FILE_STATUS = 1 AND FILE_ID = P_ID;
	-- 返回结果
	RETURN VAL;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `F_MT_NAME`
-- ----------------------------
DROP FUNCTION IF EXISTS `F_MT_NAME`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `F_MT_NAME`(`P_USER` int) RETURNS varchar(500) CHARSET utf8
BEGIN
	#
	DECLARE VAL VARCHAR(500);
	# 通过用户ID获取用户名
	SELECT MT_NAME INTO VAL FROM MT_INFO WHERE MT_ID = P_USER;
	RETURN VAL;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `F_ROLE_ID`
-- ----------------------------
DROP FUNCTION IF EXISTS `F_ROLE_ID`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `F_ROLE_ID`(`P_USER` int) RETURNS varchar(1000) CHARSET utf8
BEGIN
	DECLARE VAL INT;
	SELECT ROLE_ID INTO VAL FROM USER_ROLE WHERE USER_ID = P_USER LIMIT 0, 1;
	RETURN VAL;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `F_ROLE_NAME`
-- ----------------------------
DROP FUNCTION IF EXISTS `F_ROLE_NAME`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `F_ROLE_NAME`(`P_USER` int) RETURNS varchar(1000) CHARSET utf8
BEGIN
	DECLARE VAL varchar(1000) CHARACTER SET utf8;
	SELECT ROLE_NAME INTO VAL FROM ROLE_INFO WHERE ROLE_ID = (SELECT ROLE_ID FROM USER_ROLE WHERE USER_ID = P_USER LIMIT 0, 1);
	RETURN VAL;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `F_USER_ID`
-- ----------------------------
DROP FUNCTION IF EXISTS `F_USER_ID`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `F_USER_ID`(`P_USER` varchar(200)) RETURNS int(11)
BEGIN
	#
	DECLARE VAL INT(11);
	# 通过用户名获取用户ID
	SELECT USER_ID INTO VAL FROM USER_INFO WHERE USER_NAME = P_USER;
	RETURN VAL;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `F_USER_NAME`
-- ----------------------------
DROP FUNCTION IF EXISTS `F_USER_NAME`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `F_USER_NAME`(`P_USER` int) RETURNS varchar(500) CHARSET utf8
BEGIN
	#
	DECLARE VAL VARCHAR(500);
	# 通过用户ID获取用户名
	SELECT USER_NAME INTO VAL FROM USER_INFO WHERE USER_ID = P_USER;
	RETURN VAL;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `F_USER_NAME_EN`
-- ----------------------------
DROP FUNCTION IF EXISTS `F_USER_NAME_EN`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `F_USER_NAME_EN`(`P_USER` int) RETURNS varchar(500) CHARSET utf8
BEGIN
	#
	DECLARE VAL VARCHAR(500);
	# 通过用户ID获取用户名
	SELECT USER_NAME_EN INTO VAL FROM USER_INFO WHERE USER_ID = P_USER;
	RETURN VAL;
END
;;
DELIMITER ;
