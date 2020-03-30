package co.wangming.adminserver.vo.auth;

import co.wangming.adminserver.model.auth.User;
import co.wangming.adminserver.vo.TableData;

import java.util.HashMap;
import java.util.List;

/**
 * Created By WangMing On 2020-03-14
 **/
public class GetUsersResponse {

    private TableData users;

    public TableData getUsers() {
        return users;
    }

    public void setUsers(TableData users) {
        this.users = users;
    }

    public static GetUsersResponse build(List<User> allUsers) {

        TableData tableData = TableData.build(allUsers, item -> new HashMap() {{
            put("id", item.getId());
            put("userName", item.getUsername());
        }});

        GetUsersResponse response = new GetUsersResponse();
        response.setUsers(tableData);

        return response;
    }
}
