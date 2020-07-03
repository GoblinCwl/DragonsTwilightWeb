package com.goblincwl.dragontwilight.repository.primary;

import com.goblincwl.dragontwilight.entity.primary.BlessingPlayers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description BS Player 数据 Repository
 * @create 2020-06-20 22:11
 */
@Repository
public interface BlessingPlayersRepository extends JpaRepository<BlessingPlayers, Integer> {
}
