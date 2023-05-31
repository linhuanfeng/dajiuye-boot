//0 引入用来发送请求的方法 一定要把路径补全
import { request } from "../../requests/index.js";
var app = getApp() // 获取全局app对象
Page({
    data: {
        // 用户数组
        jobList: [],
        tabs: [{
            id: 0,
            value: "待处理",
            isActive: true
        },
        {
            id: 1,
            value: "待定",
            isActive: false
        },
        {
            id: 2,
            value: "录用",
            isActive: false
        },
        {
            id: 3,
            value: "不合适",
            isActive: false
        },
        {
            id: 3,
            value: "全部",
            isActive: false
        }
        ],
        userInfo:{}
    },
    // 分页需要的参数
    Params2: {
        pageNo: 1,
        pageSize: 10,
        id:'',
        // 职位类型
        state: "0"
    },
    totalPages:1,
    Authorization: {
        token: 'asda'
    },
    onLoad: function (options) {
        this.Authorization.token = app.globalData.token
    },
    onShow(e){
        // 重置请求参数
        this.resetParams2()
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
        this.Params2.id=userInfo.id
        this.getUsersList(0);
    },

    resetParams2(){
        // 重置请求参数
        this.Params2.pageNo=1;
        this.Params2.pageSize=10;
        this.Params2.id='';
        this.Params2.state=0;
    },

    //WXML 数据绑定：用于父组件（页面模板）向子组件（组件模板）的指定属性设置数据，仅能设置 JSON 兼容数据。
    // 事件：用于子组件向父组件传递数据，可以传递任意数据。
    // 如果以上两种方式不足以满足需要，父组件还可以通过 this.selectComponent 方法获取子组件实例对象，这样就可以直接访问组件的任意数据和方法。
    // 这个是由子组件触发的
    handleTabsItemChange(e) {
        // 重置
        this.Params2.pageNo=1
        this.totalPages=1
        // 1 获取被点击的标题索引
        const { index } = e.detail;
        // // 修改源数组
        let { tabs } = this.data;
        tabs.forEach((v, i) => i === index ? v.isActive = true : v.isActive = false);
        if(index==4){
            // 要查询全部
            this.Params2.state=''
        }else{
            this.Params2.state=index
        }
        //  3 赋值到data中
        this.setData({
            tabs,
            jobList: []
        })
        this.getUsersList(0);
    },
    onReachBottom: function () {
        // 1  判断还有没有下一页数据
        if (this.Params2.pageNo >= this.totalPages) {
            // 没有下一页数据
            wx.showToast({
                title: '没有下一页数据了',
                icon: 'none',
                image: '',
                duration: 1500,
                mask: false,
                success: (result) => {

                },
                fail: () => { },
                complete: () => { }
            });
        } else {
            this.Params2.pageNo++;
            wx.showToast({
                title: '加载中',
                icon: 'none',
                image: '',
                duration: 1000,
                mask: false,
            });
            this.getUsersList(1);
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
    //获取职位信息列表数据 tag=0 表示重新清洗数组，1把数据添加到数组
    async getUsersList(tag) {
        var that=this
        const result = await request({ url: "/user/user/getusers", data:this.Params2,header: that.Authorization });
        this.totalPages = result.pages;
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
        wx.setStorageSync("userList", this.data.jobList);
    }
});