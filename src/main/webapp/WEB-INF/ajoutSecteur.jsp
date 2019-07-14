<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Spot</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv" id="mainDivCenter">
    <div id="inscriptionDiv">
        <h1>Ajouter un spot d'escalade :</h1>
        <form method="post" action="saveSecteur.do">
            <input type="text" name="idSpot" id="idSpot" hidden="hidden" value="<c:out value="${idSpot}"/>">
            <div class="erreur">
                <div></div>
                <div>${listErreur['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom du secteur :</label>
                <input type="text" name="nom" id="nom" required="required" value="<c:out value="${param.nom}"/>">
            </div>

            <div class="erreur">
                <div></div>
                <div>${listErreur['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" required="required" value="<c:out value="${param.description}"/>"></textarea>
            </div>

            <div class="erreur">
                <div></div>
                <div>${listErreur['photo']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="photo">Ajouter des photos :</label>
                <input type="file" id="photo" name="photo" multiple="multiple" accept="image/jpg, image/png"/>
            </div>

            <div class="bouton">
                <input type="submit" value="Enregistrer">
            </div>

        </form>
    </div>
</section>
<%@include file="social.jsp"%>
</body>
</html>
