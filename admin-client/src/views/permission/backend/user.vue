<template>
  <div class="app-container">
   
    <el-table
      :data="users"
      style="width: 100%"
      max-height="1250">
      <el-table-column
        fixed
        prop="id"
        label="id"
        width="150">
      </el-table-column>
      <el-table-column
        prop="userName"
        label="用户名称"
        width="620">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="success" @click="addPermission(scope.$index, scope.row)">分配角色</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <el-dialog title="分配角色" :visible.sync="rolesDialogVisible">
       
       <el-checkbox-group v-model="checkedRoles" >
          <el-checkbox v-for="role in roles" :label="role.roleName" :key="role.id" @change="checked=>handleCheckedRoleChange(checked,role.id)">{{role.roleName}}</el-checkbox>
        </el-checkbox-group>

    </el-dialog>

  </div>
</template>

<script>
import { getUsers, getRoles, getRolesByUserId, updateUserRole } from '@/api/authority'

export default {
   data() {
    return {
      rolesDialogVisible: false,
      users: null,
      roles: null,
      checkedRoles: [],
      selectedUser: null
    }
  },
  computed: {
  },
  created() {
    // this.$store.dispatch('getPermissionsAction')
    this.initUsers()
    this.initRoles()
  },
  methods: {
    initUsers() {
      getUsers().then(response => {
        this.users = response.data.users.dataList
      })
    },
    initRoles() {
      getRoles().then(response => {
        this.roles = response.data.roles.dataList
      })
    },
    handleEdit(index, row) {
      this.selectedUser = row
    },
    addPermission(index, row) {
      this.selectedUser = row
      this.rolesDialogVisible = true
      var params = {
        userId: row.id
      }
      getRolesByUserId(params).then(response => {
        this.checkedRoles = response.data.roles.dataList.map(item => item.roleName)
      })

    },
    handleDelete(index, row) {
      
    },
    handleCheckedRoleChange(item,value) {
      
      let params = {
          type: item == true ? 1 : 0,
          roleId: value,
          userId: this.selectedUser.id
      }
      updateUserRole(params).then(response => {
        console.log("updateUserRole", response)
      })
    }
  }
}
</script>

