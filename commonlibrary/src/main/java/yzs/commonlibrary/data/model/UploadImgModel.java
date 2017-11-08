package yzs.commonlibrary.data.model;

import java.util.List;

/**
 * Des：图片上床
 * create by Zishu.Ye on 2017/11/8  20:16
 */
public class UploadImgModel {
    List<String> fileids;
    List<String> filenames;
    String driverid;

    public List<String> getFileids() {
        return fileids;
    }

    public void setFileids(List<String> fileids) {
        this.fileids = fileids;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }
}
