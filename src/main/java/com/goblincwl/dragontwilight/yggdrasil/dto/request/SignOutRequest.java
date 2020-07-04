package com.goblincwl.dragontwilight.yggdrasil.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goblincwl.dragontwilight.yggdrasil.constant.UserRegexpConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignOutRequest {
    @JsonProperty(required = true)
    @Pattern(regexp = UserRegexpConstant.username, message = "Bad username")
    private String username;
    @JsonProperty(required = true)
    @Pattern(regexp = UserRegexpConstant.password, message = "Bad credentials")
    private String password;
}
