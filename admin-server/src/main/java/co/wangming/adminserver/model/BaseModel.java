package co.wangming.adminserver.model;

import java.util.Date;

/**
 * Created By WangMing On 2020-03-01
 **/
public class BaseModel {

    private int status;
    private Date createTime;
    private Date updateTime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
