package com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mctexture;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCTextureDetail {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String url;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    // 已知的键有：model，model的值可以是 default 也可以是 slim
    private Map<String, String> metadata;
}
