import { request } from "../../requests/index.js";
var app = getApp() // 获取全局app对象
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 期望的职位数组
        expectJobs: [],
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
            expectJobs: wx.getStorageSync("expectJobs") || []
        })
        this.getCates2();
    },
    onShow: function() {},
    //获取分类数据
    async getCates2() {
        var that=this
        const result = await request({ url: "/job/category/jobcatdata" ,header: that.Authorization});
        this.setData({
            Cates2: result
        });
        let leftMenuList = this.data.Cates2.map(v => v.lname);
        let rightContent = this.data.Cates2[0].children;
        rightContent.forEach(e => {
            e.children.forEach(e2 => {
                this.data.expectJobs.forEach(e3 => {
                    console.log("e3.id:" + e3.id + "===e2.id:" + e2.id);
                    if (e3.id == e2.id) {
                        e2.isSelect = true;
                    }
                });
            })
        });
        this.setData({
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
            e.children.forEach(e2 => {
                this.data.expectJobs.forEach(e3 => {
                    console.log("e3.id:" + e3.id + "===e2.id:" + e2.id);
                    if (e3.id == e2.id) {
                        e2.isSelect = true;
                    }
                });

            })
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
        const { index2 } = e.currentTarget.dataset;
        let arr = this.data.expectJobs;
        var item = this.data.rightContent[index1].children[index2];

        if (item.isSelect) {
            const index = this.data.expectJobs.findIndex(v => {
                return v.id == item.id; //注意这是一个函数，没有return 就没哟返回值，结果就默认为-1
            });
            if (index != -1) {
                // 已被选中的再次点击则视为取消
                arr.splice(index, 1);
            } else {
                console.log("异常处理：期望职业选中表示异常");
            }
        } else {
            if (this.data.expectJobs.length < 3) {
                arr.push(this.data.rightContent[index1].children[index2]);
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
            expectJobs: arr,
            rightContent: this.data.rightContent
        })
    },
    // 开头职位的删除事件
    handleItemTapHead(e) {
        console.log(e);
        const { index } = e.currentTarget.dataset;
        let arr = this.data.expectJobs;
        var item = this.data.expectJobs[index];
        var id = item.id;
        // 这一步应该就能删除对应元素
        arr.splice(index, 1);
        let a1 = -1,
            a2 = -1;
        a1 = this.data.rightContent.findIndex((e1, i1) => {
            const t1 = e1.children.findIndex((v, i2) => {
                a2 = i2;
                return v.id == id;
            })
            if (t1 != -1) {
                // 找到了要被删除选中的元素
                return t1;
            }
        });
        item.isSelect = false;
        console.log("this.data.rightContent[a1]:" + this.data.rightContent[a1]);
        if (a1 != -1) {
            let rightContent = this.data.rightContent;
            rightContent[a1].children[a2].isSelect = false;
            this.setData({
                rightContent
            })
        }
        this.setData({
            expectJobs: arr,
        })
    },
    // 点击期望职位确定按钮
    handleExpectJobs() {
        // 5 把收藏夹重新添加回缓存中
        wx.setStorageSync("expectJobs", this.data.expectJobs);
        wx.switchTab({
            url: '/pages/user/index',
            success: (result) => {

            },
            fail: () => {},
            complete: () => {}
        });
    },
})