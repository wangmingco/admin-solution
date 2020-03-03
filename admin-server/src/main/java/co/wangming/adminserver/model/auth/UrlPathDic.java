package co.wangming.adminserver.model.auth;

import co.wangming.adminserver.model.BaseModel;

/**
 * Created By WangMing On 2020-03-02
 **/
public class UrlPathDic extends BaseModel {

    private long id;
    private String path;
    private String pathName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
}
