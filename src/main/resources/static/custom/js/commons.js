//获取服务器原始地址
function getRootPath() {
    return window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + "/";
}

//日期加减法
function addDate(date, days) {
    let d = new Date(date);
    d.setDate(d.getDate() + days);
    let m = d.getMonth() + 1;
    if (m < 10) {
        m = '0' + m;
    }
    let resultD = d.getDate();
    if (resultD < 10) {
        resultD = '0' + resultD;
    }
    return d.getFullYear() + '-' + m + '-' + resultD;
}

function addDateNoYear(date, days) {
    let d = new Date(date);
    d.setDate(d.getDate() + days);
    let m = d.getMonth() + 1;
    if (m < 10) {
        m = '0' + m;
    }
    let resultD = d.getDate();
    if (resultD < 10) {
        resultD = '0' + resultD;
    }
    return m + '-' + resultD;
}

