package com.ziroom.zyl.jpa.repository;

import com.ziroom.zyl.jpa.entity.ScheduledAnalyze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface ScheduledAnalyzeRepository extends JpaRepository<ScheduledAnalyze, Long>, JpaSpecificationExecutor<ScheduledAnalyze> {
}
