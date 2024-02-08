Page({
    data: {
        flag: true,
        choose_message_1: "",
        choose_message_2: "",
        choose_message_3: "",
        choose_message_4: "",
        right_num: 0,
        choose_arr: ["图书馆", "火之舞", "教学楼", "大学生活动中心", "菜鸟驿站", "音乐喷泉", "篮球场", "校车", "食堂"],
        right_choose: "菜鸟驿站",
        choose1: "",
        choose2: "",
        choose3: "",
        choose4: "",
        right_view: false,
        wrong_view: false,
        next_button: false,
    },

    onLoad() {
        var that = this
        if(that.data.flag) {
            that.setData({
                flag: false
            })
            that.right_choose()
        }
    },

    right_choose() {
        var that = this
        var x = Math.floor(Math.random()*4) + 1
        if(x == 1) {
            that.setData({
                choose_message_1: that.data.right_choose,
                choose1: "right",
                right_num: 1
            })
        }
        
        else if(x == 2) {
            that.setData({
                choose_message_2: that.data.right_choose,
                choose2: "right",
                right_num: 2
            })
        }

        else if(x == 3) {
            that.setData({
                choose_message_3: that.data.right_choose,
                choose3: "right",
                right_num: 3
            })
        }

        else if(x == 4) {
            that.setData({
                choose_message_4: that.data.right_choose,
                choose4: "right",
                right_num: 4
            })
        }

        for(var i = 0; i < that.data.choose_arr.length; i++) {
            if(that.data.choose_arr[i] == that.data.right_choose) {
                that.data.choose_arr.splice(i, 1)
            }
        }
        that.other_choose()
    },

    other_choose() {
        var that = this
        var arr = that.data.choose_arr
        var result = []
        var other_num = 3
        for(var i = 0; i < other_num; i++) {
            var t = Math.floor(Math.random() * (arr.length - i))
            result.push(arr[t])
            arr[t] = arr[arr.length - i - 1]
        }
        
        if(that.data.right_num == 1) {
            that.setData({
                choose2: "wrong",
                choose3: "wrong",
                choose4: "wrong",
                choose_message_2: result[0],
                choose_message_3: result[1],
                choose_message_4: result[2]
            })
        }

        else if(that.data.right_num == 2) {
            that.setData({
                choose1: "wrong",
                choose3: "wrong",
                choose4: "wrong",
                choose_message_1: result[0],
                choose_message_3: result[1],
                choose_message_4: result[2]
            })
        }

        else if(that.data.right_num == 3) {
            that.setData({
                choose1: "wrong",
                choose2: "wrong",
                choose4: "wrong",
                choose_message_1: result[0],
                choose_message_2: result[1],
                choose_message_4: result[2]
            })
        }

        else if(that.data.right_num == 4) {
            that.setData({
                choose1: "wrong",
                choose2: "wrong",
                choose3: "wrong",
                choose_message_1: result[0],
                choose_message_2: result[1],
                choose_message_3: result[2]
            })
        }
    },

    right() {
        var that = this
        that.setData({
            right_view: true,
            next_button: true
        })
        that.right_text()
    },

    wrong() {
        var that = this
        that.setData({
            wrong_view: true,
            next_button: true
        })
        that.wrong_text()
    },

    next() {
        var that = this
        that.setData({
            wrong_view: false,
            next_button: false,
            right_view: false
        })
        wx.navigateTo({
          url: '../answer/answer',
        })
    },

    right_text() {
        var that = this
        var i = 0
            var time = setInterval(function(){
                var text = that.selectComponent("#right").data.message.substring(0, i)
                i++
                that.selectComponent("#right").setData({
                    text: text
                })
                if(text.length == that.selectComponent("#right").data.message.length) {
                    clearInterval(time)
                }
            } , 100)
    },

    wrong_text() {
        var that = this
        var i = 0
            var time = setInterval(function(){
                var text = that.selectComponent("#wrong").data.message.substring(0, i)
                i++
                that.selectComponent("#wrong").setData({
                    text: text
                })
                if(text.length == that.selectComponent("#wrong").data.message.length) {
                    clearInterval(time)
                }
            } , 100)
    },
})