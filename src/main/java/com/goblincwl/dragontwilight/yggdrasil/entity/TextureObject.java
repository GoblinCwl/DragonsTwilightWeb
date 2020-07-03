package com.goblincwl.dragontwilight.yggdrasil.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ygg_texture_object", schema = "mc_base")
public class TextureObject {
    @Id
    @Column(nullable = false, name = "hash_code")
    private String hashCode;

    @Column(columnDefinition = "mediumblob", nullable = false, name = "data")
    private byte[] data;
}
