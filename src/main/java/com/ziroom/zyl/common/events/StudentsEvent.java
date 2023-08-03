package com.ziroom.zyl.common.events;

import com.ziroom.zyl.entity.Students;

public class StudentsEvent extends BaseEvent<Students>{

    public StudentsEvent(Students students){
        super(students);
    }
}
