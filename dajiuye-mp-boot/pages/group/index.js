/*
1 当页面被打开的时候 onShow
    0 onshow 不同于onload 无法在形参上接受 options参数
    0.5 判断缓存中有没有token
        1 没有 直接跳转到授权页面
        2 有 直接往下进行
    1 获取url上的参数type
    2 根据type来决定页面标题的数组元素 哪个被激活选中
    2 根据type值发送请求获取订单数据
    3 渲染页面
2 点击不同数据 重新发送请求获取和渲染数据

*/
import { request } from "../../requests/index.js";
import regeneratorRuntime from '../../lib/runtime/runtime';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        orders: [],
        tabs: [{
                id: 0,
                value: "公司介绍",
                isActive: true
            },
            {
                id: 1,
                value: "职位",
                isActive: false
            }
        ],
    },
    // 获取订单列表的方法
    async getOrders(type) {
        const res = await request({ url: "/my/orders/all", data: { type } })
        this.setData({
            // orders: res.orders
        })
    },
    // 根据标题的索引来激活选中 标题数组
    changeTitleByIndex(index) {
        // 2 修改源数组
        let { tabs } = this.data;
        tabs.forEach((v, i) => i === index ? v.isActive = true : v.isActive = false);
        // //  3 赋值到data中
        this.setData({
            tabs
        })
    },
    handleTabsItemChange(e) {
        // console.log(e);
        // 1 获取被点击的标题索引
        const { index } = e.detail;
        this.changeTitleByIndex(index);
        // 2 重新发送请求
        this.getOrders(index + 1);
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        // console.log(options);
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
        const token = wx.getStorageSync("token");
        // if (!token) {
        //     wx.navigateTo({
        //         url: '/pages/auth/index',
        //         success: (result) => {

        //         },
        //         fail: () => {},
        //         complete: () => {}
        //     });

        // }

        // 1 获取当前小程序的页面栈 长度最大10 太久的会被自动清除
        let pages = getCurrentPages();
        // 2 数组中索引最大的页面就是当前页面
        let currentPage = pages[pages.length - 1];
        // 3 获取url的type参数
        const { type } = currentPage.options;
        // 4 激活选中页面标题 当type=1 index=0
        this.changeTitleByIndex(type - 1);
        this.getOrders(type)
    },
})