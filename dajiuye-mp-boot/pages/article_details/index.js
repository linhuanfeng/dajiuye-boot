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
            interviewObj: {},
            jobId: {},
            // 商品是否被收藏过
            isCollect: false,
            mystr: '负责公司企业/n应用平台\n的开发和架构设计，保证设计和编码的质量，承担重点、难点的技术攻坚，主要开发语言为Java\n',
            mystr2: '',
            desc: ''
        },
        Authorization: {
            token: 'asda'
        },
        param:{
            page:1,
            limit:1
        },
        /**
         * onLoad先执行
         * @param {*} options 
         */
        onLoad: function(options) {
            this.Authorization.token = app.globalData.token
            this.setData({
                jobId: options.jobId
            });
            this.getInterviewBank();
            // this.getSimilarJobDetail(this.data.jobId);
        },
        // 点击了立即投递
        touchphoto(e) {
            const resumeUrl=wx.getStorageSync('resumeUrl')
            if(resumeUrl==null||resumeUrl==''){
                console.log(resumeUrl)
            }
            console.log(resumeUrl)
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
            var that = this
            wx.chooseMessageFile({
                count: 1, // 默认9
                type: 'file',
                success: function(res) {
                    // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
                    var tempFilePaths = res.tempFiles[0].path
                    that.setData({
                        photo: tempFilePaths
                    })
                    that.setOutPhoto()
                }
            })
        },
        setOutPhoto() {
            out_photo = this.data.photo
            formSubmit()
            this.setData({
                submit_stat: "已投递"
            })
        },
        // 获取职位详情数据
        async getInterviewBank() {
            var that=this
            const result = await request({ url: "/swipper/interview/list",data:that.param, header: that.Authorization });
            this.setData({
                interviewObj: result[0]
            })
            // 1 获取缓存中的商品收藏的数组
            // let collects = wx.getStorageSync("collects") || [];
            // // 判断当前商品是否被收藏
            // let isCollect = collects.some(v => v.jobId === this.jobInfoStorage.jobId);
            // this.setData({
            //     jobObj: result.list[0],
            //     isCollect
            // })
        },
        // 下一页
        handleNext(){
            this.param.page=this.param.page+1
            this.getInterviewBank()
        },
        // 点击收藏图标
        handleCollectAdd() {
            // let isCollect = false;
            // // 1 先获取缓存中的购物车数组
            // let collects = wx.getStorageSync("collects") || [];
            // // 2 判断商品对象是否存在于购物车的数组中
            // let index = collects.findIndex(v => v.jobId === this.jobInfoStorage.jobId);
            // if (index != -1) {
            //     // 能找到 已经收藏过 再点击则取消收藏
            //     collects.splice(index, 1);
            //     isCollect = false;
            //     wx.showToast({
            //         title: '取消成功',
            //         icon: 'success',
            //         mask: true
            //     });
            // } else {
            //     // 没有收藏过
            //     collects.push(this.jobInfoStorage);
            //     isCollect = true;
            //     wx.showToast({
            //         title: '收藏成功',
            //         icon: 'success',
            //         mask: true
            //     });
            // }
            // // 5 把收藏夹重新添加回缓存中
            // wx.setStorageSync("collects", collects);
            this.setData({
                isCollect:true
            })
        },
    })
    // 表单提交，上传文件
function formSubmit() {
    var photo = [out_photo];
    httputil.send_photo(photo, function(res) {
        //成功回调函数  你随便做操作
        console.log("图片上传成功");

        wx.showToast({
            title: '投递成功',
            icon: 'none',
            image: '',
            duration: 1000,
            mask: false,
        });
    })
}