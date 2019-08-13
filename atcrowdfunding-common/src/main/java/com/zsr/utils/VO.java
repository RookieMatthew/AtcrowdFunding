package com.zsr.utils;

import com.zsr.bean.MemberCert;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/8/1 11:05
 */
public class VO {

    private List<Integer> ids = new ArrayList<>();

    private List<MemberCert> certimgs = new ArrayList<MemberCert>();


    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<MemberCert> getCertimgs() {
        return certimgs;
    }

    public void setCertimgs(List<MemberCert> certimgs) {
        this.certimgs = certimgs;
    }
}
