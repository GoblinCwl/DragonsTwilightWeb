$(document).ready(function () { //jQuery 屏蔽右键
    $(document).bind("contextmenu", function (e) {
        return false;
    });
});
// $(document).ready(function () { //jQuery 屏蔽F5
//     $(document).bind("keydown", function (e) {
//         e = window.event || e;
//         if (e.keyCode === 116) {
//             e.keyCode = 0;
//             return false;
//         }
//     });
// });
$(document).ready(function () { //jQuery 屏蔽F7
    $(document).bind("keydown", function (e) {
        e = window.event || e;
        if (e.keyCode === 118) {
            e.keyCode = 0;
            return false;
        }
    });
});
$(document).ready(function () { //jQuery 屏蔽F11
    $(document).bind("keydown", function (e) {
        e = window.event || e;
        if (e.keyCode === 122) {
            e.keyCode = 0;
            return false;
        }
    });
});
$(document).ready(function () { //jQuery 屏蔽F12
    $(document).bind("keydown", function (e) {
        e = window.event || e;
        if (e.keyCode === 123) {
            e.keyCode = 0;
            return false;
        }
    });
});
