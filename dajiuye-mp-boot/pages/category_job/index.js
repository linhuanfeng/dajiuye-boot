import { request } from "../../requests/index.js";
var app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 左侧的菜单数据
        leftMenuList: [],
        // 右侧的商品数据
        rightContent: [],
        // 被点击的左侧菜单
        currentIndex: 0,
        currentIndexJob: -1,
        scrollTop: 0,
        top: 0,
        //接口的返回数据
        Cates2: [],
    },

    Authorization: {
        token: 'asda'
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        this.Authorization.token = app.globalData.token
        this.getCates2();
    },
    onShow: function() {},
    //获取分类数据
    async getCates2() {
        var that=this
        const result = await request({ url: "/job/category/jobcatdata",header: that.Authorization });
        console.log(result)
        this.setData({
            Cates2: result.list
        })
        let leftMenuList = this.data.Cates2.map(v => v.lname);
        let rightContent = this.data.Cates2[0].children;
        this.setData({
            // expectJobs,
            leftMenuList,
            rightContent
        })
    },
    // 左侧菜单的点击事件
    handleItemTap(e) {
        /*
        1 获取被点击的标题身上的索引
        2 给data中的currentIndex赋值就可以了
        */
        const { index } = e.currentTarget.dataset;
        let rightContent = this.data.Cates2[index].children;
        this.setData({
            currentIndex: index,
            rightContent,
            // 重新设置 他右侧内容的scroll-view标签的距离顶部的距离
            scrollTop: 0
        })
    }
})