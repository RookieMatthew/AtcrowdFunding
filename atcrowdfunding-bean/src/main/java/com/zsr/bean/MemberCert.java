package com.zsr.bean;

import org.springframework.web.multipart.MultipartFile;

public class MemberCert {
    private Integer id;

    private Integer memberid;

    private Integer certid;

    private String iconpath;

    private MultipartFile fileimg;

    public MultipartFile getFileimg() {
        return fileimg;
    }

    public void setFileimg(MultipartFile fileimg) {
        this.fileimg = fileimg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getCertid() {
        return certid;
    }

    public void setCertid(Integer certid) {
        this.certid = certid;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath == null ? null : iconpath.trim();
    }
}