//app.js
App({
  onLaunch: function () {
    // 获取系统信息
    var that = this
    wx.getSystemInfo({
      success: function (res) {
        that.globalData.scHeight = res.windowHeight
        that.globalData.scWidth = res.windowWidth
      },
    })
  },
  globalData: {
    userInfo: null,
    scHeight: 0,
    scWidth: 0
  }
})