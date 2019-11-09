$('.reply').hide();
var replyCount = 0;
var divCount = 0;
var nullCheck = localStorage.getItem('divCount');

console.log(nullCheck);
// if(nullCheck == NaN){
//     divCount = 0;
//     localStorage.setItem('divCount', divCount);
// }


$('#register').click(function () {
    if($('#description').val().trim().length == 0){
        alert("내용을 입력하세요.");
        $('#description').focus();
        return false;
    }

    var jsonData = JSON.stringify({
        description : $('#description').val()
    });

    $.ajax({
        url: "http://localhost:8080/toDoList",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            alert('등록 성공!');
            location.reload();
        },
        error: function () {
            alert('등록 실패!');
        }
    });
});


$('.delete').click(function () {
    var delete_id = $(this).val();

    $.ajax({
        url: "http://localhost:8080/toDoList/" + delete_id,
        type: "DELETE",
        contentType: "application/json",
        success: function () {
            // alert('삭제 성공!');
            location.href = '/toDoList/list';
        },
        error: function () {
            alert('삭제 실패!');
        }
    });
});


$('.replyDelete').click(function () {
    var delete_id = $(this).val();

    $.ajax({
        url: "http://localhost:8080/reply/" + delete_id,
        type: "DELETE",
        contentType: "application/json",
        success: function () {
            location.href = '/toDoList/list';
        },
        error: function () {
            alert('삭제 실패!');
        }
    });
});

$(document).on("click",".update",function(){
    var modified = $(this).parent().parent().find('.updateContent').text();

    var update_id = $(this).val();

    $.ajax({
        url: "http://localhost:8080/toDoList/" + update_id,
        type: "PUT",
        data: modified,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            alert('수정 성공!');
            location.reload();
        },
        error: function () {
            alert('수정 실패!');
        }
    });
});

$(document).on("click",".replyUpdate",function(){

    var modified = $(this).parent().parent().find('.replyContent').text();

    var update_id = $(this).val();

    $.ajax({
        url: "http://localhost:8080/reply/" + update_id,
        type: "PUT",
        data: modified,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            alert('수정 성공!');
            location.reload();
        },
        error: function () {
        }
    });
});

$('.complete').click(function () {
    var complete_id = $(this).val();

    $.ajax({
        url: "http://localhost:8080/toDoList/status/" + complete_id,
        type: "PUT",
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert('완료실패!');
        }
    });
});


$('.replyButton').click(function () {
    console.log("reply버튼 누름")
    var reply_id = $(this).val();
    var reply_parent_id = $(this).parent().parent().parent().find('.reply');
    var reply_parent_id2 = $(this).parent().parent().parent().find('.please3');
    reply_parent_id.fadeToggle();
    reply_parent_id2.fadeToggle();

    var jsonData = JSON.stringify({
        idx : reply_id
    });

    $.ajax({
        url: "http://localhost:8080/toDoList/checkIdx",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
        },
        error: function () {
            alert('실패!');
        }
    });
})


$('.replyRegister').click(function () {
    replyCount++;
    divCount++;
    localStorage.setItem('divCount', divCount);
    console.log(localStorage.getItem('divCount'))

    var reply_id2 = $(this).val();

    var reply_text = $('.textBox input').eq(reply_id2 - 1).val();

    console.log(reply_id2);
    var jsonData = JSON.stringify({

        content : reply_text
    });

    $.ajax({
        url: "http://localhost:8080/toDoList/postReply",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",

        success: function (data) {
            var createdDate = data.createdDate.substring(0,10);

            var node = document.createElement("dl");
            node.className = "deleteContent" + replyCount + " please3";

            node.setAttribute("value", replyCount);

            var node2 = document.createElement("dt");

            var textNode = document.createTextNode(data.content + " ");

            node2.appendChild(textNode);
            node2.setAttribute("contenteditable", "true");
            node2.setAttribute("class", "updateContent2");

            var node3 = document.createElement("dt");
            node3.className = "updateContent" + replyCount;

            var textNode2 = document.createTextNode(createdDate);

            node3.appendChild(textNode2);


            var node4 = document.createElement("dd");
            var deleteButton = document.createElement("button");
            deleteButton.setAttribute("type", "button");
            deleteButton.className="replyDelete2 btn-btn-default";
            deleteButton.setAttribute("id", "please2")
            deleteButton.setAttribute("value", data.idx);
            deleteButton.onclick = function (ev) {
                var deVal = $(this).val();
                console.log(deVal);

                $('.deleteContent' + deVal).remove();

                $.ajax({
                    url: "http://localhost:8080/reply/" + deVal,
                    type: "DELETE",
                    contentType: "application/json",
                    dataType: "json",
                    success: function () {
                        alert('삭제 성공!');
                    },
                    error: function () {
                    }
                });
            }

            var deleteI = document.createElement("img");
            deleteI.setAttribute("src", "/images/delete2.png");
            deleteI.setAttribute("width", "25px");
            deleteI.setAttribute("height", "25px");

            deleteButton.appendChild(deleteI);
            node4.appendChild(deleteButton);

            var node5 = document.createElement("dd");

            var updateButton = document.createElement("button");
            updateButton.setAttribute("type", "button");
            updateButton.className="replyUpdate2 btn-btn-default";
            updateButton.setAttribute("value", data.idx);

            updateButton.onclick = function (ev) {
                var deVal = $(this).val();
                var modified = $(this).parent().parent().find('.updateContent2').text();
                console.log(modified);
                $.ajax({
                    url: "http://localhost:8080/reply/" + deVal,
                    type: "PUT",
                    data: modified,
                    contentType: "application/json",
                    dataType: "json",
                    success: function () {
                        alert('수정 성공!');
                        // location.reload();
                        // location.href = '/toDoList/list';
                    },
                    error: function () {
                        // alert('수정 실패!');
                    }
                });
            }

            var updatel = document.createElement("img");
            updatel.setAttribute("src", "/images/update2.png");
            updatel.setAttribute("width", "25px");
            updatel.setAttribute("height", "25px");

            updateButton.appendChild(updatel);

            node5.appendChild(updateButton);


            node.appendChild(node2);

            node.appendChild(node3);

            node.appendChild(node5);

            node.appendChild(node4);
            var please = document.getElementById("textBox2");
            var please2 = document.getElementsByClassName("textBox");
            please2.item(reply_id2-1).appendChild(node);

        },
        error: function () {
            // alert('등록 실패!');
        }
    });
})


$('#deleteAll').click(function () {
    $.ajax({
        url: "http://localhost:8080/toDoList/deleteAll",
        type: "DELETE",
        contentType: "application/json",
        success: function () {
            alert('전체삭제 성공!');

            location.reload();
        },
        error: function () {
            alert('전체삭제 실패!');
        }
    });
});