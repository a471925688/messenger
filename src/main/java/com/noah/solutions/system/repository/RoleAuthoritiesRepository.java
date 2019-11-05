package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.RoleAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAuthoritiesRepository extends JpaRepository<RoleAuthorities,Integer> {

//    int insertRoleAuths(@Param("roleId") Integer roleId, @Param("authIds") List<Integer> authIds);
    void deleteByAuthorityId(Integer authorityId);
    void deleteByAuthorityIdAndRoleId(Integer authorityId,Integer roleId);

    void deleteAllByRoleId(Integer roleId);


}
