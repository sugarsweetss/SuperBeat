// pages/choose/choose.js
Page({
  data: {
    bgColor: 'rgb(36, 49, 66)',
    button: {
      choices: {
        size: 'default',
        blue: {
          title: '幻想者',
          context: '鸟儿会从天空跌落\n四月有时亦会飘雪\n',
          bgColor: 'rgb(0, 200, 255)',
          style: {
            bgColor: 'rgb(36, 49, 66)',
            color: 'rgb(0, 200, 255)'
          }
        },
        green: {
          title: '夜行者',
          context: '与其要问是否正确\n思考错误更加真实\n',
          bgColor: 'rgb(0, 255, 200)',
          style:{
            bgColor: 'rgb(36, 49, 66)',
            color: 'rgb(0, 255, 200)'
          }
        }
      },
      submit: {
        context: 'LOG IN',
        loading: false,
        disable: true,
        color: 'rgba(0, 255, 255, 0.2)',
        borderColor: 'rgba(0, 255, 255, 0.3)'
      },
    },
    choice: undefined
  },
  onLoad: function (options) {
  },
  choose: function(e){
    var that = this
    var id = e.target.id
    if (this.data.choice == undefined || this.data.choice != id){
      if (id == 'blue'){
        that.setData({
          choice: 'blue',
          'button.choices.blue.style.bgColor': this.data.button.choices.blue.bgColor,
          'button.choices.blue.style.color': this.data.bgColor,
          'button.choices.green.style.bgColor': this.data.bgColor,
          'button.choices.green.style.color': this.data.button.choices.green.bgColor
        })
      }
      else{
        that.setData({
          choice: 'green',
          'button.choices.blue.style.bgColor': this.data.bgColor,
          'button.choices.blue.style.color': this.data.button.choices.blue.bgColor,
          'button.choices.green.style.bgColor': this.data.button.choices.green.bgColor,
          'button.choices.green.style.color': this.data.bgColor,
        })
      }
      that.setData({
        'button.submit.color': 'rgb(0, 255, 255)',
        'button.submit.borderColor': 'rgb(0, 255, 255)',
        'button.submit.disable': false
      })
    }
    else{
      that.setData({
        choice: undefined,
        'button.choices.blue.style.bgColor': this.data.bgColor,
        'button.choices.blue.style.color': this.data.button.choices.blue.bgColor,
        'button.choices.green.style.bgColor': this.data.bgColor,
        'button.choices.green.style.color': this.data.button.choices.green.bgColor,
        'button.submit.color': 'rgba(0, 255, 255, 0.3)',
        'button.submit.borderColor': 'rgba(0, 255, 255, 0.3)',
        'button.submit.disable': true
      })
    }
  },
  submit: function(){
    var that = this
    that.setData({
      'button.submit.context': 'LOGGING IN',
      'button.submit.loading': true
    })
    wx.request({
      // url: 'http://localhost:8080/choose',
      url: 'http://192.168.0.132:8080/ssm/choose/',
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {
        sessionId: wx.getStorageSync('sessionId'),
        userChoice: this.data.choice
      },
      success(res) {
        console.log("-----阵营选择成功-------");
      }
    }) 
    wx.setStorageSync('userChoice', this.data.choice)
    wx.redirectTo({
      url: '../index/index',
    })
  }
})