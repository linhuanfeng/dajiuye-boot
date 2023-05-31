/*
1 发送请求获取数据
2 点击轮播图 预览大图
    1 给轮播图绑定点击事件
    2 调用小程序的api previewImage
3 点击 加入购物车
    1 先绑定点击事件
    2 获取缓存中的购物车数据 数组格式
    3 先判断 当前的商品是否存在于 购物车
    4 已经存在 修改商品数据 执行购物车数量++ 重新把购物车数组 填充回缓存中
    5 不存在于购物车的数组中 直接给购物车添加一个新元素 新元素带上购买属性 num 重新把购物车数组 填充回缓存中
    6 弹出提示
4 商品收藏
    1 页面onShow的时候 加载缓存中的商品收藏数据
    2 判断当前商品是不是被收藏
        1 是 改变图标
        2 不是 。。
    3 点击商品收藏按钮
        1 判断该商品是否存在于缓存中
        2 已经存在 把该商品删除
        3 没有存在 把该商品添加到收藏数组中 存入缓存中即可
*/
import { request } from "../../requests/index.js";
import regeneratorRuntime from '../../lib/runtime/runtime';
const httputil = require("../../utils/httputil.js") //一定要引入，根据你自己写的上传文件路径
var out_photo = "";
Page({

    /**
     * 页面的初始数据
     */
    data: {
        submit_stat: "立即投递",
        photo: "",
        goodsObj: {},
        jobObj: {},
        similarJobs: [],
        jobId: {},
        // 商品是否被收藏过
        isCollect: false,
        mystr: '负责公司企业/n应用平台\n的开发和架构设计，保证设计和编码的质量，承担重点、难点的技术攻坚，主要开发语言为Java\n',
        mystr2: '',
        desc: '',
        click: false, //是否显示弹窗内容
        option: false, //显示弹窗或关闭弹窗的操作动画
        resumeUrl: {}, // 简历的相关信息
        resumeTime: {},
        resumeName: '',
        isLogin: false
    },
    // 工作信息全局对象
    jobInfoStorage: {},
    /**
     * onLoad先执行
     * @param {*} options 
     */
    onLoad: function (options) {
        this.setData({
            jobId: options.jobId
        });
        this.getJobDetail(this.data.jobId);
        this.getSimilarJobDetail(this.data.jobId);
    },
    // 用户点击显示弹窗，点击投递简历
    clickPup: function () {
        const userInfo = wx.getStorageSync('userInfo')
        if (!userInfo) {
            // wx.navigateBack({
            //     delta: 0,
            // })
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
         // 从缓存判断是否有简历
         const resumeUrl=wx.getStorageSync('resumeUrl')
         // 设置简历的详细信息
         if(resumeUrl){
             this.setData({
                 resumeUrl,
                 resumeTime:resumeUrl.substring(resumeUrl.lastIndexOf('_')+1,resumeUrl.lastIndexOf('-')),
                 resumeName:resumeUrl.substring(resumeUrl.lastIndexOf('-')+1)
             })
         }
        let _that = this;
        if (!_that.data.click) {
            _that.setData({
                click: true,
            })
        }

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
    // 点击了立即投递
    touchphoto(e) {
        const resumeUrl = wx.getStorageSync('resumeUrl')
        if (resumeUrl == null || resumeUrl == '') {
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
            success: function (res) {
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
    async getJobDetail(jobId) {
        const result = await request({ url: "/home/jobdata", data: { jobId } });
        console.log(result);
        this.jobInfoStorage = result.list[0];
        // 1 获取缓存中的商品收藏的数组
        let collects = wx.getStorageSync("collects") || [];
        // 判断当前商品是否被收藏
        let isCollect = collects.some(v => v.jobId === this.jobInfoStorage.jobId);
        this.setData({
            jobObj: result.list[0],
            isCollect
        })
    },
    // 获取相似职位详情数据
    async getSimilarJobDetail(jobId) {
        const { isSimilar } = "1";
        const result = await request({ url: "/home/jobdata", data: { jobId, isSimilar } });
        this.setData({
            similarJobs: result.list
        })
    },
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
})
// 表单提交，上传文件
function formSubmit() {
    var photo = [out_photo];
    httputil.send_photo(photo, function (res) {
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