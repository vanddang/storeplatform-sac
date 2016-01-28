package com.skplanet.storeplatform.purchase.cancel.vo;

import com.skplanet.storeplatform.purchase.client.common.vo.ExtraInfo;

/**
 * 구매취소 사유 저장 VO
 * Created by eastaim, SK planet on 16. 1. 15..
 */
public class PurchaseRefundReason extends ExtraInfo{

    /**
     * 구매취소/환불 사유 저장
     * 구매취소 사유 : OR006401
     * 환불 사유 : OR006402
     * col_01 : 사유코드 (공통)
     * col_02 : 한도요금제 고지여부 (Only 구매취소)
     * col_03 : 현금성 캐쉬 잔액 (Only 환불)
     * col_04 : 보너스 포인트 사용액(환수포인트) (Only 환불)
     * text_01 : 기타 메세지 (공통)
     */
    public void setReasonCd(String reasonCd) {
        super.setCol01(reasonCd);
    }

    public void setCashAvailAmt(Integer cashAvailAmt) {
        super.setCol03(String.valueOf(cashAvailAmt));
    }

    public void setPointUseAmt(Integer pointUseAmt) {
        super.setCol04(String.valueOf(pointUseAmt));
    }

    public void setReasonMsg(String reasonMsg) {
        super.setText01(reasonMsg);
    }

}
