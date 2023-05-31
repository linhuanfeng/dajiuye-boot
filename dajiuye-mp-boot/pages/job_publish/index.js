import {
    request
} from "../../requests/index.js";
const app = getApp()
Page({
    /**
     * 页面的初始数据
     */
    data: {
        globalid: [],
        sex: 0,
        visitCausearray: ['不限', '大专', '本科', '硕士', '博士'],
        index1: '0',
        visitCause: 0,
        organarray: ['实习', '校招', '社招'],
        index2: '0',
        visitOrganId: 0,
        name: '',
        phone: '',
        idNumber: '',
        subscribeTime: '',
        visitCompany: '',
        interviewee: '',
        visitOrgan: '',
        show: false,
        currentChoose: '',
    },
    // 封装职位2信息
    job: {
        jobName: '',
        jobIndustry: '',
        jobPlace: '',
        jobEdu: '不限',
        jobDetails: '',
        jobSalary: '',
        email: '',
        jobType: '1',
        jobComId: '',
        jobAuthorId: ''
    },
    header: {
        token: 'asda',
        apiIdempotentToken: 'asda'
    },
    onLoad: function (options) {
        this.header.token = app.globalData.token
    },

    async onShow(e) {
        var that = this
        // 获取当前页面token,实现接口幂等性
        const result = await request({
            url: "/idempotent/token/create",
            header: that.header
        });
        console.log("得到当前页面的token:" + result)
        this.header.apiIdempotentToken = result
    },
    onConfirm(e) {
        this.setData({
            show: false,
            currentChoose: this.formatDate(new Date(e.detail))
        })
    },
    onClose() {
        this.setData({
            show: false
        })
    },
    onCancel() {
        this.setData({
            show: false
        })
    },
    // 学历要求
    bindPickerJobEdu: function (e) {
        console.log('picker发送选择改变，携带值为', e.detail.value)
        const index = e.detail.value
        this.setData({
            index1: index
        })
        if (index == 0) {
            this.job.jobEdu = '不限'
        } else if (index == 1) {
            this.job.jobEdu = '大专'
        } else if (index == 2) {
            this.job.jobEdu = '本科'
        } else if (index == 3) {
            this.job.jobEdu = '硕士'
        } else if (index == 4) {
            this.job.jobEdu = '博士'
        }
    },
    // 职位分类
    bindPickerJobType: function (e) {
        console.log('picker发送选择改变，携带值为', e.detail.value)
        const index = e.detail.value
        this.setData({
            index2: index
        })
        const i = parseInt("10", index) + 1
        console.log(i)
        this.job.jobType = i;
    },
    setInput: function (e) {
        console.log(e)
        const {
            name
        } = e.target.dataset
        this.job[name] = e.detail.value
        console.log(this.job)
    },
    async setJobComId() {
        const userInfo = wx.getStorageSync("userInfo")
        if (userInfo) {
            this.job.jobComId = userInfo.comId
            this.job.jobAuthorId = userInfo.id
        } else {
            console.log("jobPublish.setJobComId()-->useinfo为null")
        }
    },
    // 保存职位
    async confirmPublish() {
        var that = this
        this.setJobComId()
        let result = await request({
            url: "/job/job/saveJob",
            data: this.job,
            method: 'POST',
            header: that.header
        });
        if (!'success'.localeCompare(result)) {
            wx.showToast({
                title: '发布职位成功',
                icon: 'success',
                // duration: 2000
                mask:true
            })
            setTimeout(function(){
                wx.hideLoading({
                  success: (res) => {
                    // wx.navigateBack({
                    //     delta: 0,
                    // })
                  },
                })
            },2000)
        } else {
            wx.showToast({
                title: '发布职位失败:'+result,
                icon: 'fail',
                duration: 2000,
                mask:true,
            })
        }
    }
})