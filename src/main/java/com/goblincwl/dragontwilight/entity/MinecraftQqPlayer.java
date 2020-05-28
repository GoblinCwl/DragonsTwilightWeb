package com.goblincwl.dragontwilight.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description MinecraftQq
 * @create 2020-05-28 9:51
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "minecraft_qq_player", schema = "mc_base", catalog = "")
public class MinecraftQqPlayer {
    private Integer id;
    private String name;
    private String nick;
    private String qq;
    private Byte admin;

    @Id
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Nick", nullable = true, length = 255)
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Basic
    @Column(name = "QQ", nullable = false, length = 255)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Basic
    @Column(name = "Admin", nullable = false)
    public Byte getAdmin() {
        return admin;
    }

    public void setAdmin(Byte admin) {
        this.admin = admin;
    }
}
