import { request } from "../../requests/index.js";
var app = getApp() // 获取全局app对象
Page({
    /**
     * 页面的初始数据
     */
    data: {
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
        jobType:'1',
        pageNum: 1,
        pageSize: 10,
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        this.Authorization.token = app.globalData.token
        this.QueryParams.cid = options.cid
        this.getJobList(this.QueryParams);
    },
    handleTabsItemChange(e) {
        // 1 获取被点击的标题索引
        const { index } = e.detail;
        // // 修改源数组
        let { tabs } = this.data;
        tabs.forEach((v, i) => i === index ? v.isActive = true : v.isActive = false);
        if (index == 0) {
            this.QueryParams.jobType = "2"
        } else if (index == 1) {
            this.QueryParams.jobType = "1"
        } else if (index == 2) {
            this.QueryParams.jobType = "3"
        } else {
            this.QueryParams.jobType = ""
        }
        //  3 赋值到data中
        this.setData({
            tabs
        })
        this.setData({
            jobList: []
        })
        this.getJobList(this.QueryParams);
    },
    onReachBottom: function() {
        // 1  判断还有没有下一页数据
        if (this.QueryParams.pageNum >= this.totalPages) {
            // 没有下一页数据
            // console.log("没有下一页");
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
            this.QueryParams.pageNum++;
            wx.showToast({
                title: '加载中',
                icon: 'none',
                image: '',
                duration: 1000,
                mask: false,
            });
            this.getJobList(this.QueryParams);
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
    async getJobList(QueryParams) {
        var that=this
        const result = await request({ url: "/job/job/jobsByCid", data: QueryParams, method: 'POST', header:that.Authorization});
        // console.log(result)
        const total = result.total;
        // 计算总页数
        this.totalPages = Math.ceil(total / this.QueryParams.pageSize);
        this.setData({
            // jobList: result.data.message
            // 拼接数组
            jobList: [...this.data.jobList, ...result.list]
        });
        wx.stopPullDownRefresh();
    }
});