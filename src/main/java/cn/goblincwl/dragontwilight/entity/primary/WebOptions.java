package cn.goblincwl.dragontwilight.entity.primary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 应用设置项实体类
 * @create 2020-05-26 14:53
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "web_options", schema = "mc_base")
public class WebOptions {
    private Integer id;
    private String optKey;
    private String optValue;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "opt_key", nullable = false, length = 255)
    public String getOptKey() {
        return optKey;
    }

    public void setOptKey(String optKey) {
        this.optKey = optKey;
    }

    @Basic
    @Column(name = "opt_value", nullable = true, length = 255)
    public String getOptValue() {
        return optValue;
    }

    public void setOptValue(String optValue) {
        this.optValue = optValue;
    }
}

