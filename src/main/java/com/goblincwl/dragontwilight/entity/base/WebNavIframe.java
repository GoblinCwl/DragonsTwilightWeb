package com.goblincwl.dragontwilight.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-05-24 0:34
 */
@Entity
@Data
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebNavIframe that = (WebNavIframe) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(name, that.name) &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(slot, that.slot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, name, uri, icon, slot);
    }
}
