package com.ziroom.zyl.repository;

import com.ziroom.zyl.entity.ObjectiveSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName：ObjectiveSnapshotRe
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/2 11:37
 **/
@Repository
public interface ObjectiveSnapshotRepository extends JpaRepository<ObjectiveSnapshot, Long> {

    @Query(value = "select distinct(o.create_id) from objective_snapshot as o where cycle_id = ?1", nativeQuery = true)
    @Modifying
    List<String> findAllCreateId(Long cycleId);

    @Query(value = "select o.* from objective_snapshot as o where create_id = ?1", nativeQuery = true)
    @Modifying
    List<ObjectiveSnapshot> findByCreateId(String createId);



}
