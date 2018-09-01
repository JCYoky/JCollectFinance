package me.hjc.updatedividends.model;

/**
 * @Author: HJC
 * @Date: Created on 2018/7/15
 * 分红数据基本信息实体
 */
public class Dividend {
    int id;
    //股票名称
    String name;
    //股票代码
    String code;
    //公告日期
    String ad;
    //进度
    String sch;
    //分红对象
    String object;
    //派息股本基数
    String cb;
    //每10股现金(含税)
    String it;
    //每10股送红股
    String rs;
    //每10股转增股本
    String fs;
    //股权登记日
    String rd;
    //除权除息日
    String edd;
    //股息到帐日
    String dad;
    //红股上市日
    String rsod;
    //转增股本上市日
    String fsod;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSch() {
        return sch;
    }

    public void setSch(String sch) {
        this.sch = sch;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
    }

    public String getIt() {
        return it;
    }

    public void setIt(String it) {
        this.it = it;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getEdd() {
        return edd;
    }

    public void setEdd(String edd) {
        this.edd = edd;
    }

    public String getDad() {
        return dad;
    }

    public void setDad(String dad) {
        this.dad = dad;
    }

    public String getRsod() {
        return rsod;
    }

    public void setRsod(String rsod) {
        this.rsod = rsod;
    }

    public String getFsod() {
        return fsod;
    }

    public void setFsod(String fsod) {
        this.fsod = fsod;
    }
}
