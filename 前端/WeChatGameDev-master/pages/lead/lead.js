//lead.js
const app = getApp()
Page({
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    plain: true,
    userBoxShadow: "rgba(255, 255, 255, 0.7)",
    begin_image: '../../image/begin_white.svg'
  },
  onLoad() {
    var that = this
    wx.getStorage({
      key: 'userChoice',
      fail(){
        wx.setStorageSync('userChoice', 'white')
      }
    })
    //查看是否登陆
    // wx.getStorage({
    //   key: 'sessionId',
    //   success(res){
    //     that.setColor()
    //   },
    //   fail(){
        wx.login({
          success(res) {
            console.log(res.code)
            if (res.code) {
              wx.request({
                // url: 'http://localhost:8080/login',
                url: 'http://192.168.0.132:8080/ssm/login/',
                method: 'POST',
                header: {
                  'content-type': 'application/x-www-form-urlencoded'
                },
                data: {
                  code: res.code
                },
                success(res) {
                  console.log(res)
                  wx.setStorageSync('sessionId', res.data.sessionId)
                  wx.setStorageSync('userChoice', res.data.userChoice)
                  that.setColor()
                }
              })
            }
            else {
              console.log('登录失败:' + res.errMsg)
            }
          }
        })
    //   }
    // })
  },
  setColor(){
    // wx.setStorageSync('userChoice', 'blue') //-----------------------------------------------------------------------------修改上面success函数后删掉该强制赋值
    // wx.setStorageSync('userChoice', 'green')
    //设置头像边框
    var that = this
    var choice = wx.getStorageSync('userChoice') 
    if (choice == 'blue'){
      that.setData({
        userBoxShadow: "rgba(0, 200, 255, 0.7)",
        begin_image: '../../image/begin_blue.svg',
      })
    }
    else if(choice == 'green'){
      that.setData({
        userBoxShadow: "rgba(0, 255, 200, 0.7)",
        begin_image: '../../image/begin_green.svg'
      })
    }
    //查看是否授权
    if (app.globalData.userInfo != null)
      return
    wx.getSetting({
      success(res) {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称
          wx.getUserInfo({
            success(res) {
              app.globalData.userInfo = res.userInfo
            }
          })
        }
      }
    })
  },
  bindGetUserInfo(e) {
    var loadTitle = 'LOGGING IN'
    if (wx.getStorageSync('userChoice') == 'white')
      loadTitle = 'TO CHOOSE CAMP'
    wx.showLoading({
      title: loadTitle,
    })
    //保存用户详细数据
    if (app.globalData.userInfo == null){
      app.globalData.userInfo = e.detail.userInfo
    }
    //未选择状态进入选择界面
    wx.hideLoading()
    if (wx.getStorageSync('userChoice') == 'white'){
      wx.setStorageSync('firstTime', true)
      wx.redirectTo({
        url: '../choose/choose',
      })
    } 
    else{
      wx.setStorageSync('firstTime', false)
      wx.navigateTo({
        url: '../index/index'
      })
    }
  }
})