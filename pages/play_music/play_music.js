const app = getApp()
const audio = wx.createInnerAudioContext()

Page({
    data: {
        music_src: app.globalData.baseURL + "/music1.mp3",
        animation: "rotate 3s linear infinite",
        num: 0,
        right_num: 0,
        wrong_num: 0,
        score: 0,
        play_icon: "../../images/zanting.png",
        play_bind: "playMusic",
        bitworkshop: "bitworkshop_icon",
        num: 1
    },

    onLoad() {
        var that = this
        audio.src = that.data.music_src
        that.setData({
            num: app.globalData.num,
            right_num: app.globalData.right_num,
            wrong_num: app.globalData.wrong_num,
            score: app.globalData.score
        })
    },

    playMusic() {
        var that = this
        that.setData({
            play_icon: "../../images/bofang.png",
            play_bind: "pauseMusic",
            bitworkshop: "bitworkshop_icon select"
        })
        audio.play()
    },

    pauseMusic() {
        var that = this
        that.setData({
            play_icon: "../../images/zanting.png",
            play_bind: "playMusic",
            bitworkshop: "bitworkshop_icon"
        })
        audio.pause()
    },

    rePlay() {
        var that = this
        audio.seek(0)
    },

    next() {
        wx.navigateTo({
          url: '../../pages/choose/choose',
        })
    },

    nextMusic() {
        audio.pause()
        var that = this
        var nowURL = "/music" + (that.data.num + 1) + ".mp3"
        that.data.num += 1
        that.setData({
            music_src: app.globalData.baseURL + nowURL
        })
        audio.src = that.data.music_src
        audio.play()
    }
})