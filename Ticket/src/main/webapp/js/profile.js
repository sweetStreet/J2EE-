window.onload=function(){
    var ui =document.getElementById("rePassword");
    ui.style.display = 'none';
}
function showRePassword(){
    var ui =document.getElementById("rePassword");
    ui.style.display = 'block'
}

function isValid(form){
    if (form.password.value!=form.rePassword.value)
    {
        alert("两次输入的密码不同！");
        return false;
    }
    else return true;
}

function stopAccount(){

//        swal({
//            title: "确定注销吗？",
//            text: "你将无法恢复该账号！",
//            type: "warning",
//            showCancelButton: true,
//            confirmButtonColor: "#DD6B55",
//            confirmButtonText: "确定删除！",
//            closeOnConfirm: false
//        }, function(isConfirm){
//
//            $.ajax({url:"deleteAccount",success:function(result){
//                swal({
//                    title: result,
//                    timer: 2000,
//                    showConfirmButton: false
//                });
//                window.location.href = "login";
//            }});
//        });

    swal({
        title: "确定注销吗?",
        text: "你将无法恢复该账号！",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            $.ajax({url:"deleteAccount",success:function(result){
                swal({
                   title: "注销成功",
                   timer: 1500,
                   showConfirmButton: false
                });
                window.location.replace ("login");
            }});
        } else {
        }
});

}
