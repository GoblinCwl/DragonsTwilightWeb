package cn.goblincwl.dragontwilight.yggdrasil.dto.request;

import cn.goblincwl.dragontwilight.yggdrasil.constant.MinecraftConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class InvalidateRequest {
    @JsonProperty(value = "accessToken", required = true)
    @Pattern(regexp = MinecraftConstant.uuid)
    @NotBlank
    private String accessToken;
    @JsonProperty(value = "clientToken", required = false)
    @Pattern(regexp = MinecraftConstant.uuid)
    @Nullable
    private String clientToken;
}
