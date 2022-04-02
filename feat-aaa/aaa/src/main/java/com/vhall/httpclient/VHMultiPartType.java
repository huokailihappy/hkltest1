package com.vhall.httpclient;

/**
 * author: caoyanglong
 * date: 2020/9/14
 */
public enum VHMultiPartType {
    MIXED("helloworld"),ALTERNATIVE("multipart/alternative"),DIGEST("multipart/digest"),PARALLEL("multipart/parallel"),FORM("multipart/form-data");
    String type;

    VHMultiPartType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
