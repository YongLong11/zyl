package com.ziroom.zyl.quartz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobInfo {
    private String jobName;
    private String jobGroupName;
    private String description;
    private String jobStatus;
    private String jobTime;
}

