import request from '@/utils/request'

// 查询药品信息列表
export function listDrug(query) {
  return request({
    url: '/pill/drug/list',
    method: 'get',
    params: query
  })
}

// 查询药品信息详细
export function getDrug(drugId) {
  return request({
    url: '/pill/drug/' + drugId,
    method: 'get'
  })
}

// 新增药品信息
export function addDrug(data) {
  return request({
    url: '/pill/drug',
    method: 'post',
    data: data
  })
}

// 修改药品信息
export function updateDrug(data) {
  return request({
    url: '/pill/drug',
    method: 'put',
    data: data
  })
}

// 删除药品信息
export function delDrug(drugId) {
  return request({
    url: '/pill/drug/' + drugId,
    method: 'delete'
  })
}
