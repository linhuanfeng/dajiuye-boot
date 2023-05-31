const constants = require('../common/constants')
function send_photo(data, successfun) {
    console.log("in send_photo");
    console.log(data)
    for (let i = 0; i < data.length; i++) {
        console.log("data长度=" + data.length)
        console.log(data)
        wx.uploadFile({
            url: constants.API_BASE_URL+"/user/oss/all", //仅为示例，非真实的接口地址 自己写映射你Java接口的路径
            filePath: data[i] + "",
            formData: {
                openId: wx.getStorageSync("userInfo").openId,
                fileName:wx.getStorageSync('tempFiles').name
              },
            name: 'all',
            success: function(res) {
                console.log(res)
                wx.setStorageSync("resumeUrl",res.data)
                successfun(res)
            }
        })
    }
}
module.exports = {
    send_photo: send_photo
}