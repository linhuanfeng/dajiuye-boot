import { request } from "../../requests/index.js";
var app=getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {},
        toUserInfo:{},
        toUserOpenId:'', // 聊天对面的用户openid
        msgs: [], // 全部消息
        leftMsgs:[],
        rightMsgs:[],
        inputValue:''
    },
    message:{
        fromOpenId : '111', //发送方
        toOpenId : '111', // 接收方
        content : '66666666', // 内容
        type : '1' //1.文本消息 2.图片
    },
    // 保存信息的参数
    QueryParams: {
        fromOpenId:"",
        toOpenId:"",
        news:""
    },
    curSocketClient:{},
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        const toUserOpenId=options.toUserOpneId
        const userInfo=wx.getStorageSync('userInfo')
        this.setData({
            userInfo:userInfo,
            toUserOpenId
        })
        this.message.fromOpenId=this.data.userInfo.openId
        this.message.toOpenId=this.data.toUserOpenId
    },
    onShow: function() {
        const msgs=wx.getStorageSync('msgs')
        this.setData({
            msgs:msgs
        })
        this.setToUSerInfo()
        this.dealMsg()
        this.receiveMsg()
    },
    onUnload:function(){
        // 不要占用连接，不然页面每show一次就订阅了一次
        this.curSocketClient.unsubscribe()
    },
    receiveMsg(){
        var that=this
        this.curSocketClient=app.globalData.socketClient.subscribe('/user/' + app.globalData.openid + '/private', function (message, headers) {
            const json=JSON.parse(message.body)
            if(json.fromOpenId===that.data.toUserOpneId){
                let leftMsgs=that.data.leftMsgs
                leftMsgs.push(json.content)
                that.setData({
                    leftMsgs
                })
            }
            console.log('收到对面的来信:', json.fromOpenId);
            message.ack();
          });
    },
    setToUSerInfo(){
        const msgs=this.data.msgs
        let toUserInfo={}
        if(msgs[0].fromOpenId===this.data.userInfo.openId){
            toUserInfo.openId=msgs[0].toOpenId,
            toUserInfo.avatar=msgs[0].toAvatar,
            toUserInfo.userName=msgs[0].toName
        }else{
            toUserInfo.openId=msgs[0].fromOpenId,
            toUserInfo.avator=msgs[0].fromAvatar,
            toUserInfo.userName=msgs[0].fromName
        }
        this.setData({
            toUserInfo
        })
    },
    // 把消息分成左右两个队列
    dealMsg(){
        const msgs=this.data.msgs
        let leftMsgs=[]
        let rightMsgs=[]
        for (let i = 0; i < msgs.length; i++) {
            if(msgs[i].fromOpenId===this.data.userInfo.openId){
                // 自己的消息
                rightMsgs.push(msgs[i].news)
            }else{
                leftMsgs.push(msgs[i].news)
            }
        }
        this.setData({
            leftMsgs,
            rightMsgs
        })
    },
    // 发送消息
    submit(e){
        this.message.content=e.detail.value.msg
        app.sendSocketMessage(this.message)
        console.log("点击发送")
        console.log(e.detail.value.msg)
        let rightMsgs=this.data.rightMsgs
        rightMsgs.push(e.detail.value.msg)
        this.setData({
            rightMsgs
        })
        this.setData({
            inputValue:''
        })
    },

    // 发送消息
    async sendMessage(fromOpenId,toOpenId,news) {
        const result = await request({ url: "/own/user/saveMessage", data: {fromOpenId,toOpenId,news},method: 'POST', header: { "Content-Type": "application/json" } });
        console.log("发送消息了")
    }
})