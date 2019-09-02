<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>Modifier une Voie</title>
    <%@ include file="../includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "../header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">
        <h1>Modifier une voie :</h1>
        <form method="post" action="updateVoie.do" enctype="multipart/form-data">
            <div class="erreur">
                <div class="erreurSingleCol">${form.listErreurs['server']}</div>
            </div>
            <input type="text" name="idElement" id="idElement" hidden="hidden" value="<c:out value="${voie.id}"/>">
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom de la voie :</label>
                <input type="text" name="nom" id="nom" maxlength="50" required="required" value="<c:out value="${voie.nom}"/>">
            </div>

            <div class="inscriptionForm">
                <label for="cotation">Cotation :</label>
                <select name="cotation" id="cotation" required="required">
                    <option value="">Choisissez une cotation</option>
                    <c:forEach items="${cotations}" var="cotation">
                        <option value="${cotation.id}">${cotation.code}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="inscriptionForm">
                <label for="altitude">Altitude :</label>
                <fmt:parseNumber var="altitudeInt" value="${voie.altitude}"/>
                <input type="number" step="1" min="0" name="altitude" id="altitude" value="${altitudeInt}">
            </div>

            <div class="inscriptionForm">
                <label for="longueur">Nombre de longueurs :</label>
                <fmt:parseNumber var="nbLongueurInt" value="${voie.nbLongueurs}"/>
                <input type="number" step="1" min="0" name="longueur" id="longueur" value="${nbLongueurInt}">
            </div>

            <div class="erreur">
                <div class="erreurDescription">${form.listErreurs['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" required="required" ><c:out value="${voie.description}"/></textarea>
            </div>
            <br>
            <hr>
            <div class="inscriptionForm" id="photoForm">
                <h5 id="photoLabel">Photos enregistrées :</h5>
                <div  class="modifPhoto">
                    <c:set var="chemin">/imagesUsers/</c:set>
                    <c:forEach items="${voie.photos}" var="photo">
                        <div class="photoSaved"><img src="${chemin}${photo.nom}"><a href="${photo.id}">Supprimer</a></div>
                    </c:forEach>
                </div>
            </div>
            <br>
            <hr>

            <div class="erreur">
                <div>${form.listErreurs['photo']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="photo" id="labelPhoto">Ajouter des photos (format jpeg, png) :<br> (Taille max par fichier : 5 Mo, taille totale max : 50 Mo): <br><span id="taillePhoto"></span></label>
                <input type="file" id="photo" name="photo" multiple="multiple" accept="image/jpg, image/png"/>
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
<script src="../../js/checkPhotos.js" charset="UTF-8"></script>
<script src="../../js/deletePhotos.js" charset="UTF-8"></script>
<script>
    $(document).ready(function(){
        var cotation = ${voie.cotation.id};
        $("#cotation option[value="+cotation+"]").prop('selected', true);

    });
</script>
</body>
</html>
