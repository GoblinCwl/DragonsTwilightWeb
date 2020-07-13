package cn.goblincwl.dragontwilight.yggdrasil.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "ygg_user", schema = "mc_base")
public class YggUser {
    @Id
    @Column(name = "uuid", nullable = false)
    private String UUID;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "ip_addr")
    private String ipAddr;

    @Column(name = "reg_date")
    private Date regDate;

    @Column(name = "is_admin")
    private Integer isAdmin;
}
