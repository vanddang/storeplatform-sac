package com.skplanet.storeplatform.sac.client.display.vo.openapi;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MusicDetailSacReq {

    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    @Pattern(regexp="^songId$|^tumsSongId$")
    private String idType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }
}
