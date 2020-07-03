package com.goblincwl.dragontwilight.yggdrasil.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "password",nullable = false)
    private String password;

    @OneToOne(targetEntity = YggPlayerProfile.class, cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private YggPlayerProfile selectedProfile;
    //
    @OneToMany(targetEntity = YggPlayerProfile.class, cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<YggPlayerProfile> profiles;

    @ManyToMany(targetEntity = YggUserGroup.class, cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<YggUserGroup> groups;

    @OneToMany(targetEntity = TextureMapping.class, cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<TextureMapping> textures;
}
