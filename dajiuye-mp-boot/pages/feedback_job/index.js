//0 引入用来发送请求的方法 一定要把路径补全
import { request } from "../../requests/index.js";
var app = getApp() // 获取全局app对象
Page({
    data: {
        //职位数组
        jobList: [],
        tabs: [{
                id: 0,
                value: "已投递",
                isActive: true
            },
            {
                id: 1,
                value: "被查看",
                isActive: false
            },
            {
                id: 2,
                value: "通过初筛",
                isActive: false
            },
            {
                id: 3,
                value: "不适合",
                isActive: false
            }
        ],
    },
    // 分页需要的参数
    Params: {
        pagenum: 1,
        pagesize: 10,
        userid: '',
        // 职位类型
        state: "0"
    },
    Authorization: {
        token: 'asda'
    },
    totalPages:1,
    //options(Object) 页面开始加载就会触发
    onLoad: function(options) {
        this.Authorization.token = app.globalData.token
        const userInfo = wx.getStorageSync('userInfo')
        this.Params.userid=userInfo.id
        this.getJobList(0);
    },

    //WXML 数据绑定：用于父组件（页面模板）向子组件（组件模板）的指定属性设置数据，仅能设置 JSON 兼容数据。
    // 事件：用于子组件向父组件传递数据，可以传递任意数据。
    // 如果以上两种方式不足以满足需要，父组件还可以通过 this.selectComponent 方法获取子组件实例对象，这样就可以直接访问组件的任意数据和方法。
    // 这个是由子组件触发的
    handleTabsItemChange(e) {
        // 重置
        this.Params.pagenum=1
        this.totalPages=1
        // 1 获取被点击的标题索引
        const { index } = e.detail;
        // // 修改源数组
        let { tabs } = this.data;
        tabs.forEach((v, i) => i === index ? v.isActive = true : v.isActive = false);
        let state = "";
        this.Params.state=index
        //  3 赋值到data中
        this.setData({
            tabs,
            state,
            jobList: []
        })
        this.getJobList(0);
    },
    onReachBottom: function() {
        // 1  判断还有没有下一页数据
        if (this.Params.pagenum >= this.totalPages) {
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
            this.Params.pagenum++;
            wx.showToast({
                title: '加载中',
                icon: 'none',
                image: '',
                duration: 1000,
                mask: false,
            });
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
    //获取职位信息列表数据
    async getJobList(tag) {
        var that=this
        const result = await request({ url: "/job/job/jobsFeedback", data: this.Params,header: that.Authorization });
        // console.log(result)
        // 计算总页数
        this.totalPages= result.pages;
        if(tag==1){
            this.setData({
                // jobList: result.data.message
                // 拼接数组
                jobList: [...this.data.jobList, ...result.list]
            });
            wx.stopPullDownRefresh();
        }else{
            this.setData({
                // 更新数组
                jobList: result.list
            });
        }
    }
});