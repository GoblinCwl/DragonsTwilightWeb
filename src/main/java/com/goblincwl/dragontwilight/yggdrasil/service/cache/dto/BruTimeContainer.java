package com.goblincwl.dragontwilight.yggdrasil.service.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BruTimeContainer {
    @NotNull
    private Integer count;
    @NotNull
    private Long expiredTime;
}
