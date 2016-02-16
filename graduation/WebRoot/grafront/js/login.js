$(document).ready(function() {
  $("#userName").focus();

  $(".login-btn").click(function(events) {
    events.preventDefault(); 
    var nameVal = $("#userName").val(),
        pwVal = $("#userPw").val();

    $.ajax({
      url: "/html/login.php",
      type: "post",
      dataType: "json",
      data: {
        name: nameVal,
        pw: pwVal
      },
      success: function(data) {
        if(!data) {
          $(".info-alert").show();
        } else {
          location.href = "/html/manage.php";
        }
      }
    });
  });
});
