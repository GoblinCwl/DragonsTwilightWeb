package cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import cn.goblincwl.dragontwilight.yggdrasil.constant.MinecraftConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCProfile {
    @NotBlank
    // 用户角色无符号 UUID 字符串
    @Pattern(regexp = MinecraftConstant.uuid)
    private String id;

    @NotBlank
    // 用户角色名称
    @Pattern(regexp = MinecraftConstant.uuid)
    private String name;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    // 角色属性中已知的包含的数据是：
    // textures，他是一个Base64字符串，内容是JSON字符串，
    // 结构请参考 MCTexture.class
    private List<MCProfileProperty> properties;
}
