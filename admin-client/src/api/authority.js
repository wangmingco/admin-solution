import request from '@/utils/request'

export function getUsers(data) {
  return request({
    url: '/api/user/authority/getUsers',
    method: 'get',
    data
  })
}

export function getRoles() {
  return request({
    url: '/api/user/authority/getRoles',
    method: 'get'
  })
}

export function getRolesByUserId(params) {
  return request({
    url: '/api/user/authority/getRolesByUserId',
    method: 'get',
    params
  })
}

export function getPermissions() {
  return request({
    url: '/api/user/authority/getPermissions',
    method: 'get'
  })
}

export function getPermissionsByRoleId(params) {
  return request({
    url: '/api/user/authority/getPermissionsByRoleId',
    method: 'get',
    params
  })
}

export function updateRolePermission(params) {
  return request({
    url: '/api/user/authority/updateRolePermission',
    method: 'post',
    data: params
  })
}

export function updateUserRole(params) {
  return request({
    url: '/api/user/authority/updateUserRole',
    method: 'post',
    data: params
  })
}