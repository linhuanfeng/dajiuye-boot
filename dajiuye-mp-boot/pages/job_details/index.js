import { request } from "../../requests/index.js";
const httputil = require("../../utils/httputil.js") //一定要引入，根据你自己写的上传文件路径
var out_photo = "";
var app = getApp() // 获取全局app对象
Page({

    /**
     * 页面的初始数据
     */
    data: {
        submit_stat: "立即投递",
        photo: "",
        jobObj: {},
        similarJobs: [],
        jobId: {},
        jobType:'',
        // 商品是否被收藏过
        isCollect: false,
        click: false, //是否显示弹窗内容
        option: false, //显示弹窗或关闭弹窗的操作动画
        resumeUrl: {}, // 简历的相关信息
        resumeTime: {},
        resumeName: '',
        isLogin: false
    },
    Authorization: {
        token: 'asda'
    },
    // 发送投递简历
    Userdeliver: {
        fromUserId: '',
        toHrId: '',
        resumeUrl: '',
        jobId:''
    },
    // 工作信息全局对象
    jobInfoStorage: {},
    onLoad: function (options) {
        this.Authorization.token = app.globalData.token
        this.setData({
            jobId: options.jobId,
            jobType:options.jobType
        });
        this.getJobDetail(this.data.jobId);
        this.incrementScore(this.data.jobId);
        // this.getSimilarJobDetail(this.data.jobId,this.data.jobType);
    },

    // 职位被浏览，发送增浏览权值
    async incrementScore(jobId){
        var that=this
        const result = await request({ 
            url: "/job/job/jobView", 
            method: 'POST',
            data: {jobId}, 
            header: that.Authorization });
        console.log("增加了职位权重")
    },

    // 第一种动态设置高度的方法是：需要一个容器为背景色（灰色区域），一个容器为弹窗内容（粉红色区域），两者是独立的，实现的原理是一样的；粉红色区域的话，就是设置好绝对位置（在屏幕的底部）和默认内容的区域样式，动态设置内容区域的高度，比如弹出：一开始高度为0（隐藏了），通过animation设置的动画时间，将高度从0到指定高度，内容慢慢就会显示了，然后保留最后一帧的动画样式就行了；收缩也是一样的道理。
    // 用户点击显示弹窗，点击选择简历
    clickPup: function () {
        let _that = this;
        // 是否点击弹窗
        if (!_that.data.click) {
            _that.setData({
                click: true,
            })
        }
        // 弹窗的开闭效果
        if (_that.data.option) {
            _that.setData({
                option: false,
            })
            // 关闭显示弹窗动画的内容，不设置的话会出现：点击任何地方都会出现弹窗，就不是指定位置点击出现弹窗了
            setTimeout(() => {
                _that.setData({
                    click: false,
                })
            }, 500)
        } else {
            _that.setData({
                option: true
            })
        }
    },
    // 预览简历
    openFile() {
        wx.downloadFile({
            url: this.data.resumeUrl, //要预览的 PDF 的地址
            success: function (res) {
                console.log(res);
                if (res.statusCode === 200) { //成功
                    var Path = res.tempFilePath //返回的文件临时地址，用于后面打开本地预览所用
                    wx.openDocument({
                        fileType: 'pdf', // 需要写上文件类型才能预览，不让回找系统应用打开，体验不好
                        filePath: Path, //要打开的文件路径
                        success: function (res) {
                            console.log('打开 PDF 成功');
                        }
                    })
                }
            },
            fail: function (res) {
                console.log(res); //失败
            }
        })
    },
    // 点击立即投递
    resumeSubmit(e) {
        // 判断是否已登录
        const userInfo = wx.getStorageSync('userInfo')
        if (!userInfo) {
            wx.showToast({
                title: '请先登录',
                icon: 'none',
                image: '',
                duration: 1000,
                mask: false,
            });
            return;
        } else {
            this.setData({
                isLogin: true
            })
        }
        // 判断是否已投递
        if (this.data.submit_stat == "已投递") {
            wx.showToast({
                title: '您已投递',
                icon: 'none',
                image: '',
                duration: 1000,
                mask: false,
            });
            return
        }
        // 判断缓存是否有简历
        const resumeUrl = wx.getStorageSync('resumeUrl')
        if (resumeUrl) {
            this.setData({
                resumeUrl,
                resumeTime: resumeUrl.substring(resumeUrl.lastIndexOf('_') + 1, resumeUrl.lastIndexOf('-')),
                resumeName: resumeUrl.substring(resumeUrl.lastIndexOf('-') + 1)
            })
        }
        this.clickPup()

    },
    // 提交投递请求
    async sendResume() {
        var that=this
        const userInfo = wx.getStorageSync('userInfo')
        this.Userdeliver.fromUserId = userInfo.id;
        this.Userdeliver.toHrId = this.data.jobObj.jobAuthorId;
        this.Userdeliver.resumeUrl = userInfo.resume;
        this.Userdeliver.jobId = this.data.jobObj.jobId;
        const result = await request({ url: "/user/deliver/saveDeliver", data: this.Userdeliver, header: that.Authorization });        
        console.log("发送给hr后返回的结果为：")
        console.log(result)
        this.setData({
            submit_stat: "已投递"
        })
        // 发送已投递信息给hr
        this.sendMessage(userInfo.openId,this.data.jobObj.jobAuthorId, this.data.jobObj.jobName)
        // 关闭弹出窗
        this.clickPup()
        wx.showToast({
            title: '投递成功',
            icon: 'none',
            image: '',
            duration: 1000,
            mask: false,
        });
        this.handleSendResumeAdd()
    },
    // 发送消息
    async sendMessage(fromOpenId, toOpenId, news) {
        var that=this
        const result = await request({ url: "/message/message/saveMessage2", data: {fromOpenId,toOpenId,news},method: 'POST',  header: that.Authorization});
        console.log("发送消息了")
        console.log(result)
    },
    // 获取职位详情数据
    async getJobDetail(jobId) {
        var that=this
        const result = await request({ url: "/job/job/job", data: { jobId }, header: that.Authorization });
        console.log(result)
        this.jobInfoStorage = result;
        // 判断是否都投递过该职位
        this.hasSendJob()
        // 1 获取缓存中的商品收藏的数组
        let collects = wx.getStorageSync("collects") || [];
        // 判断当前商品是否被收藏
        let isCollect = collects.some(v => v.jobId === this.jobInfoStorage.jobId);
        this.setData({
            jobObj: result,
            isCollect
        })
    },
    // 判断该职位是否在缓存数组中
    hasSendJob() {
        // 1 获取缓存中的发送简历的数组
        let hasSendJobs = wx.getStorageSync("hasSendJobs") || [];
        // 判断当前职位是否已发送过
        // console.log(hasSendJobs)
        console.log(this.jobInfoStorage.jobId)
        let isSubmit = hasSendJobs.some(v => v.jobId === this.jobInfoStorage.jobId);
        console.log("是否已投递")
        console.log(isSubmit)
        if (isSubmit) {
            this.setData({
                submit_stat: '已投递'
            })
        }
    },
    // 获取相似职位详情数据
    // async getSimilarJobDetail(jobId) {
    //     const { isSimilar } = "1";
    //     const result = await request({ url: "/home/jobdata", data: { jobId, isSimilar } });
    //     this.setData({
    //         similarJobs: result.list
    //     })
    // },
    // 点击轮播图 放大预览
    handlePreviewImage(e) {
        // 1 先构造要预览的图片数组
        const urls = this.GoodsInfo.pics.map(v => v.pics_mid);
        // 2 接受传递过来的图片url
        const current = e.currentTarget.dataset.url;
        wx.previewImage({
            current,
            urls,
            success: (result) => {
            },
            fail: () => { },
            complete: () => { }
        });

    },
    // 点击收藏图标
    handleCollectAdd() {
        let isCollect = false;
        // 1 先获取缓存中的购物车数组
        let collects = wx.getStorageSync("collects") || [];
        // 2 判断商品对象是否存在于购物车的数组中
        let index = collects.findIndex(v => v.jobId === this.jobInfoStorage.jobId);
        if (index != -1) {
            // 能找到 已经收藏过 再点击则取消收藏
            collects.splice(index, 1);
            isCollect = false;
            wx.showToast({
                title: '取消成功',
                icon: 'success',
                mask: true
            });
        } else {
            // 没有收藏过
            collects.push(this.jobInfoStorage);
            isCollect = true;
            wx.showToast({
                title: '收藏成功',
                icon: 'success',
                mask: true
            });
        }
        // 5 把收藏夹重新添加回缓存中
        wx.setStorageSync("collects", collects);
        this.setData({
            isCollect
        })
    },
    // 将已投递简历存到缓存中
    handleSendResumeAdd() {
        // 1 先获取缓存中已投递职位数组
        let hasSendJobs = wx.getStorageSync("hasSendJobs") || [];
        hasSendJobs.push(this.data.jobObj);
        // 5 把收藏夹重新添加回缓存中
        wx.setStorageSync("hasSendJobs", hasSendJobs);
    }
})
// 表单提交，上传文件
// function formSubmit() {
//     var photo = [out_photo];
//     httputil.send_photo(photo, function (res) {
//         //成功回调函数  你随便做操作
//         console.log("图片上传成功");

//         wx.showToast({
//             title: '投递成功',
//             icon: 'none',
//             image: '',
//             duration: 1000,
//             mask: false,
//         });
//     })
// }