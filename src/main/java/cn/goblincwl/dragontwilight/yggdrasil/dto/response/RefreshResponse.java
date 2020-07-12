package cn.goblincwl.dragontwilight.yggdrasil.dto.response;

import cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshResponse extends JSONResponse {
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("clientToken")
    private String clientToken;
    @JsonProperty("selectedProfile")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MCProfile selectedProfile;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MCUser user;
}
