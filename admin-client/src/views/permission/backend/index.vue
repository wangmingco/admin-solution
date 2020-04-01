<template>
  <div class="app-container">
    <el-tabs type="border-card" v-model="activeName">

      <el-tab-pane label="user" name="user">
        <user></user>
      </el-tab-pane>

       <el-tab-pane label="role" name="role">
        <role></role>
      </el-tab-pane>

      <el-tab-pane label="permission" name="permission">
        <permission></permission>
      </el-tab-pane>

    </el-tabs>

  </div>
</template>

<script>
import user from './user'
import role from './role'
import permission from './permission'

export default {
  components: { user, role, permission},
  data() {
    return {
      activeName: 'user'
    }
  },
  created() {
    this.$store.dispatch('getUsersAction')
    this.$store.dispatch('getRolesAction')
    this.$store.dispatch('getBackendPermissionsAction')
  }
}
</script>

