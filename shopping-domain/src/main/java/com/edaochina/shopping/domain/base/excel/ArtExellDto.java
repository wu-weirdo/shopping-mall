package com.edaochina.shopping.domain.base.excel;

import java.util.List;

/**
 * @author Jason
 */
public class ArtExellDto {
    private Long artId;
    private String title;
    private List<ArtUserDto> dto;

    public Long getArtId() {
        return artId;
    }

    public void setArtId(Long artId) {
        this.artId = artId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ArtUserDto> getDto() {
        return dto;
    }

    public void setDto(List<ArtUserDto> dto) {
        this.dto = dto;
    }
}
