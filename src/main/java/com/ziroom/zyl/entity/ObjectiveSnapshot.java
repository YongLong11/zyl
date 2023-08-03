package com.ziroom.zyl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName：ObjectiveSnapshot
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/2 11:35
 **/
@Data
@Entity
@Table(name = "objective_snapshot")
public class ObjectiveSnapshot {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "version")
    private String version;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "create_id")
    private String createId;

    @Column(name = "cycle_id")
    private Long cycleId;

    @Column(name = "uniqueKey")
    private String uniqueKey;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "snapshot_json")
    private String snapshotJson;
}
