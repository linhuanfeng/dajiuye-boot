import {
    request
} from "../../requests/index.js";
var app = getApp() // 获取全局app对象
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {},
        allMessage: [],
        toOpenid: '',
        notice: '同学请注意了，大就业招聘火热进行中，点击了解详情',
    },
    Authorization: {
        token: 'asda'
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.Authorization.token = app.globalData.token
        var that = this
        app.globalData.refreshCallback = function () {
            app.globalData.socketClient.subscribe('/topic/public', function (message) {
                console.log("接受到全局消息")
                console.log(message)
                that.setData({
                    notice: message.body
                })
                message.ack() // 确认
            })
        }
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        const userInfo = wx.getStorageSync('userInfo')
        if (!userInfo) {
            wx.switchTab({
                url: '../user/index', //注意switchTab只能跳转到带有tab的页面，不能跳转到不带tab的页面
            })
            wx.showToast({
                title: '请先登录',
                icon: 'none',
                image: '',
                duration: 1000,
                mask: false,
            });
            return;
        }
        this.setData({
            userInfo
        })
        this.setAllMessage(userInfo.openId)
    },
    onHide: function () {},
    // 初始化所有的消息
    async setAllMessage(openId) {
        var that = this
        const result = await request({
            url: "/message/message/getAllMessage",
            data: {
                openId
            },
            header: that.Authorization
        });
        that.setData({
            allMessage: result
        })
    },
    // 跳转到具体的聊天页面
    setList(e) {
        console.log("message=>setList")
        const targetMsgs = this.data.allMessage[e.currentTarget.dataset.msgs]
        wx.setStorageSync('msgs', targetMsgs)
        const toUserOpneId = targetMsgs[0].fromOpenId == this.data.userInfo.fromOpenId ? targetMsgs[0].fromOpenId : targetMsgs[0].toOpenId
        wx.navigateTo({ //---wx.  传当前页面的数据到另外一个页面！！！
            url: "/pages/dialog/index?toUserOpneId=" + toUserOpneId
        })
    }
})