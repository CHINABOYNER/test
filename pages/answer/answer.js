// 图书馆 ：#DFFFF7  #89E4CD
// 火之舞 ：#FF7272  #E62E2E
// 教学楼 ：#BCBFF9  #6F74DB
// 大学生活动中心 ：#FFF4CF  #DFC87A
// 菜鸟驿站 ：#DEEAFF  #8EAEE6
// 音乐喷泉 ：#FCECEB  #E0A19D
// 篮球场 ：#E1C2A0  #CA9963
// 校车 ：#E7F28E  #BECD46
// 食堂 ：#DFECFF  #92B4E4
const app = getApp()

Page({
    data: {
        background: "#DEEAFF",
        icon: "https://iashaw-1318257216.cos.ap-shanghai.myqcloud.com/MDYY/location_icon/icon_cainiaoyizhan.png",
        color: "#8EAEE6",
        image: "https://iashaw-1318257216.cos.ap-shanghai.myqcloud.com/MDYY/answer_image/cainiaoyizhan.png",
        answer_icon: "https://iashaw-1318257216.cos.ap-shanghai.myqcloud.com/MDYY/answer_icon/cnyz.png",
        message: "人生三大乐事之吃饭睡觉拆快递，快递能留到第二天不拆的，本菜鸟觉得都是不懂得寻找乐趣的人。",
        click_message_1: "今天你有没有快递呀?",
        click_message_2: "加油，后面的题也要认真回答，本菜鸟相信你",
        click_message_3: "还有什么事吗？",
        click_message_4: "快点去答题吧，再点我就生气了(ꐦ°᷄д°᷅)",
        text: "",
        flag: false,
        button_text: "下一题",
        src: "../play_music/play_music",
        click_num: 1
    },

    onLoad() {
        var num = app.globalData.num
        var that = this
        var i = 0
        var time = setInterval(function(){
            var text = that.data.message.substring(0, i)
            i++
            that.setData({
                text: text
            })
            if(text.length == that.data.message.length) {
                that.setData({
                    flag: true
                })
                clearInterval(time)
            }
        },100)
        if(num >= 10) {
            that.setData({
                button_text: "完成",
                src: "",
            })
        }
    },

    next() {
        var that = this
        app.globalData.num ++
        console.log(app.globalData.num)
        wx.navigateTo({
          url: that.data.src,
        })
    },

    click_icon() {
        var that = this
        var i = 0
        if(that.data.flag) {
            var time = setInterval(function(){
                var text = that.data.click_message_4.substring(0, i)
                i++
                that.setData({
                    text: text
                })
                if(text.length == that.data.click_message_4.length) {
                    that.data.num ++
                    clearInterval(time)
                }
            },100)
        }
    }
})