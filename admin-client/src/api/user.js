import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/user/auth/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/api/user/auth/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/api/user/auth/logout',
    method: 'post'
  })
}

