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
                <div class="erreurSingleCol">${form.listErreurs['server']}</div>
            </div>
            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['nom']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="nom">Nom du spot :</label>
                <input type="text" name="nom" id="nom" required="required" value="${form.entitie.nom}">
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
                <select name="departement" id="departement" required="required">
                    <option value="">Choisissez un département</option>
                    <c:forEach items="${departements}" var="departement">
                        <option value="${departement.code}">${departement.code} - ${departement.nom}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="erreur">
                <div></div>
                <div>${form.listErreurs['ville']}</div>
            </div>
            <div class="inscriptionForm">
                <label for="ville">Ville :</label>
                <select name="ville" id="ville" required="required">
                    <option value="">Choisissez une ville</option>
                    <c:forEach items="${villes}" var="ville">
                        <option value="${ville.id}">${ville.nom}</option>
                    </c:forEach>
                </select>
            </div>

            <div  class="erreur">
                <div class="erreurSingleCol">${form.listErreurs['description']}</div>
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
<script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous">

</script>
<script>
    $(document).ready(function(){
        $('#departement').change(function() {
            var choixDep = $('#departement').val();
            $.getJSON("choixDepartement.do",{codeDep: choixDep},
                function (data) {
                    $("#ville").empty();
                    var option = "<option value=''>Choisissez une ville</option>";
                    $("#ville").append(option);
                    $.each( data, function( key, val) {
                        var valToString = val.toString();
                        var valToArray = valToString.split(",");
                        var option = "<option value="+valToArray[0]+">"+valToArray[1]+"</option>";
                        $("#ville").append(option);
                    });
                }
            );
        });
    });
</script>
</body>
</html>
