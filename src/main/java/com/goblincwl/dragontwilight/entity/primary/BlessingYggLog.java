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
 * @create 2020-06-14 20:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blessing_ygg_log", schema = "mc_base")
public class BlessingYggLog {
    private Integer id;
    private String action;
    private Integer userId;
    private Integer playerId;
    private String parameters;
    private String ip;
    private Timestamp time;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "action", nullable = false, length = 255)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "player_id", nullable = false)
    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    @Basic
    @Column(name = "parameters", nullable = false, length = 255)
    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Basic
    @Column(name = "ip", nullable = false, length = 255)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlessingYggLog that = (BlessingYggLog) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(action, that.action) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(playerId, that.playerId) &&
                Objects.equals(parameters, that.parameters) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action, userId, playerId, parameters, ip, time);
    }
}
