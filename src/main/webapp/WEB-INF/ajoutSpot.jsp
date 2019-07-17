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
        <p class="${empty form.listErreurs ? 'successConnect' : 'echecConnect'}">${form.resultat}</p>
        <form method="post" action="saveSpot.do" enctype="multipart/form-data">
            <div class="erreur">
                <div class="erreurDescription">${form.listErreurs['server']}</div>
            </div>
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom du spot :</label>
                <input type="text" name="nom" id="nom" required="required" value="<c:out value="${param.nom}"/>">
            </div>

            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['adresse']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="adresse">Adresse :</label>
                <input type="text" name="adresse" id="adresse" required="required" value="<c:out value="${param.adresse}"/>">
            </div>

            <div class="inscriptionForm">
                <label for="departement">Département :</label>

                <!-- todo - Ajouter JS pour récupérer valeur select si requête échouée-->

                <select name="departement" id="departement" required="required" valeur="<c:out value="${param.departement}"/>">
                    <option value="">Choisissez un département</option>
                    <option value="Ain">Ain</option>
                    <option value="Aisne">Aisne</option>
                    <option value="Allier">Ain</option>
                    <option value=" Alpes-de-Haute-Provence">Alpes-de-Haute-Provence</option>
                    <option value="Hautes-Alpes">Hautes-Alpes</option>
                </select>
            </div>

            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['ville']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="ville">Ville :</label>
                <input type="text" name="ville" id="ville" required="required" value="<c:out value="${param.ville}"/>">
            </div>

            <div  class="erreur">
                <div id="erreurDescription">${form.listErreurs['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" required="required"><c:out value="${param.description}"/></textarea>
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
