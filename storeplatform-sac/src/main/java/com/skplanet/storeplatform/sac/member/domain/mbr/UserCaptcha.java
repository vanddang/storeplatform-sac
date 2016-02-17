package com.skplanet.storeplatform.sac.member.domain.mbr;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "TB_US_OUSERMBR_CAPTCHA")
@SequenceGenerator(name = "SQ_US_OUSERMBR_CAPTCHA",
        sequenceName="SQ_US_OUSERMBR_CAPTCHA",
        allocationSize=1)
public class UserCaptcha {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_US_OUSERMBR_CAPTCHA")
    private Integer seq;
    private String captchaValue;    //captcha 문자
    private String imageSign;       //인증코드 확인을 위한 Signature
    private String signData;        //signature를 구성한 source data, currentTimeMills
    private String filePath;        //captcha 이미지 저장 경로
    @Column(length = 1, columnDefinition = "char(1)")
    private String useYn;           //사용 여부 (인증완료시 'N'처리)
    private Date regDt;             //등록일

    public UserCaptcha() {}
    public UserCaptcha(String captchaValue, String imageSign, String signData, String filePath, String useYn) {
        this.captchaValue = captchaValue;
        this.imageSign = imageSign;
        this.signData = signData;
        this.filePath = filePath;
        this.useYn = useYn;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCaptchaValue() {
        return captchaValue;
    }

    public void setCaptchaValue(String captchaValue) {
        this.captchaValue = captchaValue;
    }

    public String getImageSign() {
        return imageSign;
    }

    public void setImageSign(String imageSign) {
        this.imageSign = imageSign;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    @PrePersist
    public void prePersist() {
        Date currDate = new Date();
        this.regDt = currDate;
    }

    @PreUpdate
    public void preUpdate() {
    }
}
