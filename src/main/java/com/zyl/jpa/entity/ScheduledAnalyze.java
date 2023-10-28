package com.zyl.jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "scheduled_analyze")
@Entity
@Data
public class ScheduledAnalyze {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String cron;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_status")

    private String jobStatus;

    @Column(name = "create_user")

    private String createUser;
}
