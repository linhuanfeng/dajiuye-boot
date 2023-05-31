import { request } from "../../requests/index.js";
var app = getApp() // 获取全局app对象
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 期望的职位数组
        expectCities: [],
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
        this.setData({
            expectCities: wx.getStorageSync("expectCities") || []
        })
        this.getCates2();
    },
    onShow: function() {},
    //获取分类数据
    async getCates2() {
        var that=this
        const result = await request({ url: "/swipper/area/placedata",header: that.Authorization });
        this.setData({
            Cates2: result
        });
        let leftMenuList = this.data.Cates2.map(v => v.provinceName);
        let rightContent = this.data.Cates2[0].children;
        rightContent.forEach(e => {
            this.data.expectCities.forEach(e2 => {
                if (e2.id == e.id) {
                    e.isSelect = true;
                }
            });
        });
        // let expectCities = ["电工", "java", "律师"];
        this.setData({
            // expectCities,
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
        rightContent.forEach(e => {
            this.data.expectCities.forEach(e2 => {
                if (e2.id == e.id) {
                    e.isSelect = true;
                }
            });
        });
        this.setData({
            currentIndex: index,
            rightContent,
            // 重新设置 他右侧内容的scroll-view标签的距离顶部的距离
            scrollTop: 0
        })
    },
    // 右侧职位的点击事件
    handleItemTapJob(e) {
        const { index1 } = e.currentTarget.dataset;
        let arr = this.data.expectCities;
        var item = this.data.rightContent[index1];

        if (item.isSelect) {
            // 已被选中过
            const index = this.data.expectCities.findIndex(v => {
                return v.id == item.id; //注意这是一个函数，没有return 就没哟返回值，结果就默认为-1
            });
            if (index != -1) {
                // 已被选中的再次点击则视为取消
                arr.splice(index, 1);
            } else {
                console.log("异常处理：期望职业选中表示异常");
            }
        } else {
            if (this.data.expectCities.length < 3) {
                arr.push(this.data.rightContent[index1]);
            } else {
                wx.showToast({
                    title: '最多选择三个',
                    icon: 'none',
                    mask: true
                });
                return;
            }
        }
        item.isSelect = !item.isSelect;
        this.setData({
            expectCities: arr,
            rightContent: this.data.rightContent
        })
    },
    // 开头职位的删除事件
    handleItemTapHead(e) {
        console.log(e);
        const { index } = e.currentTarget.dataset;
        let arr = this.data.expectCities;
        var item = this.data.expectCities[index];
        var id = item.id;
        arr.splice(index, 1);
        let a1 = -1;
            // a2 = -1;
        // 判断当前右边内容是否有被选中的内容 
        a1 = this.data.rightContent.findIndex((e1, i1) => {
            return e1.id==id;
        });
        item.isSelect = false;
        console.log("this.data.rightContent[a1]:" + this.data.rightContent[a1]);
        if (a1 != -1) {
            let rightContent = this.data.rightContent;
            rightContent[a1].isSelect = false;
            this.setData({
                rightContent
            })
        }
        this.setData({
            expectCities: arr,
        })
    },
    // 点击期望职位确定按钮
    handleExpectJobs() {
        // 5 把收藏夹重新添加回缓存中
        wx.setStorageSync("expectCities", this.data.expectCities);
        wx.switchTab({
            url: '/pages/user/index',
            success: (result) => {

            },
            fail: () => {},
            complete: () => {}
        });
    },
})