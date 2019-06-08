const app = getApp()
Page({
  data:{

  },
  //注销用户信息
  destroy: function(){
    //向服务器发送消除该用户信息的请求
    console.log('destroy begin')
    var make_sure = false
    wx.showModal({
      title: '注销',
      content: '您确定注销当前用户信息？\r\n若注销您在一个星期之内将无法再次注销',
      success(res){
        make_sure = res.confirm
        if (res.confirm == true && make_sure){
          wx.showLoading({
            title: '注销中',
          })
          wx.request({
            // url: 'http://localhost:8080/logout',
            url: 'http://192.168.0.132:8080/ssm/logout/',
            method: 'POST',
            header: {
              'content-type': 'application/x-www-form-urlencoded'
            },
            data: {
              sessionId: wx.getStorageSync('sessionId'),
              destroy: 'destroy'
            },
            success(res) {
              console.log(res)
              if (res.data.status == 'success') {
                wx.showToast({
                  title: '已注销',
                })
                wx.clearStorage()
                wx.reLaunch({
                  url: '../lead/lead',
                })
              }
              else {
                wx.showModal({
                  title: '注销失败',
                  content: '注销周期未满一个星期\r\n无法注销',
                  showCancel: false,
                  complete(){
                    return
                  }
                })
              }
            },
            fail(){
              wx.showModal({
                title: '注销失败',
                content: '网路连接失败\r\n请检查网络连接状态',
                showCancel: false,
                success(res){
                  return
                }
              })
            },
            complete(){
              wx.hideLoading()
            }
          })
        }
      }
    })
    console.log('destroy end')
  },
  //显示开发者信息
  getApeInfo: function(){
    wx.showModal({
      title: '关于我们',
      content: '团队名称：\r\n豆浆油条\r\n团队成员：\r\nZiUNO+Charon+小明同学+白',
      showCancel: false
    })
  },
  showUsage(){
    wx.showModal({
      title: '使用提示',
      content: '←左侧按钮：用户界面\r\n↓中间按钮：攻击当前所在圈\r\n右侧按钮：定位',
      showCancel: false,
    })
  },
  admire(){
    console.log('admire')
    wx.navigateToMiniProgram({
      appId: 'wx18a2ac992306a5a4',
      path: 'pages/apps/largess/detail?id=pc6tu0GXPFo%3D',
    })
  }
}
)