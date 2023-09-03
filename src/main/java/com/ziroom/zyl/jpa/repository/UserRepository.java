package com.ziroom.zyl.jpa.repository;

import com.ziroom.zyl.datasource.DatasourceEnum;
import com.ziroom.zyl.datasource.TargetDataSource;
import com.ziroom.zyl.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
@TargetDataSource(DatasourceEnum.ZYL)
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
