package cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mctexture;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

// 注意，这是一个 JSON 字符串的序列化对象
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCTexture {
    @NotNull
    private Long timestamp;
    @JsonProperty("profileId")
    @NotBlank
    private String profileId;
    @JsonProperty("profileName")
    @NotBlank
    private String profileName;
    @NotNull
    private Map<String, MCTextureDetail> textures;

    public String toBase64() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(this);
        byte[] base64 = Base64.getMimeEncoder().encode(json.getBytes(StandardCharsets.UTF_8));
        return new String(base64, StandardCharsets.UTF_8);
    }
}
