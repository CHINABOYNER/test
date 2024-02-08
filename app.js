App({
    onLaunch: function() {
        const that = this
        const systemInfo = wx.getSystemInfoSync()
        const menuButtonInfo = wx.getMenuButtonBoundingClientRect()
        that.globalData.navBarHeight = systemInfo.statusBarHeight + 44
        that.globalData.menuRight = systemInfo.screenWidth - menuButtonInfo.right
        that.globalData.menuTop=  menuButtonInfo.top
        that.globalData.menuHeight = menuButtonInfo.height
    },

    globalData: {
        navBarHeight: 0, // 导航栏高度
        menuRight: 0, // 胶囊距右方间距（方保持左、右间距一致）
        menuTop: 0, // 胶囊距底部间距（保持底部间距一致）
        menuHeight: 0, // 胶囊高度（自定义内容可与胶囊高度保证一致）
        num: 1,
        message: false,
        right_num: 0,
        wrong_num: 0,
        score: 0,
        baseURL: "http://127.0.0.1"
    }
})
