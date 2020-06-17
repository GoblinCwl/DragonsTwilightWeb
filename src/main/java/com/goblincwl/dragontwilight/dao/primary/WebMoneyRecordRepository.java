package com.goblincwl.dragontwilight.dao.primary;

import com.goblincwl.dragontwilight.entity.primary.WebMoneyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 资金记录 Repository
 * @create 2020-06-17 23:13
 */
@Repository
public interface WebMoneyRecordRepository extends JpaRepository<WebMoneyRecord, Integer> {
    @Query(nativeQuery = true, value = "SELECT SUM(IFNULL(MONEY,0)) FROM WEB_MONEY_RECORD")
    Double sumMoney();

    @Query(nativeQuery = true, value = "SELECT IFNULL(SUM(IFNULL(MONEY, 0)),0)\n" +
            "FROM WEB_MONEY_RECORD\n" +
            "WHERE DATE_FORMAT(MONEY_DATE, '%Y-%m-%d') = :date")
    Long getMoneyDateCount(@Param("date") String date);
}
