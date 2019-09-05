<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter des topos</title>
    <%@ include file="../includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/table.css">
    <link rel="stylesheet" type="text/css" href="../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "../header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">
        <h1>Ajouter des topos au spot ${spot.nom}</h1>
        <div class="descriptionDiv" id="conditionTopo">
            <p>En ajoutant un topo à un spot, vous acceptez de le mettre à disposition des membres de la communauté pour un prêt.
                Il sera proposé dans l'encart "Topos disponible" de la page d'affichage du spot concerné, avec la possibilité pour un membre de vous faire une demande de réservation.
                Si vous acceptez cette demande, votre adresse email sera communiqué au membre.</p>
        </div>

        <form method="post" action="addTopoSpot.do">

        <div class="resultatDiv">
                <form method="post" action="saveTopoSpot.do">
                    <table class="tableauResult">
                        <thead>
                            <tr>
                                <th>Nom Topo</th>
                                <th>Date Edition</th>
                                <th>Ajouter au spot</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${utilisateur.topos}" var="topo">
                            <tr class="item">
                                <c:set var="idSpot" value="${topo.id}"/>
                                <td><c:out value="${topo.nom}"/></td>
                                <td><c:out value="${topo.dateEdition}"/></td>
                                <td><input type="checkbox" name="checkTopo"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="bouton">
                        <input type="submit" value="Enregistrer"/>
                    </div>
                </form>

        </div>
</section>
<%@include file="../social.jsp"%>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function(){

        //Rend chaque ligne du tableau de résultat entièrement cliquable
        $(".item").click(function(){
            var href = $(this).find("a").attr("href");
            if (href){
                window.location = href;
            }
        });