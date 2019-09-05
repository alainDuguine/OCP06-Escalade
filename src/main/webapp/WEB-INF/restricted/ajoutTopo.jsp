<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter de Topo</title>
    <%@ include file="../includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "../header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">
        <h1>Ajouter un topo :</h1>
        <c:if test="${not empty form.resultat}">
            <p class="${form.resultat ? 'success' : 'echec'}">
                <c:out value="${form.resultat ? 'Enregistrement effectué' : 'Enregistrement échoué'}"/>
            </p>
        </c:if>
        <form method="post" action="saveTopo.do" enctype="multipart/form-data">
            <div class="erreur">
                <div class="erreurSingleCol">${form.listErreurs['server']}</div>
            </div>

            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom du topo :</label>
                <input type="text" name="nom" id="nom" maxlength="50" required="required" value="<c:out value="${param.nom}"/>">
            </div>

            <div class="erreur">
                <div>${form.listErreurs['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" maxlength="2000" required="required" ><c:out value="${param.description}"/></textarea>
            </div>

            <div class="erreur">
                <div>${form.listErreurs['photo']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="dateParution" id="labelPhoto">Ajouter des photos (format jpeg, png) :<br> (Taille max 5 Mo, taille totale max 50 Mo):<br><span id="taillePhoto"></span></label>
                <input type="date" id="dateParution" name="dateParution" min="1950-01-01" required="required"/>
            </div>

            <div class="bouton">
                <input type="submit" value="Enregistrer">
            </div>

        </form>
    </div>
</section>
<%@include file="../social.jsp"%>
<script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous">
</script>
<script src="../../js/checkPhotos.js"></script>
</body>
</html>
