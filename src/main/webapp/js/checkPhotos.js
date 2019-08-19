$(document).ready(function() {

    var validImageTypes = ["image/jpeg", "image/png"];

    $(":file").change(function () {
        var totalSize = 0;
        for (var i = 0; i < this.files.length; i++) {
            var file = this.files[i];
            if (file.size > 5242880) {
                alert("Le fichier " + file.name + " fait plus de 5 Mo");
                $(":file").val('');
            } else if ($.inArray(file.type, validImageTypes)){
                alert("Le fichier " + file.name + " n'est pas au format jpeg ou png");
                $(":file").val('');
            } else {
                totalSize = totalSize + Number(file.size);
            }
        }
        var sizeMo = totalSize / 1024 / 1024;
        if (totalSize > 52428800) {
            alert("Les fichiers font au total : " + sizeMo.toFixed(2) + " Mo, merci d'envoyer au maximum 50 Mo");
            $(":file").val('');
        }
        $("#taillePhoto").empty();
        var text = "Taille des fichiers : " + sizeMo.toFixed(2) + " Mo";
        $("#taillePhoto").text(text);
    });
});