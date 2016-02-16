var data = [
    {
    "ip": "127.0.0.1",
    "method": "post",
		"kbs":"1231",
		"state":"200"
    },
    {
    "ip": "127.0.0.1",
    "method": "get",
		"kbs":"1231",
		"state":"404"
    },
    {
    "ip": "127.0.0.1",
    "method": "get",
		"kbs":"1231",
		"state":"304"
    },
    {
    "ip": "127.0.0.1",
    "method": "get",
		"kbs":"1231",
		"state":"200"
    }];

$(document).ready(function() {
  $.ajax({
    url: "./data",
    type: "get",
    dataType: "text",
    success: function(data) {
      data = eval(data);
      var num200 = 0,
          num404 = 0,
          num304 = 0;

      var tableStr = "";

      for(var i = 0; i < data.length; i++) {
        tableStr += 
          "<tr>" +
            "<td>" + data[i].ip + "</td>" +
            "<td>" + data[i].method + "</td>" +
            "<td>" + data[i].kbs + "</td>" +
            "<td>" + data[i].state + "</td>" +
          "</tr>";

        if(data[i].state === '200') {
          num200++;
        } else if(data[i].state === '404') {
          num404++;
        } else {
          num304++;
        }
      } 

      var drawData = [
        {name: '200', value: num200, color: '#a5c2d5'},
        {name: '404', value: num404, color: '#cbab4f'},
        {name: '304', value: num304, color: '#76a871'}
      ];

      var chart = new iChart.Column2D({
        render : 'canvasDiv',//渲染的Dom目标,canvasDiv为Dom的ID
        data: drawData,//绑定数据
        title : '详细数据',//设置标题
        width : 800,//设置宽度，默认单位为px
        height : 400,//设置高度，默认单位为px
        shadow:true,//激活阴影
        shadow_color:'#c7c7c7',//设置阴影颜色
        coordinate:{//配置自定义坐标轴
          scale:[{//配置自定义值轴
            position:'left',//配置左值轴	
            start_scale:0,//设置开始刻度为0
            end_scale: Math.max(num200, num404, num304) + 5,
            scale_space:5,//设置刻度间距
            listeners:{//配置事件
              parseText:function(t,x,y){//设置解析值轴文本
                return {text:t}
              }
            }
          }]
        }
      });

      //调用绘图方法开始绘图
      chart.draw();
      $("#drawTable tbody").html(tableStr); 
    }
  });
});
