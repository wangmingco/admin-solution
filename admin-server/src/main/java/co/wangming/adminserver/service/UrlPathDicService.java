package co.wangming.adminserver.service;

import co.wangming.adminserver.mapper.auth.PermissionMapper;
import co.wangming.adminserver.mapper.auth.UrlPathDicMapper;
import co.wangming.adminserver.model.auth.Permission;
import co.wangming.adminserver.model.auth.UrlPathDic;
import co.wangming.adminserver.util.SpringUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created By WangMing On 2020-03-02
 **/
@Service
public class UrlPathDicService {

    @Resource
    private UrlPathDicMapper urlPathDicMapper;

    @Resource
    private PermissionMapper permissionMapper;

    public void insertPathUrls() {

        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean(RequestMappingHandlerMapping.class);
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : requestMappingHandlerMapping.getHandlerMethods().entrySet()) {
            RequestMappingInfo key = entry.getKey();
            for (String path : key.getPatternsCondition().getPatterns()) {
                try {
                    UrlPathDic urlPathDic = new UrlPathDic();
                    urlPathDic.setPath(path);
                    urlPathDic.setPathName(path);

                    urlPathDicMapper.insertOneUrlPathDic(urlPathDic);
                } catch (DuplicateKeyException e) {
                }

                try {
                    Permission permission = new Permission();
                    permission.setPath(path);
                    permission.setPermissionName(path);
                    permissionMapper.insertOnePermission(permission);
                } catch (DuplicateKeyException e) {
                }
            }
        }

    }
}
