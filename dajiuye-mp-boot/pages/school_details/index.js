import { request } from "../../requests/index.js";
var app=getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        schoolId: {},
        schoolObj: {},
        // 商品是否被收藏过
        isCollect: false
    },
    Authorization: {
        token: 'asda'
    },
    // 商品全局对象
    GoodsInfo: {},
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        this.Authorization.token = app.globalData.token
        // 获取当前校招详情信息
        this.getSchoolDetails(options.schoolId);
    },
    // 获取校招详情数据
    async getSchoolDetails(schoolId) {
        var that=this
        const result = await request({ url: "/swipper/school/schooldata", data: { schoolId },header: that.Authorization });
        this.setData({
            schoolObj: result[0]
        })
    },
    // 点击秒杀
    handleSecKill() {
        // 1 先获取缓存中的购物车数组
        let cart = wx.getStorageSync("cart") || [];
        // 2 判断商品对象是否存在于购物车的数组中
        let index = cart.findIndex(v => v.goods_id === this.GoodsInfo.goods_id);
        if (index === -1) {
            // 3 不存在 第一次添加
            this.GoodsInfo.num = 1;
            this.GoodsInfo.checked = true;
            cart.push(this.GoodsInfo);
        } else {
            // 4 已经存在购物车数据 执行num++
            cart[index].num++;
        }
        // 5 把购物车重新添加回缓存中
        wx.setStorageSync("cart", cart);
        // 弹窗提示
        wx.showToast({
            title: '加入成功',
            icon: 'success',
            image: '',
            duration: 1500,
            // true 防止用户手抖疯狂点击按钮
            mask: true,
            success: (result) => {

            },
            fail: () => {},
            complete: () => {}
        });
    }
})