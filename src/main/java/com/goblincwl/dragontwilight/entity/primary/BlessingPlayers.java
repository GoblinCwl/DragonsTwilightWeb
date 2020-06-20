package com.goblincwl.dragontwilight.entity.primary;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-06-20 22:10
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blessing_players", schema = "mc_base", catalog = "")
public class BlessingPlayers {
    private Integer pid;
    private Integer uid;
    private String name;
    private Integer tidCape;
    private Timestamp lastModified;
    private Integer tidSkin;

    @Id
    @Column(name = "pid", nullable = false)
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "uid", nullable = false)
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "tid_cape", nullable = false)
    public Integer getTidCape() {
        return tidCape;
    }

    public void setTidCape(Integer tidCape) {
        this.tidCape = tidCape;
    }

    @Basic
    @Column(name = "last_modified", nullable = false)
    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    @Basic
    @Column(name = "tid_skin", nullable = false)
    public Integer getTidSkin() {
        return tidSkin;
    }

    public void setTidSkin(Integer tidSkin) {
        this.tidSkin = tidSkin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlessingPlayers that = (BlessingPlayers) o;
        return Objects.equals(pid, that.pid) &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(tidCape, that.tidCape) &&
                Objects.equals(lastModified, that.lastModified) &&
                Objects.equals(tidSkin, that.tidSkin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, uid, name, tidCape, lastModified, tidSkin);
    }
}
