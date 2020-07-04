package com.goblincwl.dragontwilight.yggdrasil.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goblincwl.dragontwilight.yggdrasil.constant.MinecraftConstant;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest {
    @Pattern(regexp = MinecraftConstant.uuid, message = "Bad accessToken")
    @JsonProperty(value = "accessToken", required = true)
    // 要求传入旧的 accessToken
    private String accessToken;
    @JsonProperty(value = "clientToken", required = false)
    @Pattern(regexp = MinecraftConstant.uuid, message = "Bad clientToken")
    // 可选的旧的 clientTOken
    private String clientToken;
    @JsonProperty(value = "requestUser", required = true)
    @NotNull(message = "Bad requestUser")
    // 是否请求用户
    private Boolean requestUser;
    @JsonProperty(value = "selectedProfile", required = false)
    @Nullable
    // 要选择的新角色，可以不存在
    private MCProfile selectedProfile;
}
