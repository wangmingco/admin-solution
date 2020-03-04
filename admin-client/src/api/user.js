import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/user/auth/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/api/user/auth/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api/user/auth/logout',
    method: 'post'
  })
}

