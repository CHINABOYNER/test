Page({
    data: {
        ready: true,
        back_button: false,
        show_message: false,
        show_rule: false,
    },

    login() {
        wx.navigateTo({
          url: '../play_music/play_music',
        })
    },

    toTop() {
        wx.navigateTo({
          url: '../charts/charts',
        })
    },

    onLoad() {
        var that = this
        var load = setInterval(function () {
            that.setData({
                ready: false
            })
            clearInterval(load)
        },2000)
    },

    show_message() {
        var that = this
        that.setData ({
            show_message: true,
            back_button: true
        })
    },

    show_rule() {
        var that = this
        that.setData ({
            show_rule: true,
            back_button: true
        })
        that.rule()
    },

    rule() {
        var that = this
        var text = setInterval(function() {
            that.selectComponent("#r").setData({
                show_text: true
            })
            clearInterval(text)
        } , 1500)
    },

    back() {
        var that = this
        that.setData ({
            show_message: false,
            show_rule: false,
            back_button: false
        })
    },
})