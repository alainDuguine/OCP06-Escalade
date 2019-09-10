<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="../includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../../css/listeTopo.css">
    <link rel="stylesheet" type="text/css" href="../../css/displaySpot.css">
    <link rel="stylesheet" type="text/css" href="../../css/displayNav.css">
    <link rel="stylesheet" type="text/css" href="../../css/popup.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
    <title>Liste des topos</title>
</head>
<body>
<%@ include file= "../header.jsp"%>
<section id="displaySpot">

    <section class="mainDiv" id="displayDiv">
        <div class="descriptionDiv">
            <h1>Les Topos partagés par la communauté</h1>
            <ul class="treeview">
                <c:forEach items="${topos}" var="topo">
                    <div class="popup" id="${topo.id}">
                        <div class="popuptext">
                            <p class="descriptionPopup">
                                <span>Description :</span>
                                <br>${topo.description}<br>
                                <span>Edition:</span>
                                <br>${topo.dateEdition}
                            </p>
                        </div>
                    </div>
                    <li class="parent"><a class="treeElement" href="#"><c:out value="${topo.nom}"/> </a><a class="description" href="#">(Description)</a>
                        <ul>
                            <c:forEach items="${topo.spots}" var="spot">
                                <li><a class="treeElement" href="display.do?idSpot=${spot.id}"><c:out value="${spot.nom}"/> - Département : <c:out value="${spot.departement.code} - ${spot.departement.nom}"/> - Ville :  <c:out value="${spot.ville.nom}"/></a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </section>
</section>
<%@include file="../social.jsp"%>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script type="text/javascript" src="../../js/listeTopo.js"></script>
</body>
</html>
