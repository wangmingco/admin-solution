<template>
  <div class="app-container">
    <el-tabs type="border-card" v-model="activeName">

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
import role from './role'
import permission from './permission'

export default {
  components: { role, permission},
  data() {
    return {
      activeName: 'role'
    }
  },
  created() {
    this.$store.dispatch('getRolesAction')
    this.$store.dispatch('getFrontendPermissionsAction')
  }
}
</script>

