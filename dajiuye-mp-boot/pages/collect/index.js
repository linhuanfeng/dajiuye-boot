// Pages/collect/index.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        collects: [],
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
        const collects = wx.getStorageSync("collects");
        this.setData({
            collects
        })
    },
})