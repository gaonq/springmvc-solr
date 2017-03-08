/**
 * Created by gaonq on 2017/3/5.
 */
$(function(){
    $("#btnSearch").click(
        function () {
            $("#list").html("");
            $.ajax({
                url:"/doSearch",
                type:"post",
                dataType:"json",
                data:{
                    "keyword":$("#keyword").val(),
                    "searchtype":$("#searchtype").val()
                },
                async:false,
                success:function (data) {
                    //var objs = jQuery.parseJSON(data);
                    var str = "";
                    for(var i=0;i<data.length;i++){
                        if(i == 0)
                        {
                            $("#keywordTxt").html(data[i].keyword);
                        }
                        else if(i == 1)
                        {
                            $("#resultCount").html(data[i].resultCount);
                        }
                        else if(i == 2)
                        {
                            $("#qtime").html(data[i].qtime);
                        }else{
                            if($("#searchtype") == "database"){
                                str = str + "<li  class=\"list-group-item\">"+ i +"、"+data[i].exception+"</li>";
                            }
                            else
                            {
                                str = str + "<li  class=\"list-group-item\">"+ i +"、"+data[i].attr_stream_name+"</li>";
                            }
                        }
                    }
                    $("#list").html(str);
                },
                error:function () {
                    alert("error");
                }
            });
        }
    );

    $("#btnAddDocument").click(function () {
       $.ajax(
           {
               url: "/add",
               type: "post",
               dataType: "json",
               data: {},
               async: false,
               success: function (data) {
                   if(data.status == "0")
                   {
                       alert("索引成功");
                   }
                   else
                   {
                       alert("索引失败");
                   }
               },
               error: function () {
                   alert("error");
               }
           }
       );
    });
});
