package com.geotmt.cacheprime.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sys_user")
@Data
public class SysUserDO {

    /**
     * id          int(11)       (NULL)     NO      PRI     (NULL)   auto_increment  select,insert,update,references  主键id
     * avatar      varchar(255)  utf8_bin   YES             (NULL)                   select,insert,update,references  头像
     * account     varchar(45)   utf8_bin   YES             (NULL)                   select,insert,update,references  账号
     * password    varchar(45)   utf8_bin   YES             (NULL)                   select,insert,update,references  密码
     * salt        varchar(45)   utf8_bin   YES             (NULL)                   select,insert,update,references  md5密码盐
     * name        varchar(45)   utf8_bin   YES             (NULL)                   select,insert,update,references  名字
     * birthday    datetime      (NULL)     YES             (NULL)                   select,insert,update,references  生日
     * sex         int(11)       (NULL)     YES             (NULL)                   select,insert,update,references  性别（1：男 2：女）
     * email       varchar(45)   utf8_bin   YES             (NULL)                   select,insert,update,references  电子邮件
     * phone       varchar(45)   utf8_bin   YES             (NULL)                   select,insert,update,references  电话
     * roleid      varchar(255)  utf8_bin   YES             (NULL)                   select,insert,update,references  角色id
     * deptid      int(11)       (NULL)     YES             (NULL)                   select,insert,update,references  部门id
     * status      int(11)       (NULL)     YES             (NULL)                   select,insert,update,references  状态(1：启用  2：冻结  3：删除）
     * createtime  datetime      (NULL)     YES             (NULL)                   select,insert,update,references  创建时间
     * version     int(11)       (NULL)     YES             (NULL)                   select,insert,update,references  保留字段
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * md5密码盐
     */
    private String salt;

    /**
     * 名字
     */
    private String name;


    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;


    /**
     * 角色id
     */
    @Column(name = "roleid")
    private String roleId;

    /**
     * 部门id
     */
    @Column(name = "deptid")
    private Integer deptId;

    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    private Integer status;


    @Column(name = "createtime")
    private Date createTime;


    @Column(name = "updatetime")
    private Date updateTime;

}
