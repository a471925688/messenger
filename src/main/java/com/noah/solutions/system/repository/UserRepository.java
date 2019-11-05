package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

//    User getByUsername(String username);
    User findByUsername(String userName);

    boolean existsByUsername(String username);

    Integer countAllByCreateTimeGreaterThanAndType(Date time, Integer type);
    Integer countAllByType(Integer type);

    User findByUserId(Integer userId);

    User findByPhone(String phone);

    boolean existsByPhone(String phone);

    List<User> findAllByType(Integer type);

    //查询活跃会员
    @Query("select count(distinct o.userId) from Order o where o.createTime>=?1")
    Integer countByActiveUser(Date time);

    @Query("select u.tokenMoney from  User u where u.userId=?1")
    String getTokenMoneyById(Integer userId);//查詢代幣

    @Modifying
    @Query("update User u set u.tokenMoney = (u.tokenMoney+?1) where u.userId=?2")
    void addTokenMoney(Double tokenMoney,Integer userId);

    @Modifying
    @Query("update User u set u.tokenMoney = (u.tokenMoney-?1) where u.userId=?2")
    void subTokenMoney(Double tokenMoney,Integer userId);

    @Modifying
    @Query("update User u set u.nickName = ?2 where u.userId = ?1")
    void updateNickName(Integer userId, String nickName);

    @Modifying
    @Query("update User u set u.avatar = ?2 where u.userId = ?1")
    void updateAvatar(Integer userId,String avatar);

    @Modifying
    @Query("update User u set u.appId = ?2 where u.userId = ?1")
    void updateAppId(Integer userId,String appId);


    @Query("select u.appId from User u where u.userId=?1")
    String getAppId(Integer userId);
}
