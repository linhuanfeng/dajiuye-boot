//0 引入用来发送请求的方法 一定要把路径补全
import { request } from "../../requests/index.js";
Page({
    data: {
        //职位数组
        jobList: [],
    },

    onLoad: function(options) {
        this.getJobList();
    },

    //获取职位信息列表数据
    async getJobList() {
        const jobList=wx.getStorageSync('customized_res')
        this.setData({
            // jobList: result.data.message
            // 拼接数组
            jobList
        });
    }
});