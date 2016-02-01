package com.skplanet.storeplatform.sac.display.promotion.vo;

import java.util.List;

public class PromotionTargetUserKeysPaginated {
    private String startKey;
    private List<String> userKeys;

    public boolean hasNext() {
        return startKey != null;
    }



    // Getters, Setters
    public String getStartKey() {
        return startKey;
    }

    public void setStartKey(String startKey) {
        this.startKey = startKey;
    }

    public List<String> getUserKeys() {
        return userKeys;
    }

    public void setUserKeys(List<String> userKeys) {
        this.userKeys = userKeys;
    }
}
