$(document).ready(function() {
    $(".photoSaved > a").click(function (event) {
        event.preventDefault();
        var el = $(this),
            idElementParent = $("#idElement").val(),
            jspUrl = location.pathname.split('/').slice(-1)[0],
            photoId = $(this).attr('href');
        if (confirm("Etes-vous sûr de vouloir supprimer cette photo ?")) {
            $.post("supprimerPhoto.do", {idElement: photoId, jspUrl: jspUrl}, function (data) {
                if (data == 'true') {
                    el.parent().remove();
                    if(!alert("Suppression effectuée")){window.location.reload()};
                } else {
                    alert("Suppression échouée");
                }
            })
        }
    });
});