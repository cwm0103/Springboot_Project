package com.oper.dto;

import com.oper.entity.PubDirectory;
import com.oper.entity.PubView;

import java.util.List;

/**
 * cwm add 2018-8-11
 * 目录视图View
 */
public class PubDirectoryVModel extends PubDirectory {

    private List<PubView> pubViews;
    public List<PubView> getPubViews() {
        return pubViews;
    }

    public void setPubViews(List<PubView> pubViews) {
        this.pubViews = pubViews;
    }





}
