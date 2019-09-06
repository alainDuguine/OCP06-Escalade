$(document).ready(function(){

    // Publication des commentaires
    $("#submitCommentaire").click(function(e){
        e.preventDefault();
        var commentaire = $.trim($("#commentaireInput").val());
        var utilisateur = ($("#usernameCommentaire").val());
        var idSpot = $('#idSpot').val();
        if (commentaire.length > 280){
            alert("Un commentaire peut au maximum contenir 280 caractères");
        }else if (commentaire.length == 0) {
            alert("Vous ne pouvez pas publier un commentaire vide")
        }else{
            $.post("saveCommentaire.do", {contenu: commentaire, idSpot: idSpot, utilisateur: utilisateur},
                function (data) {
                    if (!data.hasOwnProperty('erreur')) {
                        $("#commentaireInput").val('');
                        if ($(".commentaire").length) {
                            $(".commentaire").first().before("<p class=\"commentaire\">Par " + data.username + " le "
                                + data.dateFormat + "<br/>" + data.contenu + "</p><hr/>");
                        } else {
                            $(".commentaireDisplay").append("<p class=\"commentaire\">Par " + data.username + " le "
                                + data.dateFormat + "<br/>" + data.contenu + "</p><hr/>");
                        }
                    }else{
                        alert(data.erreur);
                    }
                });
        }
    })

    var commentaireAvantUpdate, idComm

    //Suppression et modification des commentaires par l'administrateur
    $(".modifComm > a").click(function (event) {
        event.preventDefault();
        var el = $(this),
            path = $(this).attr('href');
        idComm = $(this).parent().attr('id');
        if (path === "supprimerCommentaire.do") {
            if (confirm("Etes-vous sûr de vouloir supprimer ce commentaire ?")) {
                $.post(path, {idElement: idComm}, function (data) {
                    if (data == 'true') {
                        el.parent().parent().remove();
                        alert("Suppression effectuée");
                    } else {
                        alert("Suppression échouée");
                    }
                })
            }
            // remplace l'affichage du commentaire en input<text> et affiche les boutons de sauvegardes de modification
        }else if (path === "modifierCommentaire.do"){
            event.preventDefault();
            commentaireAvantUpdate = $('#content'+idComm).text().trim();
            $('#content'+idComm).replaceWith("<input id='inputModifCommentaire' type='text' id='content' value='"+commentaireAvantUpdate+"' style='width:100%;height: 2.5em;border-radius: 5px;'/>");
            $('#updateCommentaire'+idComm).show();
            $('#annulerUpdate'+idComm).show();
            $(this).closest('div').focusin();
        }
    });

    //Sauvegarde de la modification d'un commentaire
    $(".updateCommentaire").click(function (event) {
        var contenuComm = $('#inputModifCommentaire').val();
        if (confirm("Etes-vous sûr de vouloir modifier ce commentaire ?")) {
            $.post("updateCommentaire.do", {idCommentaire: idComm, contenu: contenuComm}, function (data) {
                if (data.resultat == 'true') {
                    if(!alert("Modification effectuée")){window.location.reload()};
                } else {
                    if(!alert("Modification échouée : "+ data.erreur)){
                        $('#content'+idComm).text(commentaireAvantUpdate);
                    };
                }
            })
        }
    });

    // Réinitialisation du commmentaire sur perte du focus
    $(".commentaire").focusout(function(){
        var $elem=$(this);
        setTimeout(function(){
            if(!$elem.find(':focus').length) {
                $('#inputModifCommentaire').replaceWith("<span id='content" + idComm + "'>" + commentaireAvantUpdate + "</span>");
                $('#updateCommentaire' + idComm).hide();
                $('#annulerUpdate' + idComm).hide();
            }
        },0);
    });

    $(".annulerUpdate").click(function () {
        window.location.reload();
    });
});