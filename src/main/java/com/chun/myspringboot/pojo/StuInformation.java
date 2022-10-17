package com.chun.myspringboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StuInformation {

    private String username;
    private String roles;
 private  Integer id;

    private Date stutime;
}
