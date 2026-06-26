import { logout, getInfo } from '@/api/user'
import { setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import axios from 'axios'

const getDefaultState = () => {
  return {
    // token: getToken(),
    token: {
      roles: ['admin'],
      introduction: 'I am a super administrator',
      avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
      name: 'Super Admin'
    },
    name: 'admin',
    avatar: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}
const token = {
  roles: ['admin'],
  introduction: 'I am a super administrator',
  avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
  name: 'Super Admin'
}
const actions = {
  login({ commit }, userInfo) {
    const params = new URLSearchParams()
    params.append('email', userInfo.username)
    params.append('password', userInfo.password)
    const url = 'http://localhost:8080/user/login'
    console.log('发送登录请求')
    console.log(userInfo)
    // 发送 POST 请求
    return new Promise((resolve, reject) => {
      axios.post(url, params)
        .then(response => {
          // 处理请求成功的响应
          const userId = response.data.data
          if (userId !== null) {
            sessionStorage.setItem('userId', userId)
            sessionStorage.setItem('email', userInfo.username)
            commit('SET_TOKEN', token)
            setToken(token)
            console.log('登录成功')
            console.log(sessionStorage.getItem('email'))
            console.log(sessionStorage.getItem('userId'))
            resolve()
          } else {
            console.log('登录失败')
            reject()
          }
        }).catch(error => {
          console.log('登录错误')
          reject(error)
        })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          return reject('Verification failed, please Login again.')
        }

        const { name, avatar } = data

        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    sessionStorage.clear()
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

