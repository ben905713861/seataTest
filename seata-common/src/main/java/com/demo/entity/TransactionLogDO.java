/**
 * @author 作者: Ben
 * @date 创建日期: 2021年1月4日
 * @description
 */

package com.demo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.demo.entity.enumClass.TransactionLogStatus;

@TableName("transaction_log")
public class TransactionLogDO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Long branchId;
    private String xid;
    private TransactionLogStatus status;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public TransactionLogStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionLogStatus status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
