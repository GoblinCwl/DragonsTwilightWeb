package cn.goblincwl.dragontwilight.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ☪wl
 * @program MinecrafProject
 * @description 表头
 * @create 2020-07-15 11:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableHead {

    private String headName;
    private Integer width;

}
