package com.chun.myspringboot.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StuResult {
    private Integer id;
    private Integer taotiid;
    private String username;
    private Integer radioscores;
    private String listenName;
    private Timestamp createtime;
    private String content;



}
