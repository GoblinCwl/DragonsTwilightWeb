package cn.goblincwl.dragontwilight.entity.primary;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-06-14 18:32
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blessing_textures", schema = "mc_base")
public class BlessingTextures {
    private Integer tid;
    private String name;
    private String type;
    private String hash;
    private Integer size;
    private Integer uploader;
    private Byte isPublic;
    private Timestamp uploadAt;
    private Integer likes;

    @Id
    @Column(name = "tid", nullable = false)
    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
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
    @Column(name = "type", nullable = false, length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "hash", nullable = false, length = 64)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "size", nullable = false)
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Basic
    @Column(name = "uploader", nullable = false)
    public Integer getUploader() {
        return uploader;
    }

    public void setUploader(Integer uploader) {
        this.uploader = uploader;
    }

    @Basic
    @Column(name = "public", nullable = false)
    public Byte getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Byte isPublic) {
        this.isPublic = isPublic;
    }

    @Basic
    @Column(name = "upload_at", nullable = false)
    public Timestamp getUploadAt() {
        return uploadAt;
    }

    public void setUploadAt(Timestamp uploadAt) {
        this.uploadAt = uploadAt;
    }

    @Basic
    @Column(name = "likes", nullable = false)
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlessingTextures that = (BlessingTextures) o;
        return Objects.equals(tid, that.tid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(hash, that.hash) &&
                Objects.equals(size, that.size) &&
                Objects.equals(uploader, that.uploader) &&
                Objects.equals(isPublic, that.isPublic) &&
                Objects.equals(uploadAt, that.uploadAt) &&
                Objects.equals(likes, that.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid, name, type, hash, size, uploader, isPublic, uploadAt, likes);
    }
}
