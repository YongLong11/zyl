package com.ziroom.zyl.jpa.repository;

import com.ziroom.zyl.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
