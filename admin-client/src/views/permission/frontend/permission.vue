<template>
  <div class="app-container">
     <el-table
      :data="permissions"
      style="width: 100%"
      max-height="1250"
      border>
      <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
      <el-table-column prop="path" label="path"></el-table-column>
      <el-table-column prop="name" label="name"></el-table-column>
      <el-table-column prop="component" label="component"></el-table-column>
      <el-table-column prop="title" label="title"></el-table-column>
      <el-table-column prop="parrent" label="parrent"></el-table-column>
      <el-table-column prop="icon" label="icon"></el-table-column>
      <el-table-column prop="redirect" label="redirect"></el-table-column>
      <!-- <el-table-column prop="status" label="status"></el-table-column> -->
    
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

  </div>
</template>

<script>
import {getFrontendPermissions } from '@/api/authority'

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
      getFrontendPermissions().then(response => {
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

