import { request } from "../../requests/index.js";
var app = getApp() // 获取全局app对象
Page({

    /**
     * 页面的初始数据
     */
    data: {
        submit_stat: '不合适',
        deliverId:'',
        cuUser:{},
        click: false, //是否显示弹窗内容
        option: false, //显示弹窗或关闭弹窗的操作动画
        resumeUrl: {}, // 简历的相关信息
        resumeTime: {},
        resumeName: ''                                                   
    },
    // 发送投递简历
    Userdeliver: {
        id: '',
        state: '',
        message: ''
    },
    header: {
        token: 'asda'
    },
    // 工作信息全局对象
    jobInfoStorage: {},
    onLoad: function (options) {
        this.header.token = app.globalData.token
        this.setData({
            deliverId: options.jobId
        });
        this.Userdeliver.id=options.jobId
        const userList=wx.getStorageSync("userList") || [];
        var that=this;
        userList.forEach(function(item, index){
            if(item.tempDeliverId==that.data.deliverId){
                that.setData({
                    cuUser:item
                })
                that.Userdeliver.id=item.tempDeliverId
                const resumeUrl = item.resume
                if (resumeUrl) {
                    that.setData({
                        resumeUrl,
                        resumeTime: resumeUrl.substring(resumeUrl.lastIndexOf('_') + 1, resumeUrl.lastIndexOf('-')),
                        resumeName: resumeUrl.substring(resumeUrl.lastIndexOf('-') + 1)
                    })
                }else{
                    console.log("用户简历为空")
                }
                return;
            }
          })
    },
    // 用户点击显示弹窗
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
    //  唤醒弹出层
    resumeSubmit(e) {
        const state = e.currentTarget.dataset.state
        this.Userdeliver.state = state;
        if (state == 1) {
            this.setData({
                submit_stat: '待定',
            })
        } else if (state == 2) {
            this.setData({
                submit_stat: '简历通过',
            })
        } else {
            this.setData({
                submit_stat: '不合适',
            })
        }
        this.clickPup()
    },
    // 文本域的输入事件
    handleTextInput(e) {
        this.Userdeliver.message=e.detail.value
    },
    // 真正发送请求
    async sendResume() {
        var that=this
        const result = await request({ url: "/user/deliver/updateDeliver", data: this.Userdeliver,header:that.header });
        console.log("hr审核简历后")
        console.log(result)
        // 关闭弹出窗
        this.clickPup()
        wx.showToast({
            title: '审核成功',
            icon: 'none',
            image: '',
            duration: 1000,
            mask: false,
        });
    }
})