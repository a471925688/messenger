package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

//    List<Role> selectByUserId(Integer userId);

    @Query("select r from Role r where r.roleId in (select u.roleId from com.noah.solutions.system.entity.UserRole u  where u.userId = ?1)")
    List<Role> selectByUserId(Integer userId);


    @Query("select r from Role r order by  r.createTime")
    List<Role> selectOrderByCreateTime();

    List<Role> findAllByIsDelete(Integer isDelete);

    //通過角色名稱查詢角色id
    @Query("select r.roleId from Role r where r.roleName = ?1")
    Integer findIdByUsername(String username);

}
