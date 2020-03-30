<template>
  <div class="app-container">
     <el-table
      :data="permissions"
      style="width: 100%"
      max-height="1250">
      <el-table-column
        fixed
        prop="id"
        label="id"
        width="120">
      </el-table-column>
      <el-table-column
        prop="path"
        label="路径"
        width="400">
      </el-table-column>
      <el-table-column
        prop="permissionName"
        label="权限名称"
        width="400">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

  </div>
</template>

<script>
import {getBackendPermissions } from '@/api/authority'

export default {
  data() {
    return {
      permissions: []
    }
  },
  computed: {
    // permissions() {
    //   var permissions = this.$store.getters.permissions
    //   if(permissions) {
    //     console.log("set permissions", permissions)
    //     return permissions.dataList
    //   } else {
    //     return []
    //   }
    // }
  },
  created() {
    // this.$store.dispatch('getPermissionsAction')
    this.initPermissions()
  },
  methods: {
    initPermissions() {
      getBackendPermissions().then(response => {
        this.permissions = response.data.permissions.dataList
      })
    },
    handleEdit(index, row) {

    },
    handleDelete(index, row) {

    }
  }
}
</script>

