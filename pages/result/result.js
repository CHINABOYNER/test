Page({
    data: {
        score: 100 + "分",
        title: "民大灵魂“听众”",
        message: "细心的同学，民大的这么多声音都被你捕捉到了，小特愿封你为民大的“灵魂听众”。",
    },

    toHome() {
        wx.navigateTo({
          url: '../home/home',
        })
    },

    toTop() {
        wx.navigateTo({
          url: '../charts/charts',
        })
    }
})