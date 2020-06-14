package com.goblincwl.dragontwilight.dao;

import com.goblincwl.dragontwilight.entity.BlessingUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description BS用户 Repository
 * @create 2020-06-14 18:35
 */
@Repository
public interface BlessingUsersRepository extends JpaRepository<BlessingUsers, Integer> {

    @Query(nativeQuery = true, value = "SELECT COUNT(1)\n" +
            "FROM BLESSING_USERS\n" +
            "WHERE DATE_FORMAT(REGISTER_AT, '%Y-%m-%d') = :date")
    long getDateCount(@Param("date") String date);
}
