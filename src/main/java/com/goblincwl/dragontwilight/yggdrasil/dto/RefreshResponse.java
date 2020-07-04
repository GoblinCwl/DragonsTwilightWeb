package com.goblincwl.dragontwilight.yggdrasil.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshResponse {
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
