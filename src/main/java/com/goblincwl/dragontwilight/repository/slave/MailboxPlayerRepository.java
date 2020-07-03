package com.goblincwl.dragontwilight.repository.slave;

import com.goblincwl.dragontwilight.entity.slave.MailboxPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description MailBox Player邮箱数据 Repository
 * @create 2020-06-17 17:54
 */
@Repository
public interface MailboxPlayerRepository extends JpaRepository<MailboxPlayer, Integer> {
}
