$(document).ready(function() {
  $(".add-manager-btn").click(function(events) {
    events.preventDefault(); 
    var nameVal = $("#userName").val(),
        pwVal = $("#userPw").val(),
        pwAgainVal = $("#userPwAgain").val();

    if(pwVal !== pwAgainVal) {
      $(".info-alert ").show();
    } else {
      $(".info-alert ").hide();

      $.ajax({
        url: "/html/add_manager_back.php",
        type: "post",
        dataType: "json",
        data: {
          name: nameVal,
          pw: pwVal
        },
        success: function(data) {
          if(data) {
            alert("Ìí¼Ó³É¹¦£¡");
            location.reload();
          }
        }
      });// END ajax
    }
  });
});
