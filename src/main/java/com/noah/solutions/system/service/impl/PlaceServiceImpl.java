package com.noah.solutions.system.service.impl;

import com.noah.solutions.system.repository.PlaceRepository;
import com.noah.solutions.system.entity.Place;
import com.noah.solutions.system.service.PlaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Resource
    private PlaceRepository placeRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public List<Place> listAll() {
        return placeRepository.findAll();
    }

    @Override
    public List<Place> ztreeAll() {
        List<Place> places = placeRepository.findAllByLevel(1);
        places.forEach(v->v.setChild(getZtreeChild(v.getId())));
        return places;
    }

    @Override
    public List<Place> listChild(String id) {
        return placeRepository.findAllByParentId(id);
    }

    @Override
    public List<Place> ztreeChild(String id) {
        return getZtreeChild(id);
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////




    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    private  List<Place> getZtreeChild(String id){
        List<Place> places = listChild(id);
        places.forEach(v->v.setChild(getZtreeChild(v.getId())));
        return places;
    }

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}
