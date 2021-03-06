/**
 * Created by gaonq on 2017/3/6.
 */


Vue.component('tolist',{
    template: '<li>This is a todo</li>'
})
var app = new Vue({
    el: '#app',
    data:{
        message:'hello Vue!',
        id:'formid',
        password:'password',
        html:'<input id="test" type="text"/>',
        users:[{
            username:'gaonengquan',
            sex:'男'
        },{ 
            username:'gao',
            sex:'男'
        }]
    },
    filters:{
        sex:function (data) {
            if(data == 'nan'){
                return "男";
            }
            else
            {
                return "女";

            }
        }
    },
    computed:{
      reverseMessage:function () {
          alert('reverseMessage');
          return this.password.split("").reverse().join("");
      }
    },
    components:{
        testtemplate: {
            props:['messageNotify'],
            template: '<div>A custom component! {{ messageNotify }}</div>'
        }
    },
    watch:{
        message:function () {
            alert('message');
            //this.message = 'watch';
        }
    },
    methods:{
        login:function () {
           return alert(this.message);
        }
    },
    created:function () {
        //alert('vue init ok ')
    },
    beforeCreate:function () {
        //alert('vue is beforeCreate')
    },
    beforeMount:function () {
      //alert('vue is beforeMount');
    },
    mounted:function () {
        //alert('vue is mounted ');
    },
    beforeDestory:function () {
       // alert('vue is beforeDestory');
    },
    beforeUpdate:function () {
     // alert('vue is beforeUpdate')
    },
    update:function () {
       // alert('vue is update')
    },
    destroyed:function () {
       // alert('vue is destroyed')
    }
});