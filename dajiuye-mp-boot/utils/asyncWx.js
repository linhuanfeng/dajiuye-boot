/*
promise 形式 getSetting
*/
export const getSetting = () => {
        return new Promise((resolve, reject) => {
            wx.getSetting({
                success: (result) => {
                    resolve(result)
                },
                fail: (err) => {
                    reject(err);
                },
                complete: () => {}
            });

        })
    }
    /*
    promise 形式 chooseAddress
    */
export const chooseAddress = () => {
        return new Promise((resolve, reject) => {
            wx.chooseAddress({
                success: (result) => {
                    resolve(result)
                },
                fail: (err) => {
                    reject(err);
                },
                complete: () => {}
            });

        })
    }
    /*
    promise 形式 openSetting
    */
export const openSetting = () => {
    return new Promise((resolve, reject) => {
        wx.openSetting({
            success: (result) => {
                resolve(result)
            },
            fail: (err) => {
                reject(err);
            },
            complete: () => {}
        });

    })
}

/*promise 形式showModal
@param {object} param0 参数
*/
export const showModal = ({ content }) => {
    return new Promise((resolve, reject) => {
        wx.showModal({
            title: '提示',
            content: '您是否要删除？',
            success: (result) => {
                resolve(result);
            },
            fail: (err) => {
                reject(err);
            },
            complete: () => {}
        });
    })
}

/*promise showToast
@param {object} param0 参数
*/
export const showToast = ({ title }) => {
    return new Promise((resolve, reject) => {
        wx.showToast({
            title: title,
            content: 'none',
            success: (result) => {
                resolve(result);
            },
            fail: (err) => {
                reject(err);
            },
            complete: () => {}
        });
    })
}