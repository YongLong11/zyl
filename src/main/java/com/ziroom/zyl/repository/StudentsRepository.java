package com.ziroom.zyl.repository;

import com.ziroom.zyl.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Long>, JpaSpecificationExecutor<Students> {

    @Query(value = "select a from Students a where id in (?1)")
    @Modifying
    List<Students> queryById(List<Long> ids);


    List<Students> findAllByName(String name);

    List<Students> findAllByNameContains(String string);

}
