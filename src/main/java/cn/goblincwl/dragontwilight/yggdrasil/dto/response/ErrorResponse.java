package cn.goblincwl.dragontwilight.yggdrasil.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends JSONResponse {
    @JsonProperty("error")
    private String error;
    @JsonProperty("errorMessage")
    private String errorMessage;
}
