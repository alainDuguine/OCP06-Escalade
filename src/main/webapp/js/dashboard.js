$(document).ready(function () {

    // Alerte si réservation de topo en attente
    var attentePret = $("#attentePret").html(),
    attenteEmprunt = $("#attenteEmprunt").html();
    console.log(attentePret + " " + attenteEmprunt);
    if(attentePret === true){
        alert("Vous avez des demandes de prêts de topos en attente. Vous les trouverez dans l'onglet \"Mes Topos\"");
    }
    if(attenteEmprunt === true){
        alert("Des demandes de prêts on été acceptées. Veuillez vous rendre dans l'onglet \"Mes Topos\" pour contacter le propriétaire et terminer la réservation.");
    }

    // Affiche chaque taleau (spot, secteur, voie, topo, utilisateur) sur le clic du menu
    $(".menu-button").click(function() {
        $(".resultatDiv").hide();
        $(".menuDashboard button").removeClass("active");
        $(this).addClass("active");
        var idTable = "#table" + $(this).attr("id");
        $(idTable).show();
    });

    // Change le statut disponible d'un topo
    $(".dispoTopo").click(function() {
        var el = $(this);
        var topoId = $(this).parent().parent().attr('id');
        if (confirm("Etes-vous sûr de vouloir modifier la disponibilité de ce topo ?")) {
            $.post("toggleTopo.do", {idTopo: topoId}, function (data) {
                var result;
                if (data === 'true'){
                    result = 'Oui';
                }else{
                    result = 'Non';
                }
                $(el).parent().siblings(".topoTable").text(result);
                alert("Modification de la disponibilité effectuée");
            })
        }
    });

    // Change le statut administrateur de l'utilisateur sur le clic du bouton
    $(".buttonAdmin").click(function() {
        var el = $(this);
        var userId = $(this).parent().parent().attr('id');
        if (confirm("Etes-vous sûr de vouloir modifier les doits utilisateurs pour cet utilisateur ?")) {
            $.post("toggleAdmin.do", {idUser: userId}, function (data) {
                var result;
                if (data === 'true'){
                    result = 'Oui';
                }else{
                    result = 'Non';
                }
                $(el).parent().siblings(".adminTable").text(result);
                alert("Modification des droits effectuée");
            })
        }
    });

    // Suppression des éléments Spots, Secteurs, Voies
    $(".supprElem").click(function(event){
        event.preventDefault();
        var el = $(this),
            path = $(this).children('a').attr('href')+".do",
            elId = $(this).parent().attr('id');
        var msg;
        if (path.includes('Topo')){
            msg = "Etes vous sûr de vouloir supprimer ce topo ?"
        }else if (path.includes('Reservation')){
            msg = "Etes vous sûr de vouloir annuler cette demande de réservation ?"
        }else{
            msg ="Attention ! La suppression d'un élément entraînera la suppression de tous les éléments associés ! Spots, Secteurs, Voies, Photos, Commentaires. Etes-vous sûr de vouloir continuer ?";
        }
        if (confirm(msg)) {
            $.post(path, {idElement: elId}, function (data) {
                if (data === 'true') {
                    el.parent().remove();
                    if(!alert("Suppression effectuée")){window.location.reload()}
                } else {
                    alert("Suppression échouée");
                }
            })
        }
    });

    // Accord ou refus réservation

    $(".buttonReservation").click(function(){
        var idReservation = $(this).parent().parent().attr('id'),
            buttonPath = $(this).val();
        if (buttonPath.includes("accepter")) {
            msg = "En acceptant la demande de prêt, votre adresse email sera communiquée au demandeur. Etes-vous sûr ?"
        } else if (buttonPath.includes("refuser")){
            msg = "Etes-vous sûr de vouloir refuser la demande de prêt ?"
        } else{
            msg = "Si vous terminez la réservation, vous ne recevrez plus d'alerte la concernant."
        }
        if (confirm(msg)){
            $.post(buttonPath, {idReservation: idReservation}, function (data){
                if (data === 'true') {
                    if (buttonPath === "accepterPret.do"){
                        $('#statutReservation').val("Acceptée");
                        if(!alert("La demande de prêt a bien été acceptée !")){window.location.reload()}
                    }else if(buttonPath === "refuserPret.do"){
                        $('#statutReservation').val("Refusée");
                        if(!alert("La demande de prêt a bien été refusée !")){window.location.reload()}
                    }else{
                        $('#statutReservation').val("Terminée");
                        if(!alert("La demande de prêt est terminée !")){window.location.reload()}
                    }
                } else {
                    alert("Une erreur est apparue, merci de réessayer plus tard");
                }
            })
        }
    })
});
