$(function () {
    $(function () {
        var email = getCookie("wkbemail");
        $("#email").val(email);
    });

    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toGMTString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
    }

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i].trim();
            if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
        }
        return "";
    }


    $('#upbtn').click(function () {
        var docid = $("#docid").val();
        var email = $("#email").val();
        var reg = /^\w+\@+[0-9a-zA-Z]+\.(com|com.cn|edu|hk|cn|net)$/;
        if (!reg.test(email)) {
            layer.open({
                type: 0,
                title: false,
                closeBtn: 0,
                shadeClose: true,
                skin: '',
                content: '邮箱格式不正确'
            });
            return;
        }
        var reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
        if (!reg.test(docid)) {
            layer.open({
                type: 0,
                title: false,
                closeBtn: 0,
                shadeClose: true,
                skin: '',
                content: '请检查您的下载连接'
            });
            return;
        }
        setCookie("wkbemail", email,30);
        $.post("/download/email", {
                docid: docid,
                email: email
            },
            function (data) {
                if (data.indexOf("http") == 0) {
                    $("#upbtn").attr("disabled", true);
                    $.post(data, {
                        'username': '',
                        'password': '',
                        'txtUrl': docid,
                        'mail': email
                    }, function (data) {
                    }, "text");

                    layer.open({
                        type: 0,
                        title: false,
                        closeBtn: 0,
                        shadeClose: true,
                        skin: '',
                        content: "提交成功，请耐心等待一分钟"
                    });
                    setTimeout("$('#upbtn').attr('disabled', false)", 10000);
                } else {
                    layer.open({
                        type: 0,
                        title: false,
                        closeBtn: 0,
                        shadeClose: true,
                        skin: '',
                        content: data
                    });
                }
            }, "text"
        );
    });
})