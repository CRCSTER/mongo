$(document).ready(function() {
  $(".change-pw-btn").click(function(events) {
    events.preventDefault(); 
    var pwVal = $("#userPw").val(),
        pwAgainVal = $("#userPwAgain").val();

    if(pwVal !== pwAgainVal) {
      $(".info-alert ").show();
    } else {
      $(".info-alert ").hide();

      $.ajax({
        url: "/html/change_pw_back.php",
        type: "post",
        dataType: "json",
        data: {
          pw: pwVal
        },
        success: function(data) {
          if(data) {
            alert("修改成功，请重新登陆");
            location.href = "/html/logout.php";
          }
        }
      });// END ajax
    }
  });
});
