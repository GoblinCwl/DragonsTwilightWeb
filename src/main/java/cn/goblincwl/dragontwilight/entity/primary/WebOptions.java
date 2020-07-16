package cn.goblincwl.dragontwilight.entity.primary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotEmpty(message = "key不能为空")
    @Basic
    @Column(name = "opt_key", nullable = false)
    private String optKey;

    @NotEmpty(message = "value不能为空")
    @Basic
    @Column(name = "opt_value")
    private String optValue;

    @Basic
    @Column(name = "remarks", nullable = false)
    private String remarks;

}

