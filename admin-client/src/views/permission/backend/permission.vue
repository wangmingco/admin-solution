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

export default {
  data() {
    return {
    }
  },
  computed: {
    permissions() {
      return this.$store.getters.backendPermissions
    }
  },
  methods: {
    handleEdit(index, row) {

    },
    handleDelete(index, row) {

    }
  }
}
</script>

