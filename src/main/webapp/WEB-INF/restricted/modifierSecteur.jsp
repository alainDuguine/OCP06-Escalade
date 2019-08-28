<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier un Secteur</title>
    <%@ include file="../includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../../css/form.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "../header.jsp"%>
<section class="mainDiv">
    <div class="formDiv">
        <h1>Modifier un secteur :</h1>
        <form method="post" action="updateSecteur.do" enctype="multipart/form-data">
            <div class="erreur">
                <div class="erreurSingleCol">${form.listErreurs['server']}</div>
            </div>
            <input type="text" name="idSpot" id="idSpot" hidden="hidden" value="<c:out value="${secteur.spot.id}"/>">
            <input type="text" name="idSecteur" id="idSecteur" hidden="hidden" value="<c:out value="${secteur.id}"/>">
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom du secteur :</label>
                <input type="text" name="nom" id="nom" maxlength="50" required="required" value="<c:out value="${secteur.nom}"/>">
            </div>

            <div class="erreur">
                <div>${form.listErreurs['description']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="description">Description :</label>
                <textarea name="description" id="description" required="required"><c:out value="${secteur.description}"/></textarea>
            </div>
            <br>
            <hr>
            <div class="inscriptionForm" id="photoForm">
                <h5 id="photoLabel">Photos enregistrées :</h5>
                <div  class="modifPhoto">
                    <c:set var="chemin">/imagesUsers/</c:set>
                    <c:forEach items="${secteur.photos}" var="photo">
                        <div class="photoSaved"><img src="${chemin}${photo.nom}"><a href="supprimerPhoto.do">Supprimer</a></div>
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
<script src="../../js/checkPhotos.js"></script>
<script>
    $(document).ready(function(){

        var departement = ${spot.departement.code};
        var ville = ${spot.ville.id};
        $("#departement option[value="+departement+"]").prop('selected', true);

        $('#departement').change(function() {
            var choixDep = $('#departement').val();
            $.getJSON("choixDepartement.do",{codeDep: choixDep},
                function (data) {
                    $("#ville").empty();
                    var option = "<option value=''>Choisissez une ville</option>";
                    $("#ville").append(option);
                    $.each( data, function(key, val) {
                        var valToString = val.toString();
                        var valToArray = valToString.split(",");
                        var option = "<option value=" + valToArray[0] + ">" + valToArray[1] + "</option>";
                        $("#ville").append(option);
                    });
                }
            );
        });

        $('#departement').trigger("change");

        $("#ville").val(ville);
    });
</script>
</body>
</html>
