package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.Place;

import java.util.List;

public interface PlaceService {

    List<Place>  listAll();//获取所有地区(list)

    List<Place>  ztreeAll();//获取所有地区(树形)

    List<Place> listChild(String id);//通过父id获取子级(仅下一级子集)

    List<Place> ztreeChild(String id);//通过父id获取子孙级(树形)

}
