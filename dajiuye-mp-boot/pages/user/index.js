import {
    request
} from "../../requests/index.js";
// Pages/user/index.js
var app=getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {},
        sendJobsNums: "99+",
        collectNums: 0,
        expectCities: 0,
        expectJobs: 0,
        // 用户登录凭证
        loginCode: {}
    },
    // code和iv可解密用户信息encryptedData
    LoginInfo: {
        code: '',
        encryptedData: '',
        iv: ''
    },
    Authorization: {
        token: 'asda'
    },
    
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        // 1.获取缓存数据，初始化页面,包括userInfo，hasSendJobs，collect，expectCities，expectJobs
        const userInfo = wx.getStorageSync("userInfo");
        const collect = wx.getStorageSync("collects") || [];
        const expectCities = wx.getStorageSync("expectCities") || [];
        const expectJobs = wx.getStorageSync("expectJobs") || [];
        this.setData({
            userInfo,
            collectNums: collect.length,
            expectCities: expectCities.length,
            expectJobs: expectJobs.length
        })
        // 2.wx.login获取code
        var that = this;
        wx.login({ //login流程
            success: function (res) { //登录成功
                console.log(res);
                if (res.code) {
                    console.log("成功==>wx.login==>获取登录凭证code")
                    that.setData({
                        loginCode: res.code
                    })
                } else {
                    console.log('失败==>wx.login==>获取登录凭证code' + res.errMsg)
                }
            }
        });
        this.Authorization.token = app.globalData.token
    },
    // 1.点击登录
    getUserProfile(e) {
        // 1.1.wx.getUserProfile获取加密用户信息encryptedData
        var that = this;
        wx.getUserProfile({
            desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
            success: (res) => {
                console.log(" 成功==>wx.getUserProfile==>获取初始的userInfo")
                console.log(res)
                this.setData({
                        userInfo: res.userInfo,
                        hasUserInfo: true
                    }),
                    console.log(that.LoginInfo)
                    // 1.2.根据code和iv解密用户信息encryptedData
                    that.LoginInfo.code = that.data.loginCode
                that.LoginInfo.encryptedData = res.encryptedData
                that.LoginInfo.iv = res.iv
                console.log(that.LoginInfo)
                that.getMegFromServer()
            },
            fail: function (res2) {
                console.log("失败==>wx.getUserProfile==>获取初始的userInfo")
            }
        })
    },
    // 3.设置resumeUrl
    async setUserResume() {
        const resumeUrl=this.data.userInfo.resume
        this.setData({
            resumeUrl,
            resumeTime: resumeUrl.substring(resumeUrl.lastIndexOf('_') + 1, resumeUrl.lastIndexOf('-')),
            resumeName: resumeUrl.substring(resumeUrl.lastIndexOf('-') + 1)
        })
        wx.setStorageSync('resumeUrl', resumeUrl)
    },
    // 2.根据code和iv解密用户信息encryptedData
    async getMegFromServer() {
        console.log("loginInof==>")
        console.log(this.LoginInfo)
        var that=this
        const result = await request({
            url: "/user/wx/handleWXMsg",
            data: this.LoginInfo,
            method: 'POST',
            header: {
                "Content-Type": "application/x-www-form-urlencoded",
                "token":that.Authorization.token
            }
        });
        console.log("handleWXMsg==>结果")
        console.log(result)
        this.setData({
            userInfo: result.data
        })

        // 设置用户简历
        this.setUserResume(this.data.userInfo.myEncrypt)
        // 将用户信息放入缓存
        wx.setStorageSync("userInfo", this.data.userInfo);

        app.globalData.uri= app.globalData.uri+this.data.userInfo.openId
    }
})