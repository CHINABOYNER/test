// components/wrong/wrong.js
Component({
    /**
     * 组件的属性列表
     */
    properties: {

    },

    /**
     * 组件的初始数据
     */
    data: {
        text: "",
        message: "答错了，好可惜.."
    },

    /**
     * 组件的方法列表
     */
    methods: {
        onLoad() {
            var that = this
            var i = 0
            var time = setInterval(function(){
                var text = that.data.message.substring(0, i)
                i++
                that.setData({
                    text: text
                })
                if(text.length == that.data.message.length) {
                    clearInterval(time)
                }
            } , 100)
        }
    }
})
