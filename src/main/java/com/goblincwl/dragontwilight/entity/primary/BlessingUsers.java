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
 * @create 2020-06-14 18:31
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blessing_users", schema = "mc_base")
public class BlessingUsers {
    private Integer uid;
    private String email;
    private String nickname;
    private Integer score;
    private Integer avatar;
    private String password;
    private String ip;
    private Integer permission;
    private Timestamp lastSignAt;
    private Timestamp registerAt;
    private Byte verified;
    private String verificationToken;
    private String rememberToken;

    @Id
    @Column(name = "uid", nullable = false)
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "nickname", nullable = false, length = 50)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "score", nullable = false)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "avatar", nullable = false)
    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "ip", nullable = false, length = 45)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "permission", nullable = false)
    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    @Basic
    @Column(name = "last_sign_at", nullable = false)
    public Timestamp getLastSignAt() {
        return lastSignAt;
    }

    public void setLastSignAt(Timestamp lastSignAt) {
        this.lastSignAt = lastSignAt;
    }

    @Basic
    @Column(name = "register_at", nullable = false)
    public Timestamp getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(Timestamp registerAt) {
        this.registerAt = registerAt;
    }

    @Basic
    @Column(name = "verified", nullable = false)
    public Byte getVerified() {
        return verified;
    }

    public void setVerified(Byte verified) {
        this.verified = verified;
    }

    @Basic
    @Column(name = "verification_token", nullable = false, length = 255)
    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    @Basic
    @Column(name = "remember_token", nullable = true, length = 100)
    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlessingUsers that = (BlessingUsers) o;
        return Objects.equals(uid, that.uid) &&
                Objects.equals(email, that.email) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(score, that.score) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(password, that.password) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(permission, that.permission) &&
                Objects.equals(lastSignAt, that.lastSignAt) &&
                Objects.equals(registerAt, that.registerAt) &&
                Objects.equals(verified, that.verified) &&
                Objects.equals(verificationToken, that.verificationToken) &&
                Objects.equals(rememberToken, that.rememberToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, email, nickname, score, avatar, password, ip, permission, lastSignAt, registerAt, verified, verificationToken, rememberToken);
    }
}
