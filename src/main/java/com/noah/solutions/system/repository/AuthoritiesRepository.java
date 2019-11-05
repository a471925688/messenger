package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities,Integer> {

//    @Query("select a from Authorities a join RoleAuthorities ra on a.authorityId = ra.authorityId")
    @Query("select a from Authorities a where a.authorityId in (select ra.authorityId from com.noah.solutions.system.entity.RoleAuthorities ra where ra.roleId in (select ur.roleId from com.noah.solutions.system.entity.UserRole ur where ur.userId=?1)) order by a.orderNumber")
    List<Authorities> listByUserId(Integer userId);



    @Query("select  a from Authorities  a where a.authorityId in (select ra.authorityId from com.noah.solutions.system.entity.RoleAuthorities ra where  ra.roleId in ?1)")
    List<Authorities> listByRoleIds(@Param("roleIds") List<Integer> roleIds);

    @Query("select  a from Authorities  a where a.authorityId in (select ra.authorityId from com.noah.solutions.system.entity.RoleAuthorities ra where  ra.roleId = ?1)")
    List<Authorities> listByRoleId(Integer roleId);

    @Query("select  a from Authorities a order by a.orderNumber")
    List<Authorities> selectAllOrderByOrderNumber();


    @Query("select  a from Authorities a where a.isMenu = ?1 order by a.orderNumber")
    List<Authorities> selectAllByIsMenuOrderByOrderNumber(Integer isMenu);

    @Query("select  a from Authorities a where a.parentId = ?1 order by a.orderNumber")
    List<Authorities> selectAllByParentIdOrderByOrderNumber(Integer parentId);

    Authorities findByAuthorityId(Integer authorityId);
}
