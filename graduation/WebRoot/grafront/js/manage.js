$(document).ready(function() {
  $(".student-search-btn").click(function(events) {
    events.preventDefault(); 
    var $kindVal = $(".student-search").val(),
        $kind = $("#searchKind")[0].options[$("#searchKind")[0].selectedIndex].value;

    $.ajax({
      url: "/html/student_search.php",
      type: "post",
      dataType: "json",
      data: {
        kindVal: $kindVal,
        kind: $kind
      },
      success: function(data) {
        var tableStr = "";

        if(data) {
          for(var i = 0; i < data.length; i++) {
            tableStr += '<tr>' +
                          '<td>' + data[i].number + '</td>' +
                          '<td>' + data[i].name + '</td>' +
                          '<td>' + data[i].week + '</td>' +
                          '<td>' + data[i].count + '</td>' +
                          '<td>' + data[i].teacher + '</td>' +
                          '<td>' + data[i].tip + '</td>' +
                          '<td>' + data[i].theory + '</td>' +
                          '<td>' + data[i].standard + '</td>' +
                          '<td>' + data[i].operate + '</td>' +
                          '<td>' + data[i].error + '</td>' +
                          '<td>' + data[i].debug + '</td>' +
                          '<td>' + data[i].report + '</td>' +
                          '<td>' + data[i].tool + '</td>' +
                          '<td>' + (parseFloat(data[i].theory) + parseFloat(data[i].standard) + parseFloat(data[i].operate) + parseFloat(data[i].error) + parseFloat(data[i].debug) + parseFloat(data[i].report) + parseFloat(data[i].tool)) + '</td>' +
                          '<td><a href="./edit_student.php?id=' + data[i].number + '">编辑</a></td>' +
                        '</tr>';
          }
        }

        $(".table tbody").html(tableStr);
      }
    });// END ajax
  });// END click

  $(".student-all-btn").click(function(events) {
    events.preventDefault(); 

    location.reload();
  });
});
