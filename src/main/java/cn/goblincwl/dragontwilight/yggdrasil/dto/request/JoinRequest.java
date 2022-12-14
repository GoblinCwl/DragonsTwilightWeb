package cn.goblincwl.dragontwilight.yggdrasil.dto.request;

import cn.goblincwl.dragontwilight.yggdrasil.constant.MinecraftConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    @JsonProperty(value = "accessToken", required = true)
    @Pattern(regexp = MinecraftConstant.uuid)
    private String accessToken;
    @JsonProperty(value = "selectedProfile", required = true)
    @Pattern(regexp = MinecraftConstant.uuid)
    private String selectedProfile;
    @JsonProperty(value = "serverId", required = true)
    private String serverId;
}
