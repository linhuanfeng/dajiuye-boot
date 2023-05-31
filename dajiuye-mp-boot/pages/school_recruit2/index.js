import { request } from "../../requests/index.js";
import regeneratorRuntime from '../../lib/runtime/runtime';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 校招信息数组
        schoolList: [],
        currentIndex: 0,
        scrollTop: 0,
        tabs: [{
                id: 0,
                value: "秋招",
                isActive: false
            },
            {
                id: 1,
                value: "春招",
                isActive: false
            },
            {
                id: 2,
                value: "职位",
                isActive: false
            }
        ],
    },

    handleTabsItemChange(e) {
        // 1 获取被点击的标题索引
        const { index } = e.detail;
        // console.log(index);
        // 修改源数组
        let { tabs } = this.data;
        tabs.forEach((v, i) => i === index ? (v.isActive = !v.isActive) : v.isActive = false);

        // for (let i = 0; i < this.tabs.length; i++) {
        //     const element = this.tabs[index];
        //     element
        // }

        //  3 赋值到data中
        this.setData({
            tabs
        })
    },

    // 获取校招列表数据
    async getSchoolList() {
        const res = await request({ url: "/school/schooldata", data: {} });
        this.setData({
            schoolList: result
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {

        this.getSchoolList();


        /*
        0 web中的本地存储和小程序中的本地存储的区别
            1 写代码的方式不一样了
                web:localStorage.setItem("key","value") localStorage.getItem("key")
                小程序中：wx.setStorageSync("key","value");wx.getStorageSync("key")
            2 存的时候 有没有做数据类型转换
                web:不管存入什么类型，最终都先调用以下 toString(),把数据变成字符串 在存进去
                小程序：不存在类型转换这种操作 存什么类型的数据进去，获取的时候就是什么数据
        1 先判断本地缓存中有没有旧的数据
          {time:Data.now(),data:[...]}
        2 没有旧的数据 直接发送请求
        3 有旧的数据 同时 旧的数据也没有过期 就使用 本地存储的旧数据即可
        */

        // 1 获取本地存储数据(小程序中也是存在本地存储技术的)
        // const Cates = wx.getStorageSync("cates");
        // // 2 判断
        // if (!Cates) {
        //     // 不存在 发送请求获取数据
        //     this.getCates();
        // } else {
        //     // 有旧的数据 定义过期时间 10s 改成5分钟
        //     if (Date.now() - Cates.time > 1000 * 10) {
        //         // 重新发送请求
        //         this.getCates();
        //     } else {
        //         // 可以使用旧的数据
        //         this.Cates = Cates.data;
        //         let leftMenuList = this.Cates.map(v => v.cat_name);
        //         let rightContent = this.Cates[0].children;
        //         this.setData({
        //             leftMenuList,
        //             rightContent
        //         })
        //     }
        // }
    }
})