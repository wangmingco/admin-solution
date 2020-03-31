package co.wangming.adminserver.vo.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created By WangMing On 2020-03-30
 **/
public class GetUserBackendPermissionsResponse {

    private Collection<RouteNode> routeNodes;

    public Collection<RouteNode> getRouteNodes() {
        return routeNodes;
    }

    public void setRouteNodes(Collection<RouteNode> routeNodes) {
        this.routeNodes = routeNodes;
    }

    public static class RouteNode {

        private String name;
        private String path;
        private String component;
        private String redirect;
        private Meta meta;

        private List<RouteNode> children = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }

        public String getRedirect() {
            return redirect;
        }

        public void setRedirect(String redirect) {
            this.redirect = redirect;
        }

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

        public void setMeta(String icon, String title) {
            this.meta = new Meta();
            this.meta.setIcon(icon);
            this.meta.setTitle(title);
        }

        public List<RouteNode> getChildren() {
            return children;
        }

        public void setChildren(List<RouteNode> children) {
            this.children = children;
        }
    }

    public static class Meta {
        private String title;
        private String icon;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
