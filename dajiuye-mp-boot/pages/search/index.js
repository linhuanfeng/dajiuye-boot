import {
    request
} from "../../requests/index.js";
var app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        goods: [],
        // 取消 按钮 是否显示
        isFocus: false,
        // 输入框的值
        inValue: ""
    },
    header: {
        token: 'asda'
    },
    // 分页需要的参数
    QueryParams: {
        query: "",
        cid: "",
        pageNo: 1,
        pageSize: 10,
        // 职位类型
        jobType: -1,
        jobAge:"",
        jobSalary:""
    },
    TimeId: -1,
    onLoad: function(options) {
        this.header.token = app.globalData.token
    },
    // 输入框的值打印就会触发的事件
    handleInput(e) {
        // 1 获取输入框的值
        const {
            value
        } = e.detail;
        // 2 检测合法性
        if (!value.trim()) {
            this.setData({
                goods: [],
                isFocus: false
            })
            // 不合法
            return
        }
        // 3 准备发送请求获取数据
        this.QueryParams.query = value;
        this.setData({
            isFocus: true
        })
        clearTimeout(this.TimeId);
        this.TimeId = setTimeout(() => {
            this.qsearch();
        }, 1000);
    },
    async qsearch() {
        var that=this
        const res = await request({
            url: "/job/job/jobsByEs",
            data: this.QueryParams,
            header:that.header
        });
        console.log(res);
        this.setData({
            goods: res
        })
    },
    // 点击取消
    handelCancel() {
        this.setData({
            inpValue: "",
            isFocus: false,
            goods: []
        })
    }
})