package com.ziroom.zyl.quartz;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum JobStatus {
    NORMAL("NORMAL", "正常"),
    PAUSE("PAUSE", "暂停");
    private String status;
    private String desc;

    private static final Map<String, JobStatus> MAP = Arrays.asList(JobStatus.values()).stream()
            .collect(Collectors.toMap(JobStatus::getStatus, Function.identity(), (k1, k2) -> k1));




}
