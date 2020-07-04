package com.goblincwl.dragontwilight.yggdrasil.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goblincwl.dragontwilight.yggdrasil.constant.MinecraftConstant;
import com.goblincwl.dragontwilight.yggdrasil.constant.UserRegexpConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateRequest {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // Minecraft 要求的请求标准，需要进行上传（尽管后面可能不会用到）
    public static class Agent{
        @JsonProperty(required = true)
        @NotBlank(message = "The agent name cannot be blank")
        private String name;
        @JsonProperty(required = true)
        @NotNull(message = "The agent version cannot be blank")
        private Integer version;
    }

    @JsonProperty(required = true)
    @NotBlank(message = "The email cannot be blank")
    @Pattern(regexp = UserRegexpConstant.username, message = "Bad username")
    // 强制要求的请求用户名，使用邮箱正则
    private String username;
    @JsonProperty(required = true)
    @Pattern(regexp = UserRegexpConstant.password, message = "Bad password")
    // 用户请求时使用的密码，参照 README.md 可以获得更多详细信息
    private String password;
    @JsonProperty(value = "clientToken", required = false)
    @Pattern(regexp = MinecraftConstant.uuid, message = "Bad client token")
    // 用户请求时使用的无符号 UUID，参照 README.md 可以获得更多信息
    private String clientToken;
    @JsonProperty(value = "requestUser", required = true)
    @NotNull(message = "Required to choose user")
    // 用户是否请求用户信息
    private Boolean requestUser;
    @JsonProperty(required = true)
    @NotNull(message = "The agent cannot be null")
    // MC 规定请求时必须具有的实体
    private Agent agent;
}
