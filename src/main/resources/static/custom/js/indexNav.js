$(function () {
    if ($("#loadSideNav").text() === "1") {
        $.ajax({
            type: 'GET',
            url: getRootPath() + '/getSideNavList',
            success: function (res) {
                $("#sideNav").html(res);
                //添加首页选中样式
                var activeId = 7;
                $("#navLi" + activeId, parent.document).addClass('liCheck');
                $("#navA" + activeId, parent.document).addClass('aCheck');
                //更新服务器状态
                pullServerSts();
            }
        })
    }

    //更新侧边栏CSS样式
    updateCss();
    //定时更新服务器状态
    setInterval(pullServerSts, 10000);


});

//填充服务器状态
function pullServerSts() {
    let url = window.location.href;
    //过滤对goUrl跳转时进行的调用
    if (url.indexOf("?goUrl=") === -1) {
        $.ajax({
            type: 'GET',
            url: getRootPath() + '/serverStatus',
            success: function (res) {
                res = JSON.parse(res);
                const data = res.data;

                let serverSpan = $("#serverSpan");
                if (data.status === true) {
                    $("#serverImg").attr("src", "/images/serverEnabled.png");
                    serverSpan.css("color", "#a1ffc1");
                } else {
                    $("#serverImg").attr("src", "/images/serverClose.png");
                    serverSpan.css("color", "#ff8a84");
                }
                if (data.current_players > 9) {
                    serverSpan.text(data.current_players);
                } else {
                    serverSpan.text("\xa0" + data.current_players);
                }
            }
        })
    }
}

//更新导航栏CSS样式
function updateCss() {
    let activeId = $("#actionId").text();
    //删除旧样式
    $(".liCheck", parent.document).removeClass('liCheck');
    $(".aCheck", parent.document).removeClass('aCheck');
    //添加选中样式
    $("#navLi" + activeId, parent.document).addClass('liCheck');
    $("#navA" + activeId, parent.document).addClass('aCheck');
}

//改变iframe内容
function changeSrc(url) {
    if (url === "#") {
        return;
    }
    const iframe = document.getElementById("mainFrame");
    iframe.src = encodeURI(url);
}

//加载iframe时动画
function stateChangeFirefox(_frame) {
    const loader = document.getElementById("LoadDiv");
    loader.style.display = "none";
    _frame.style.visibility = "visible";
    _frame.style.display = "block";
}