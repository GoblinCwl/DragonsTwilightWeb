package com.goblincwl.dragontwilight.yggdrasil.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "ygg_password_link", schema = "mc_base")
public class YggPasswordLink {
    @Id
    @Column(name = "uuid", nullable = false)
    private String UUID;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "end_time", nullable = false)
    private Long endTime;
}
