package com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCProfileProperty {
    @NotBlank
    private String name;

    @NotBlank
    private String value;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    // 一个Base 64 字符串，期中包含属性值的数字签名
    // 使用 SHA1withRSA 算法，参见签名密钥对
    // https://github.com/yushijinhun/authlib-injector/wiki/%E7%AD%BE%E5%90%8D%E5%AF%86%E9%92%A5%E5%AF%B9
    private String signature;
}
