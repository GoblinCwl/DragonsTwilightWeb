package com.goblincwl.dragontwilight.yggdrasil.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ygg_player_profile", schema = "mc_base")
public class YggPlayerProfile {
    // 角色的无符号UUID
    @Id
    @Column(name = "uuid", nullable = false)
    private String UUID;

    // 角色名称
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "model", nullable = false)
    @Builder.Default
    // true, 1 为 STEVE，false, 0 为 ALEX
    private Boolean model = true;

    @Column(name = "texture_setting_time")
    @Nullable
    private Long textureSettingTime;

    @JoinColumn()
    @ManyToOne(fetch = FetchType.EAGER)
    private TextureMapping skinTexture;

    @JoinColumn()
    @ManyToOne(fetch = FetchType.EAGER)
    private TextureMapping capeTexture;
}
