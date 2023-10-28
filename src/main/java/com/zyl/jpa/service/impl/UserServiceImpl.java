package com.zyl.jpa.service.impl;


import com.google.common.collect.Lists;
import com.zyl.jpa.repository.UserRepository;
import com.zyl.jpa.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.zyl.jpa.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service(value = "jpa-user-service")
public class UserServiceImpl implements UserService {
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
