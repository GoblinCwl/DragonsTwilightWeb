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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ygg_user_group", schema = "mc_base")
public class YggUserGroup {
    @Id
    @Column(nullable = false, name = "group_name")
    private String groupName;

    @Column(nullable = false, name = "max_skin")
    private Integer maxSkin;

    @Column(nullable = false, name = "max_cape")
    private Integer maxCape;

    @Builder.Default
    @Column(nullable = false, name = "is_default")
    private Boolean isDefault = false;
}
