package com.chun.myspringboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Questions {
    private Integer Id;
    private String subject;

    private Integer taotiid;
    private String listenName;
    private String optiona;

    private String optionb;
    private String optionc;
    private String optiond;
    private String answer;


}