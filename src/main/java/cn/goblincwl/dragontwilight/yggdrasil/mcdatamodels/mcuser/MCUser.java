package cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCUser {
    @NotBlank
    // 用户的无符号 UUID 字符串
    private String id;

    @NotNull
     /*
         用户的属性组，一般情况下只可能出现
         preferredLanguage 是 Java Locale 格式的
         https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#toString--
         例如：en，zh_CN
      */
    private List<MCUserProperty> properties;
}
