package com.goblincwl.dragontwilight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 首页导航栏实体类
 * @create 2020-05-24 0:34
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "web_nav_iframe", schema = "mc_base")
public class WebNavIframe {
    private Integer id;
    private String title;
    private String name;
    private String uri;
    private String icon;
    private Integer slot;
    private String liClass;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 30)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "uri", nullable = false)
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Basic
    @Column(name = "icon", nullable = false)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "slot", nullable = false)
    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    @Basic
    @Column(name = "li_class", length = 30)
    public String getLiClass() {
        return liClass;
    }

    public void setLiClass(String liClass) {
        this.liClass = liClass;
    }
}
