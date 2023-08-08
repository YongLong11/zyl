package com.ziroom.zyl.jpa.repository;

import com.ziroom.zyl.datasource.DataSourceConstant;
import com.ziroom.zyl.datasource.TargetDataSource;
import com.ziroom.zyl.jpa.entity.ScheduledAnalyze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
@TargetDataSource(DataSourceConstant.ZYL)
public interface ScheduledAnalyzeRepository extends JpaRepository<ScheduledAnalyze, Long>, JpaSpecificationExecutor<ScheduledAnalyze> {
}
