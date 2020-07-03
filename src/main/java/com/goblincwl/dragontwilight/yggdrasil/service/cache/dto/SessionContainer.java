package com.goblincwl.dragontwilight.yggdrasil.service.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionContainer {
    private String accessToken;
    private String selectedProfile;
    private String ip;
    private Long expiredTime;
}
