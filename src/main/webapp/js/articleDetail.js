function backArticle(){
    window.location.href = "/";
}

function articleDetail(element) {
    var snValue = element.querySelector('.sn').value;
    var url = "/getArticle?sn=" + encodeURIComponent(snValue);
    window.location.href = url;
}
