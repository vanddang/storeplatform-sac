package com.skplanet.storeplatform.sac.display.music.vo;

/**
 * SubContent
 * Updated on : 2014. 01. 24 Updated by : 정희원, SK 플래닛.
 */
public class SubContent {

    private String dpPgQultCd;
    private String filePath;
    private long fileSize;

    public String getDpPgQultCd() {
        return dpPgQultCd;
    }

    public void setDpPgQultCd(String dpPgQultCd) {
        this.dpPgQultCd = dpPgQultCd;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
