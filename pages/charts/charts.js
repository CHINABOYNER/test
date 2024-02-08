const app = getApp()

Page({
    data: {
        navBarHeight: app.globalData.navBarHeight,
        menuRight: app.globalData.menuRight,
        menuTop: app.globalData.menuTop,
        menuHeight: app.globalData.menuHeight,
        firstName: "xxx",
        firstScore: 0,
        secondName: "xxx",
        secondScore: 0,
        thirdName: "xxx",
        thirdScore: 0,
        // testImage只用来测试用的图片，上线需要删掉
        testImage: "https://iashaw-1318257216.cos.ap-shanghai.myqcloud.com/MDYY/bitworkshop_logo.png"
    },

    back() {
        wx.navigateTo({
          url: '../home/home',
        })
    }
})