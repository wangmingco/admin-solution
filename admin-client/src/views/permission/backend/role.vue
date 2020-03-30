<template>
  <div class="app-container">
    <el-table
      :data="roles"
      style="width: 100%"
      max-height="1250">
      <el-table-column
        fixed
        prop="id"
        label="id"
        width="150">
      </el-table-column>
      <el-table-column
        prop="roleName"
        label="角色名称"
        width="620">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="success" @click="addPermission(scope.$index, scope.row)">授权</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

     <el-dialog title="分配权限" :visible.sync="permissionDialogVisible">
       <el-checkbox-group v-model="checkedPermissions" >
         
         <div v-for="permission in permissions">
           <el-checkbox  
            :label="permission.permissionName" 
            :key="permission.id" 
            size="medium"
            @change="checked=> handleCheckedPermissionChange(checked,permission.id)"
            >
              {{permission.permissionName}}
          </el-checkbox>
         </div>
          
        </el-checkbox-group>
    </el-dialog>

  </div>
</template>

<script>
import { getRoles, getBackendPermissions, getBackendPermissionsByRoleId, updateRoleBackendPermission } from '@/api/authority'

export default {
   data() {
    return {
      permissionDialogVisible: false,
      roles: [],
      permissions: [],
      checkedPermissions: [],
      selectedRole: null
    }
  },
  computed: {
  },
  created() {
    // this.$store.dispatch('getPermissionsAction')
    this.initRoles()
    this.initPermissions()
  },
  methods: {
    initRoles() {
      getRoles().then(response => {
        this.roles = response.data.roles.dataList
      })
    },
    initPermissions() {
      getBackendPermissions().then(response => {
        this.permissions = response.data.permissions.dataList
      })
    },
    handleEdit(index, row) {

    },
    addPermission(index, row) {
      this.selectedRole = row
      this.permissionDialogVisible = true
      var params = {
        roleId: row.id
      }
      getBackendPermissionsByRoleId(params).then(response => {
        this.checkedPermissions = response.data.permissions.dataList.map(item => item.permissionName)
      })
    },
    handleDelete(index, row) {

    },
    handleCheckedPermissionChange(item, value) {
      console.log("handleCheckedPermissionChange", value, this.selectedRole)
      let params = {
          type: item == true ? 1 : 0,
          permissionId: value,
          roleId: this.selectedRole.id
      }
      updateBackendRolePermission(params).then(response => {
        console.log("updateRolePermission", response)
      })
    }
  }
}
</script>

