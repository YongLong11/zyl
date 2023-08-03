package com.ziroom.zyl.service;

import com.google.common.collect.Lists;
import com.ziroom.zyl.entity.User;
import com.ziroom.zyl.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @ClassName：UserService
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/4/25 11:27
 **/
@Service
@Slf4j
public class UserService {

    @Resource
    private UserRepository userRepository;

//    @Cacheable(value = "redisCacheManager")
    public Page<User> getUserByPage(User user, Integer pageNum, Integer pageSize ){
        Specification<User> specification = (Specification<User>) (r, q, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if(StringUtils.isNotBlank(user.getUserName())){
                predicates.add(cb.equal(r.get("userName"), user.getUserName()));
            }
            if(StringUtils.isNotBlank(user.getAge())){
                predicates.add(cb.equal(r.get("age"), user.getAge()));
            }
            return cb.and(predicates.stream().toArray(Predicate[]::new));
        };
        final Page<User> all = userRepository.findAll(specification, PageRequest.of(pageNum, pageSize));
        return all;
    }

    public List<User> getUserAll(User user){
        Specification<User> specification = (Specification<User>) (r, q, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if(StringUtils.isNotBlank(user.getUserName())){
                predicates.add(cb.equal(r.get("userName"), user.getUserName()));
            }
            if(StringUtils.isNotBlank(user.getAge())){
                predicates.add(cb.equal(r.get("age"), user.getAge()));
            }
            return cb.and(predicates.stream().toArray(Predicate[]::new));
        };
        final List<User> all = userRepository.findAll(specification);
        return all;
    }
}
