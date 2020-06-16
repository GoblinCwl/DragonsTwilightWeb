package com.goblincwl.dragontwilight.dao.primary;

import com.goblincwl.dragontwilight.entity.primary.BlessingYggLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description BS材质 Repository
 * @create 2020-06-14 18:35
 */
@Repository
public interface BlessingYggLogRepository extends JpaRepository<BlessingYggLog, Integer> {

    @Query(nativeQuery = true, value = "SELECT COUNT(1)\n" +
            "FROM (SELECT BP.UID\n" +
            "      FROM BLESSING_YGG_LOG BYL\n" +
            "               LEFT JOIN BLESSING_PLAYERS BP ON BYL.USER_ID = BP.UID\n" +
            "      WHERE ACTION = 'has_joined' AND BP.UID IS NOT NULL AND DATE_FORMAT(BYL.TIME, '%Y-%m-%d') = :date\n" +
            "      GROUP BY BP.NAME) AS BB\n")
    Long getLoginPlayerDateCount(@Param("date") String date);

    @Query(nativeQuery = true, value = "SELECT COUNT(1)\n" +
            "FROM (SELECT BP.NAME AS 'name'\n" +
            "      FROM BLESSING_YGG_LOG BYL\n" +
            "               LEFT JOIN BLESSING_PLAYERS BP ON BYL.USER_ID = BP.UID\n" +
            "      WHERE ACTION = 'has_joined' AND BP.UID IS NOT NULL\n" +
            "      GROUP BY BP.NAME) AS BBN")
    Long groupCount();
}
