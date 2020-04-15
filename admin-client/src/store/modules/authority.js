import { getUsers, getRoles, getBackendPermissions, getFrontendPermissions } from '@/api/authority'

const getDefaultState = () => {
    return {
      users: [],
      roles: [],
      backendPermissions: [],
      frontendPermissions: []
    }
  }
  
  const state = getDefaultState()

  const mutations = {
      RESET_STATE: (state) => {
        Object.assign(state, getDefaultState())
      },
      SET_UERS: (state, users) => {
        state.users = users
      },
      SET_ROLES: (state, roles) => {
        state.roles = roles
      },
      SET_BACKEND_PERMISSIONS: (state, backendPermissions) => {
        state.backendPermissions = backendPermissions
      },
      SET_FRONTEND_PERMISSIONS: (state, frontendPermissions) => {
        state.frontendPermissions = frontendPermissions
      }
  }

  const actions = {
    getUsersAction({commit}) {
        return new Promise((resolve, reject) => {
            getUsers().then(response => {
                commit('SET_UERS', response.data.users.dataList)
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },
    getRolesAction({commit}) {
        if(state.roles && state.roles.length > 0) {

        } else {
            return new Promise((resolve, reject) => {
                getRoles().then(response => {
                    commit('SET_ROLES', response.data.roles.dataList)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        }
        
    },
    getBackendPermissionsAction({commit}) {
        if(state.backendPermisions && state.backendPermisions.length > 0) {

        } else {
            return new Promise((resolve, reject) => {
                getBackendPermissions().then(response => {
                    commit('SET_BACKEND_PERMISSIONS', response.data.permissions.dataList)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        }
      
    },
    getFrontendPermissionsAction({commit}) {
        if(state.frontendPermissions && state.frontendPermissions.length > 0) {

        } else {
            return new Promise((resolve, reject) => {
                getFrontendPermissions().then(response => {
                    commit('SET_FRONTEND_PERMISSIONS', response.data.permissions.dataList)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        }
    },

    resetState({ commit }) {
        return new Promise(resolve => {
            commit('RESET_STATE')
            resolve()
        })
    }
  }

  export default {
    // namespaced: true,
    state,
    mutations,
    actions
  }