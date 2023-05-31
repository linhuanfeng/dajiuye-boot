// pages/resume.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 存放解密得到的数据
        userInfo: {},
        hasUserInfo: false,
        // 登录login授权后获得的凭证code,可用于获取解密
        loginCode: {},
        canIUse: wx.canIUse('button.open-type.getUserInfo')
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {

    },
    onShow: function() {
        var that = this;
        wx.login({ //login流程
            success: function(res) { //登录成功
                console.log("登录成功")
                console.log(res);
                if (res.code) {
                    that.setData({
                        loginCode: res.code
                    })
                } else {
                    console.log('获取用户登录态失败！' + res.errMsg)
                }
            }
        });
    },
    getUserProfile(e) {
        // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
        // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
        wx.getUserProfile({
            desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
            success: (res) => {
                this.setData({
                    userInfo: res.userInfo,
                    hasUserInfo: true
                })
            }
        })
    },
    getSetting() {
        // 查看是否授权
        wx.getSetting({
            success(res) {
                if (res.authSetting['scope.userInfo']) {
                    // 已经授权，可以直接调用 getUserInfo 获取头像昵称
                    wx.getUserInfo({
                        success(res) {
                            console.log(res.userInfo)
                            wx.request({
                                url: url,
                                data: {
                                    'iv': res.iv,
                                    'encryptedData': res.encryptedData,
                                    'login_key': 登入标识
                                },
                                method: "POST",
                                header: {
                                    'content-type': 'application/json' // 默认值
                                },
                                success: function(res) {
                                    //解密后数据
                                    console.log(res)
                                }
                            });
                        }
                    })
                }
            }
        })
    },
    checkSession() {
        wx.checkSession({
            success: (res) => {
                console.log(res)
            },
            fail: (res) => {

            }
        })
    },
    handleGetPhoneNumber(e) {
        console.log("点击了获取手机号")
            // console.log(e);
        var that = this
        wx.login({
                success: function(res) {
                    console.log("login success")
                        // console.log(res)
                    if (res.code) {
                        that.setData({
                            loginCode: res.code
                        })
                        var code = res.code;
                        // console.log("code:")
                        // console.log(code)
                        wx.getUserInfo({ //getUserInfo流程
                            success: function(res2) { //获取userinfo成功
                                console.log("获取userinfo成功")
                                    // console.log(res2);
                                    // var encryptedData = encodeURIComponent(res2.encryptedData); //一定要把加密串转成URI编码
                                    //请求自己的服务器
                                that.getMegFromServer(code, e.detail.encryptedData, e.detail.iv);
                                // that.getMegFromServer(code, encryptedData, iv);
                            }
                        })
                    } else {
                        console.log('获取用户登录态失败！' + res.errMsg)
                    }
                }
            })
            // wx.login({
            //   timeout: 0,
            // })
        this.getMegFromServer(this.data.loginCode, e.detail.encryptedData, e.detail.iv)
            // const { userInfo } = e.detail;
            // wx.setStorageSync("userInfo", userInfo);
            // wx.navigateBack({
            //     delta: 1
            // });
    },

    handleLogin(e) {
        console.log("进来了微信登录")
        var that = this;
        // 不是 wx.login 和 wx.getUserProfile 的调用顺序问题，而是 wx.getUserProfile 不允许自动触发，必须通过用户点击触发
        wx.getUserProfile({
            desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
            success: (res) => {
                console.log(res)
                this.setData({
                        userInfo: res.userInfo,
                        hasUserInfo: true
                    })
                    // 请求自己的服务器进行解密
                    // var encryptedData = encodeURIComponent(res2.encryptedData); //一定要把加密串转成URI编码
                that.getMegFromServer(that.data.loginCode, res.encryptedData, res.iv);
            },
            fail: function(res2) {
                console.log("获取userinfo失败")
            }
        })
    },
    async getMegFromServer(code, encryptedData, iv) {
        const result = await request({ url: "/handleWXMsg", data: { code, encryptedData, iv }, header: { 'content-type': 'application/json' } });
        this.setData({
                userInfo: result
            })
    },
    userexit(e) {
      wx.showModal({
        content: '确定要退出大就业吗？',
        success (res) {
          if (res.confirm) {
            wx.removeStorage({key:"userInfo"})
            // wx.clearStorage({
            //   success: (res) => {

            //   },
            // })
            wx.switchTab({
              url: '../user/index',
            })
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    }
})