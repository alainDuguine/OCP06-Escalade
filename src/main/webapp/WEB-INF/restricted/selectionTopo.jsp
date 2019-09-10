<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter des topos</title>
    <%@ include file="../includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../../css/table.css">
    <link rel="stylesheet" type="text/css" href="../../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "../header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">

        <c:if test="${not empty resultat}">
        <p class="${form.resultat ? 'success' : 'echec'}">
            <c:out value="${form.resultat ? 'Enregistrement effectué' : 'Enregistrement échoué'}"/>
        </p>
        </c:if>

        <h1>Ajouter des topos au spot ${spot.nom}</h1>
        <div class="descriptionDiv" id="conditionTopo">
            <p>En ajoutant un topo à un spot, vous acceptez de le mettre à disposition des membres de la communauté pour un prêt.
                Il sera proposé dans l'encart "Topos disponible" de la page d'affichage du spot concerné, avec la possibilité pour un membre de vous faire une demande de réservation.
                Si vous acceptez cette demande, votre adresse email sera communiqué au membre.</p>
        </div>

        <form method="post" action="saveTopoSpot.do">
            <input type="text" name="idSpot" id="idSpot" hidden="hidden" value="<c:out value="${param.idSpot}"/>">
            <div class="resultatDiv">
                    <table class="tableauResult">
                        <thead>
                            <tr>
                                <th>Nom Topo</th>
                                <th>Date Edition</th>
                                <th>Ajouter au spot</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:set var="topos" value="${admin ? listTopo : utilisateur.topos}"/>
                        <c:forEach items="${topos}" var="topo">
                            <tr class="item">
                                <input type="text" hidden="hidden" name="idTopo" id="idTopo" value="<c:out value="${topo.id}"/>"/>
                                <td><c:out value="${topo.nom}"/></td>
                                <td><c:out value="${topo.dateEdition}"/></td>
                                <td><input type="checkbox" name="checkTopo" value="${topo.id}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="bouton">
                        <input type="submit" value="Enregistrer"/>
                    </div>
            </div>
        </form>
</section>
<%@include file="../social.jsp"%>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {

        //Rend chaque ligne du tableau de résultat entièrement cliquable
        $(".item").click(function () {
            var href = $(this).find("a").attr("href");
            if (href) {
                window.location = href;
            }
        });
    });
</script>