const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  permissionRoutes: state => state.permission.routes,
  permissionAddRoutes: state => state.permission.addRoutes,
  name: state => state.user.name,
  users: state => state.authority.users,
  roles: state => state.authority.roles,
  backendPermissions: state => state.authority.backendPermissions,
  frontendPermissions: state => state.authority.frontendPermissions
}
export default getters
