$(document).ready(function() {
    $(".photoSaved > a").click(function (event) {
        event.preventDefault();
        // var jspName = location.pathname.split('/').slice(-1)[0];
        // alert(jspName);
        var el = $(this),
            photoId = $(this).attr('href');
        if (confirm("Etes-vous sûr de vouloir supprimer cette photo ?")) {
            $.post("supprimerPhoto.do", {idElement: photoId}, function (data) {
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