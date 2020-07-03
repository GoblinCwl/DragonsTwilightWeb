package com.goblincwl.dragontwilight.yggdrasil.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ygg_texture_mapping", schema = "mc_base")
public class TextureMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = TextureObject.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private TextureObject textureObject;

    @Column(name = "texture_name")
    private String textureName;

    @Column(nullable = false, name = "hash_code")
    private String hashCode;

    @Column(nullable = false, name = "texture_type")
    private String textureType;
}
