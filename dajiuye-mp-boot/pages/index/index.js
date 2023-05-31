//0 引入用来发送请求的方法 一定要把路径补全
import {
    request
} from "../../requests/index.js";
var app = getApp()
const constants = require('../../common/constants')
Page({
    data: {
        // 轮播图数组
        swiperList: [],
        //导航数组
        catesList: [],
        //职位数组
        jobList: [],
        tabs: [{
                id: 0,
                value: "校招",
                isActive: false
            },
            {
                id: 1,
                value: "实习",
                isActive: true
            },
            {
                id: 2,
                value: "社招",
                isActive: false
            },
            {
                id: 3,
                value: "全部",
                isActive: false
            }
        ],
    },
    Authorization: {
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
    totalPages: 0,
    //options(Object) 页面开始加载就会触发
    onLoad: function (options) {
        this.setToken();
    },
    doLoadFunc(){
        this.getSwiperList();
        this.getCatesList();
        this.getJobList(0);
    },
    //设置token保证异步拿到token
    setToken() {
        var that=this
        if (!app.globalData.token) {
            wx.login({
                success(res) {
                    // 发起网络请求
                    wx.request({
                        url: constants.API_BASE_URL + "/user/wx/openid",
                        data: {
                            code: res.code
                        },
                        success(res) {
                            that.Authorization.token=res.data.message.token
                            that.doLoadFunc();
                        }
                    })
                }
            })
        }else{
            that.doLoadFunc();
        }
        this.Authorization.token = app.globalData.token
    },
    handleTabsItemChange(e) {
        // 重置
        this.QueryParams.pageNo = 1
        this.totalPages = 1
        // 1 获取被点击的标题索引
        const {
            index
        } = e.detail;
        // // 修改源数组
        let {
            tabs
        } = this.data;
        tabs.forEach((v, i) => i === index ? v.isActive = true : v.isActive = false);
        let type = "";
        if (index == 0) {
            this.QueryParams.jobType = 2
        } else if (index == 1) {
            this.QueryParams.jobType = 1
        } else if (index == 2) {
            this.QueryParams.jobType = 3
        } else {
            this.QueryParams.jobType = "-1"
        }
        //  3 赋值到data中
        this.setData({
            tabs,
            type,
            jobList: []
        })
        this.getJobList(0);
    },
    onReachBottom: function () {
        // 1  判断还有没有下一页数据
        if (this.QueryParams.pageNo >= this.totalPages) {
            // 没有下一页数据
            wx.showToast({
                title: '没有下一页数据了',
                icon: 'none',
                image: '',
                duration: 1500,
                mask: false,
                success: (result) => {

                },
                fail: () => {},
                complete: () => {}
            });

        } else {
            this.QueryParams.pageNo++;
            this.getJobList(1);
        }
    },

    onPageScroll(e) {
        //导航栏到达顶部固定
        if (e.scrollTop > 280) {
            // 当页面顶端距离大于一定高度时
            let a = this.selectComponent("#mytabs");
            a.meth1();
        } else {
            let b = this.selectComponent("#mytabs");
            b.meth2();
        }
    },

    // 获取轮播图数据
    async getSwiperList() {
        console.log(this.Authorization)
        var that = this
        const result = await request({
            url: "/swipper/swipper/swiperdata",
            header: that.Authorization
        });
        this.setData({
            swiperList: result
        })
    },

    //获取分类导航栏数据
    async getCatesList() {
        var that = this
        const result = await request({
            url: "/swipper/swipper/catitems",
            header: that.Authorization
        });
        this.setData({
            catesList: result
        })
    },
    //获取职位信息列表数据
    async getJobList(tag) {
        var that = this
        const result = await request({
            url: "/job/job/jobsByEs",
            data: this.QueryParams,
            header: that.Authorization
        });
        // console.log(result)
        // 计算总页数
        this.totalPages = result.pages;
        if (tag == 1) {
            this.setData({
                // jobList: result.data.message
                // 拼接数组
                jobList: [...this.data.jobList, ...result.list]
            });
            wx.stopPullDownRefresh();
        } else {
            this.setData({
                // 更新数组
                jobList: result.list
            });
        }
    },
    //item(index,pagePath,text)
    onTabItemTap: function (item) {

    },
    handleBindSuccess() {
        console.log("跳转成功");
    },
    handleBindFail() {
        console.log("跳转失败");
    }
});