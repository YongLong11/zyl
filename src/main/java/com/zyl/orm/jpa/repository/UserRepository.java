package com.zyl.orm.jpa.repository;

import com.zyl.something.datasource.DatasourceEnum;
import com.zyl.something.datasource.TargetDataSource;
import com.zyl.orm.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
@TargetDataSource(DatasourceEnum.ZYL)
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
