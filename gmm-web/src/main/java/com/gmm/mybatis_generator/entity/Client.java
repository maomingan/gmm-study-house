package com.gmm.mybatis_generator.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * (Client)表实体类
 *
 * @author makejava
 * @since 2024-11-13 15:33:32
 */
@SuppressWarnings("serial")
public class Client extends Model<Client> {
    
    private Integer pknum;
    
    private Integer prepaid;
    
    private Integer logrequest;
    
    private String clientname;
    
    private String shortname;
    
    private String contractname;
    
    private String ficoclientname;
    
    private Date createdate;
    
    private String note;
    
    private Integer autobill;
    
    private Integer ordernum;
    //1、全国性银行 2、区域性银行或民营银行 3、持牌消金  4、其他
    private Integer clienttype;
    //1: 正常 0：账户关闭
    private Integer status;
    
    private Integer parentpknum;


    public Integer getPknum() {
        return pknum;
    }

    public void setPknum(Integer pknum) {
        this.pknum = pknum;
    }

    public Integer getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(Integer prepaid) {
        this.prepaid = prepaid;
    }

    public Integer getLogrequest() {
        return logrequest;
    }

    public void setLogrequest(Integer logrequest) {
        this.logrequest = logrequest;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getContractname() {
        return contractname;
    }

    public void setContractname(String contractname) {
        this.contractname = contractname;
    }

    public String getFicoclientname() {
        return ficoclientname;
    }

    public void setFicoclientname(String ficoclientname) {
        this.ficoclientname = ficoclientname;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAutobill() {
        return autobill;
    }

    public void setAutobill(Integer autobill) {
        this.autobill = autobill;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getClienttype() {
        return clienttype;
    }

    public void setClienttype(Integer clienttype) {
        this.clienttype = clienttype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentpknum() {
        return parentpknum;
    }

    public void setParentpknum(Integer parentpknum) {
        this.parentpknum = parentpknum;
    }

}

