package com.goblincwl.dragontwilight.yggdrasil.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goblincwl.dragontwilight.yggdrasil.constant.MinecraftConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateRequest {
    @JsonProperty(value = "accessToken", required = true)
    @Pattern(regexp = MinecraftConstant.uuid, message = "Bad accessToken")
    @NotBlank
    private String accessToken;
    @JsonProperty(value = "clientToken", required = false)
    @Pattern(regexp = MinecraftConstant.uuid, message = "Bad clientToken")
    @Nullable
    private String clientToken;
}
