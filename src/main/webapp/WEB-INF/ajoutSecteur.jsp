<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Spot</title>
    <%@ include file="includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">
        <h1>Ajouter un secteur au spot - ${spot.nom} :</h1>
        <p class="${empty form.listErreurs ? 'successConnect' : 'echecConnect'}">${form.resultat}</p>
        <form method="post" action="saveSecteur.do">
            <div class="erreur">
                <div class="erreurDescription">${form.listErreurs['server']}</div>
            </div>
            <input type="text" name="idSpot" id="idSpot" hidden="hidden" value="<c:out value="${param.idSpot}"/>">
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom du secteur :</label>
                <input type="text" name="nom" id="nom" required="required" value="<c:out value="${param.nom}"/>">
            </div>

            <div class="erreur">
                <div class="erreurDescription">${form.listErreurs['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" required="required" ><c:out value="${param.description}"/></textarea>
            </div>

            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['photo']}</div>
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
