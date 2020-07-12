package cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCUserProperty {
    @NotBlank
    private String name;
    @NotBlank
    private String vale;
}
