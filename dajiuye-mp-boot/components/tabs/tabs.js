// components/tabs/tabs.js
Component({
    /**
     * 组件的属性列表
     */
    properties: {
        // 父接受父亲传过来的数据
        tabs: {
            type: {
                type: Array,
                value: []
            }
        }
    },

    /**
     * 组件的初始数据
     */
    data: {

    },

    /**
     * 组件的方法列表
     */
    methods: {
        handleItemTap(e) {
            // 点击事件
            const { index } = e.currentTarget.dataset;
            // 2 触发 父组件的事件 自定义
            this.triggerEvent("tabsItemChange", { index });
        },
        onPageScroll(e) {
            console.log(e);
        },
        meth1() {
            this.setData({
                curClass: 'item_fix'
            })
        },
        meth2() {
            this.setData({
                curClass: ''
            })
        }
    }
})