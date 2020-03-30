import { getUsers, getRoles, getPermissions } from '@/api/authority'

const getDefaultState = () => {
    return {
      users: [],
      roles: [],
      permisions: []
    }
  }
  
  const state = getDefaultState()

  const mutations = {
      SET_UERS: (state, users) => {
        state.users = users
      },
      SET_ROLES: (state, roles) => {
        state.roles = roles
      },
      SET_PERMISSIONS: (state, permisions) => {
        state.permisions = permisions
      }
  }

  const actions = {
    getUsersAction({commit}) {
        return new Promise((resolve, reject) => {
            getUsers().then(response => {
                commit('SET_UERS', response.userNames)
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },
    getRolesAction({commit}) {
        return new Promise((resolve, reject) => {
            getRoles().then(response => {
                commit('SET_ROLES', response.roleNames)
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
        
    },
    getPermissionsAction({commit}) {
        return new Promise((resolve, reject) => {
            getPermissions().then(response => {
                console.log("getPermissions", response.data.permissions)
                commit('SET_PERMISSIONS', response.data.permissions)
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
        
    }
  }

  export default {
    // namespaced: true,
    state,
    mutations,
    actions
  }