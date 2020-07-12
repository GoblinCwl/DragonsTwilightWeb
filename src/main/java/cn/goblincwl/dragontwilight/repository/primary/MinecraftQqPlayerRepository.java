package cn.goblincwl.dragontwilight.repository.primary;

import cn.goblincwl.dragontwilight.entity.primary.MinecraftQqPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description MinecraftQq repository
 * @create 2020-05-28 9:53
 */
@Repository
public interface MinecraftQqPlayerRepository extends JpaRepository<MinecraftQqPlayer, Integer> {
}
