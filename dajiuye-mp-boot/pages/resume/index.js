const httputil = require("../../utils/httputil.js") //一定要引入，根据你自己写的上传文件路径
var out_photo = "";
import { request } from "../../requests/index.js";
var app=getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 存放解密得到的数据
        userInfo: {},
        photo: {},
        photoName: {},
        resumeUrl: {},
        resumeTime: {},
        resumeName: '',
        isLogin: false
    },
    header: {
        token: 'asda'
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.header.token = app.globalData.token
        const userInfo = wx.getStorageSync('userInfo')
        if (!userInfo) {
            wx.navigateBack({
                delta: 0,
            })
            wx.showToast({
                title: '请先登录',
                icon: 'none',
                image: '',
                duration: 1000,
                mask: false,
            });
        } else {
            this.setData({
                isLogin: true
            })
        }
    },
    onShow: function () {
        if (this.data.isLogin) {
            this.setUserInfo()
            // 从缓存判断是否有简历
            const resumeUrl = wx.getStorageSync('resumeUrl')
            if (resumeUrl) {
                this.setData({
                    resumeUrl,
                    resumeTime: resumeUrl.substring(resumeUrl.lastIndexOf('_') + 1, resumeUrl.lastIndexOf('-')),
                    resumeName: resumeUrl.substring(resumeUrl.lastIndexOf('-') + 1)
                })
            } else {
                // 没有发送请求，获取简历链接
                this.setUserResume(this.data.userInfo.openId)
            }
        }
    },
    setUserInfo() {
        const userInfo = wx.getStorageSync("userInfo")
        this.setData({
            userInfo
        })
    },
    // 更新简历的详细信息（resumeUrl，resumeTime，resumeName）
    async setUserResume(openId) {   
        var that=this
        const result = await request({ url: "/user/user/getUser", data:{openId},header:that.header});
        const resumeUrl=result.resume
        this.setData({
            resumeUrl,
            resumeTime:resumeUrl.substring(resumeUrl.lastIndexOf('_')+1,resumeUrl.lastIndexOf('-')),
            resumeName:resumeUrl.substring(resumeUrl.lastIndexOf('-')+1)
        })
        wx.setStorageSync('resumeUrl', result.resume)
    },
    // 1.选择要上传的文件
    touchphoto(e) {
        var that = this
        wx.chooseMessageFile({
            count: 1, // 默认9
            type: 'file',
            success: function (res) {
                console.log("选择图片成功后")
                wx.setStorageSync('tempFiles', res.tempFiles[0])
                console.log(res)
                // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
                var tempFilePaths = res.tempFiles[0].path
                that.setData({
                    photo: tempFilePaths,
                    photoName: res.tempFiles[0].name
                })
                that.setOutPhoto()
            }
        })
    },
    setResumeUrl() {
        const resumeUrl = wx.getStorageSync("resumeUrl");
        this.setData({
            resumeUrl
        })
    },
    // 2.
    setOutPhoto() {
        out_photo = this.data.photo
        formSubmit()
    },
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
    }
})
// 3.表单提交，上传文件
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
        // 更新简历的详细信息（resumeUrl，resumeTime，resumeName）
        getCurrentPages()[getCurrentPages().length - 1].setUserResume(getCurrentPages()[getCurrentPages().length - 1].data.userInfo.openId)
    })
}