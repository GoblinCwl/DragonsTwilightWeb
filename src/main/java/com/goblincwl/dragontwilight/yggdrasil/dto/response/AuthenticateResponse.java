package com.goblincwl.dragontwilight.yggdrasil.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateResponse extends JSONResponse{
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("clientToken")
    private String clientToken;
    @JsonProperty("availableProfiles")
    private List<MCProfile> availableProfiles;
    @JsonProperty("selectedProfile")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MCProfile selectedProfile;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MCUser user;
}
