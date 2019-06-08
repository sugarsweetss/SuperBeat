// components/index/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    avatarUrl:{
      type: String,
      value: '',
    },
    subkey:{
      type: String,
      value: '',
    },
    firstTime:{
      type: Boolean,
      value: false,
    },
    userChoice:{
      type: String,
      value: 'white',
      observer: function (newVal, oldVal){
        var that = this
        var userChoice = newVal
        //设置头像框边缘颜色
        if (userChoice === 'green') {
          that.setData({
            userBorder: "rgba(0, 255, 200, 0.8)",
            logo: '../../image/logo_green.png',
            location: '../../image/location_green.png',
          })
        }
        else if (userChoice === 'blue') {
          that.setData({
            userBorder: "rgba(0, 200, 255, 0.8)",
            logo: '../../image/logo_blue.png',
            location: '../../image/location_blue.png',
          })
        }
      }
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    timestamp: 0,
    //映射表
    f1: {},
    f2: {},
    //地图信息
    map: {
      //地图用户信息
      userInfo: {
        longitude: 38.91377,
        latitude: 31.614565,
        //用户视野范围
        region: {
          southwest: {
            longitude: 0,
            latitude: 0,
          },
          northeast: {
            longitude: 0,
            latitude: 0,
          },
        }
      },
      //地图临时信息
      mapInfo:{},
      //地图内容信息
      subkey: null,
      markers: [],
      circles: [],
      scale: 0,
      init: true,
    },
    regionChanged: false,
    //用户头像边框颜色
    userBorder: "rgba(255, 255, 255, 0.8)",
    logo: undefined,
    location: undefined,
  },
  observers:{
    //当将视野变化置为true则设置当前位置经纬度
    'regionChanged': function(regionChanged){
      console.log('observer regionChanged begin')
      if (!regionChanged)
        return
      var that = this
      that.setData({
        regionChange: false
      })
      //视野变化
      //设置当前位置的经纬度
      if (!that.data.map.init){
        that._setLocation()
      }
      that._setRegion()
      that._connectToServer()
      console.log('observer regionChanged end')
    },
    //当mapInfo改变时，根据图信息绘制
    'map.mapInfo': function(mapInfo){
      console.log('observer mapInfo begin')
      var that = this
      that._printMap(mapInfo, that.data.map.init)
      console.log('observer mapInfo end')
    },
    //当缩放等级变化的时候重新设置一遍所有数据
    'map.scale':function(scale){
      console.log('observer scale begin')
      var that = this
      that.setData({
        'map.circles': that.data.map.circles,
      })
      console.log('observer scale end')
    }
  },
  lifetimes:{
    attached: function () {
      console.log('attached begin')
      var that = this
      that._setLocation()
      console.log(that.data.subkey)
      console.log('attached end')
    },
    //页面渲染过程中，获取mapCtx，初始设置
    ready: function () {
      console.log('ready begin')
      var that = this
      that.mapCtx = wx.createMapContext('usermap')
      //当移动到当前位置时因为视野发生变化自动调用regionChange函数刷新当前地图
      //显示提示教程
      if (that.properties.firstTime) {
        wx.showModal({
          title: '使用提示',
          content: '←左侧按钮：用户界面\r\n↓中间按钮：攻击当前所在圈\r\n→右侧按钮：回到当前位置',
          showCancel: false,
        })
        that.setData({
          firstTime: false
        })
      }
      console.log('ready end')
    },
  },
  /**
   * 组件的方法列表
   */
  methods: {
    //设置当前缩放等级
    _setScale: function(){
      console.log('setScale begin')
      var that = this
      that.mapCtx.getScale({
        success: function(res){
          that.setData({
            'map.scale': res.scale,
          })
        },
      })
      console.log('setScale end')
    },
    //设置当前位置经纬度
    _setLocation: function(){
      console.log('setLocation begin')
      var that = this
      wx.getLocation({
        type: 'gcj02',
        altitude: true,
        success: function (res) {
          that.setData({
            'map.userInfo.latitude': res.latitude,
            'map.userInfo.longitude': res.longitude,
          })
        },
      })
      console.log('setLocation end')
    },
    //设置当前视野范围（东北-西南）
    _setRegion: function(){
      var that = this
      that.mapCtx.getRegion({
        success: function (res) {
          region.northeast = res.northeast
          region.southwest = res.southwest
          that.setData({
            'map.userInfo.region.southwest': region.southwest,
            'map.userInfo.region.northeast': region.northeast,
          })
        }
      })
    },
    //右侧按钮，视野返回到当前位置
    _moveToLocation: function () {
      console.log('moveToLocation begin')
      this._setLocation()
      this._setScale()
      console.log('moveToLocation end')
    },
    //中间按键beat功能
    _beat: function () {
      var that = this
      console.log('beat begin')
      wx.showLoading({
        title: 'BEATING...',
      })
      that.setData({
        'map.init': false,
        regionChanged: true,
      })
      console.log('beat end')
    },
    //跳转到用户信息界面
    toUserPage: function () {
      console.log('toUserPage begin')
      wx.navigateTo({
        url: '../userpage/userpage',
      })
      console.log('toUserPage end')
    },
    //向服务器发送指定信息，设置map.mapInfo数据
    _connectToServer: function () {
      console.log('connectToServer begin')
      var circles = []
      var markers = []
      var raw_markers = []
      var that = this
      var info = that.data.map.userInfo
      wx.request({
        // url: 'http://localhost:8080/map',
        url: 'http://10.6.67.183:8080/ssm/map/',
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        data: {
          init: that.data.map.init,
          sessionId: wx.getStorageSync('sessionId'),
          latitude: info.latitude,
          longitude: info.longitude,
          swlongitude: info.region.southwest.longitude,
          swlatitude: info.region.southwest.latitude,
          nelatitude: info.region.northeast.latitude,
          nelongitude: info.region.northeast.longitude,
        },
        success(res) {
          // console.log(res.data)
          raw_markers = res.data
          // console.log('raw_markers:', raw_markers)
          for (let i = 0; i < raw_markers.length; i++) {
            let raw_marker = raw_markers[i]
            let id = raw_marker['id']
            let longitude = raw_marker['longitude']
            let latitude = raw_marker['latitude']
            let content = raw_marker['content']
            let color = raw_marker['color'] == 'white' ? '#ffffff' : raw_marker['color'] == 'blue' ? '#00c8ff' : '#00ffc8'
            let marker = {}
            let circle = {}
            let callout = {}
            circle['id'] = id
            marker['id'] = id
            circle['longitude'] = longitude
            marker['longitude'] = longitude
            circle['latitude'] = latitude
            marker['latitude'] = latitude
            circle['color'] = color
            circle['fillColor'] = color + '30'
            circle['radius'] = Number(content) + 20
            circles[i] = circle
            marker['id'] = id
            marker['iconPath'] = color == '#ffffff' ? '../../image/setpoint_white.png' : color == '#00c8ff' ? '../../image/setpoint_blue.png' : '../../image/setpoint_green.png'
            callout['content'] = '' + content
            callout['bgColor'] = color + '90'
            callout['color'] = color
            marker['callout'] = callout
            markers[i] = marker
          }
          // console.log({ 'circles': circles, 'markers': markers })
          that.setData({
            'map.mapInfo': {'circles': circles, 'markers': markers }
          })
        },
        fail() {
          wx.hideLoading()
          wx.showModal({
            title: 'CONNECTION FAILED',
            content: '未连接到服务器',
            showCancel: false,
          })
        },
      })
      console.log('get map info end')
    },
    //设置图中markers和circles数据
    _printMap: function (mapInfo, init) {
      console.log('prinMap begin')
      var that = this
      var change = false
      var circles = mapInfo['circles']
      var markers = mapInfo['markers']
      var tmp_info = {}
      var tmp_id = undefined
      console.log('print circles')
      for (let i = 0; i < circles.length; i++) {
        let circle = circles[i]
        let id = circle.id
        let fid = 0
        if (that.data.f1[id] == undefined) {
          let tmp_key = 'f1[' + id + ']'
          that.setData({
            [tmp_key]: Object.keys(that.data.f1).length
          })
        }
        else if (init)
          continue
        fid = that.data.f1[id]
        tmp_id = 'map.circles[' + fid + ']'
        if (that.data.map.circles[fid] == undefined) {
          tmp_info = {
            id: id,
            latitude: 0,
            longitude: 0,
            color: 0,
            fillColor: 0,
            radius: 0,
          }
        }
        else {
          tmp_info = that.data.map.circles[fid]
        }
        for (let j in circle) {
          if (j == undefined)
            continue
          tmp_info[j] = circle[j]
        }
        that.setData({
          [tmp_id]: tmp_info
        })
      }
      // console.log(this.data.map.circles)
      console.log('print markers')
      for (let i = 0; i < markers.length; i++) {
        let marker = markers[i]
        let id = marker.id
        let fid = 0
        if (that.data.f2[id] == undefined) {
          let tmp_key = 'f2[' + id + ']'
          that.setData({
            [tmp_key]: Object.keys(that.data.f2).length
          })
        }
        else if (init)
          continue
        fid = that.data.f2[id]
        tmp_id = 'map.markers[' + fid + ']'
        if (that.data.map.markers[fid] == undefined) {
          tmp_info = {
            id: id,
            iconPath: 0,
            longitude: 0,
            latitude: 0,
            width: 30,
            height: 30,
            callout: {
              content: -1,
              fontSize: 14,
              color: '#000000',//改为白色ffffff
              bgColor: 0,
              padding: 8,
              borderRadius: 15,
            }
          }
        }
        else {
          tmp_info = that.data.map.markers[fid]
        }
        for (let j in marker) {
          if (j == undefined)
            continue
          if (tmp_info[j].content != marker[j].content)
            change = true
          if (j == 'callout') {
            for (let k in marker[j]) {
              if (k == undefined)
                continue
              tmp_info['callout'][k] = marker[j][k]
            }
          }
          else {
            tmp_info[j] = marker[j]
          }
        }
        that.setData({
          [tmp_id]: tmp_info
        })
      }
      // console.log(that.data.map.circles, that.data.map.markers)
      if (!init){
        wx.hideLoading()
        if (!change) {
          wx.showModal({
            title: 'BEAT FAILED',
            content: '您未在范围内或未满点击周期',
            showCancel: false,
          })
        }
        else {
          wx.showToast({
            title: 'BEAT SUCCESS!',
          })
        }
      }
      else{
        that._setScale()
      }
      console.log('print map end')
    },
    //当视野变化的时候动态的请求新视野下的markers和circles信息
    regionChange: function () {
      console.log('region change begin')
      var that = this
      if (that.data.timestamp == 0) {
        that.setData({
          'timestamp': Date.parse(new Date()) / 1000,
        })
        return
      }
      if (Date.parse(new Date()) / 1000 - that.data.timestamp < 1) {
        return
      }
      else {
        that.setData({
          'timestamp': Date.parse(new Date()) / 1000,
        })
      }
      that.setData({
        'map.init': true,
        regionChanged: true,
      })
      console.log('region change end')
    },
  }
})
