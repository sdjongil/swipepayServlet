document.addEventListener("DOMContentLoaded", function() {
    var dateElements = document.querySelectorAll('.date');
    dateElements.forEach(function(element) {
        var rawDate = element.getAttribute('data-date');
        if (rawDate && rawDate.length === 8) {
            var formattedDate = rawDate.slice(0, 4) + '-' + rawDate.slice(4, 6) + '-' + rawDate.slice(6, 8);
            element.textContent = formattedDate;
        }
    });
});

function articleDetail(element) {
    var snValue = element.querySelector('.sn').value;
    var url = "/getArticle?sn=" + encodeURIComponent(snValue);
    window.location.href = url;
}