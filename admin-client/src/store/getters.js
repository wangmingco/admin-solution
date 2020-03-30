const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  permission_routes: state => state.permission.routes,
  name: state => state.user.name,
  users: state => state.authority.users,
  roles: state => state.authority.roles,
  permissions: state => state.authority.permissions
}
export default getters
