package com.ziroom.zyl.repository;

import com.ziroom.zyl.entity.OkrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OkrUserRepsoitory extends JpaRepository<OkrUser, Long> {

}
