package com.skplanet.storeplatform.sac.display.app.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * ImageSource
 * Updated on : 2014. 01. 23 Updated by : 정희원, SK 플래닛.
 */
public class ImageSource extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private String filePath;
    private String fileNm;
    private String imgCd;
    private Integer width;
    private Integer height;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getImgCd() {
        return imgCd;
    }

    public void setImgCd(String imgCd) {
        this.imgCd = imgCd;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }
}
