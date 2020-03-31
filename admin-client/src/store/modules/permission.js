import { asyncRoutes, constantRoutes } from '@/router'
import { getUserFrontendPermissions } from '@/api/authority'
import Layout from '@/layout'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      getUserFrontendPermissions().then(response => {
        
        let routeNodes = response.data.routeNodes
        importComponent(routeNodes)
        
        commit('SET_ROUTES', routeNodes)
        resolve(routeNodes)
      })
      
    })
  }
}

function importComponent(routeNodes) {

  for(var rn of routeNodes) {
    if(rn.component == "Layout") {
      rn.component = Layout
    } else {
      let componentPath = rn.component
      rn.component = () => import(`@/views/${componentPath}`)
    }
   
    if(rn.children && rn.children.length > 0) {
      importComponent(rn.children)
    }
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
