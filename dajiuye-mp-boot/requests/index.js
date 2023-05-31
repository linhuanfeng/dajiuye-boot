const constants = require('../common/constants')
// 同时发送异步请求的次数
let ajaxTimes = 0;
export const request = (params) => {
    // 显示加载中效果
    wx.showLoading({
        title: "加载中",
        mask: true,
        success: (result) => {

        },
        fail: () => {},
        complete: () => {}
    });
    return new Promise((resolve, rejects) => {
        ajaxTimes++,
        wx.request({
            ...params,
            method:params.method,
            url: constants.API_BASE_URL + params.url,
            header:params.header,
            success: (result) => {
                resolve(result.data.message)
            },
            fail: (err) => {
                rejects(err);
            },
            complete: () => {
                ajaxTimes--;
                if (ajaxTimes === 0) {
                    // 关闭正在等待的图标
                    wx.hideLoading();
                }
            }
        })
    })
}