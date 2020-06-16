package com.goblincwl.dragontwilight.dao.primary;

import com.goblincwl.dragontwilight.entity.primary.BlessingTextures;
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
public interface BlessingTextureRepository extends JpaRepository<BlessingTextures, Integer> {

    @Query(nativeQuery = true, value = "SELECT COUNT(1)\n" +
            "FROM BLESSING_TEXTURES\n" +
            "WHERE DATE_FORMAT(UPLOAD_AT, '%Y-%m-%d') = :date")
    long getDateCount(@Param("date") String date);
}
