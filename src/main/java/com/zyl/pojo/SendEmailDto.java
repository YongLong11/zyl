package com.zyl.pojo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data(staticConstructor = "of")
@Accessors(chain = true)
public class SendEmailDto {

    private String from;
    private List<String> tos;
    private List<String> ccs;
    private String title;
    private List<String> sendGroup;
    private List<String> ccGroup;
    private List<String> columns;
    private List<List<String>> cells;

}
