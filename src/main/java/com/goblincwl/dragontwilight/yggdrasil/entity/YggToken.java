package com.goblincwl.dragontwilight.yggdrasil.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ygg_token", schema = "mc_base")
public class YggToken {
    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    private String UUID;

    @Column(name = "access_token", unique = true, nullable = false)
    private String accessToken;

    @Column(name = "client_token", unique = true, nullable = false)
    private String clientToken;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner", unique = true, nullable = false)
    private YggUser owner;

    @Column(name = "expired_time", nullable = false)
    private long expiredTime;
}
