//index.js
//获取应用实例
const app = getApp()
Page({
  data: {
    
  },
  //事件处理函数
  //页面加载中获取初始化坐标
  onLoad: function(){
    console.log('onLoad begin')
    var that = this
    that.setData({
      'avatarUrl': app.globalData.userInfo.avatarUrl,
      'userChoice': wx.getStorageSync('userChoice'),
      'firstTime': wx.getStorageSync('firstTime'),
    })
    console.log('onLoad end')
  },
  onReady: function(e){
    console.log('onReady begin')
    this.index = this.selectComponent('#index')
    console.log('onReady end')
  },
})
