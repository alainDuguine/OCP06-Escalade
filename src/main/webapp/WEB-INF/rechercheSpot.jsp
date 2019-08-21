<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rechercher un spot</title>
    <%@ include file="includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/table.css">
    <link rel="stylesheet" type="text/css" href="../css/form.css">
    <link rel="stylesheet" type="text/css" href="../css/recherche.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
    <%@ include file= "header.jsp"%>
    <section class="mainDiv">
        <div class="formDiv">
            <h1>Rechercher un spot</h1>
            <form method="post">
                <div class="rechercheForm">
                    <input type="text" name="nomSpot" id="nomSpot" placeholder="Nom du spot"/>
                    <input type="checkbox" name="officiel" id="officiel"/>
                    <label for="officiel">Officiel "Les amis de l'escalade"</label>
                    <br>
                </div>
                <div class="rechercheForm">
                    <select class="optionBigger" name="departement" id="departement" required="required">
                        <option value="">Choisissez un département</option>
                        <c:forEach items="${listDepartement}" var="departement">
                            <option value="${departement.key}">${departement.key} - ${departement.value}</option>
                        </c:forEach>
                    </select>
                    <select class="optionBigger" name="ville">
                        <option>Ville</option>
                    </select>
                    <select name="cotationMin">
                        <option>Cotation Min</option>
                    </select>
                    <select name="cotationMax">
                        <option>Cotation Max</option>
                    </select>
                    <select name="voies">
                        <option>Nb Voies</option>
                    </select>
                </div>
                <div class="bouton">
                    <input type="submit" value="Rechercher">
                    <input type="submit" value="Réinitialiser">
                </div>
            </form>
        </div>
        <div class="resultatDiv">
            <table class="tableauResult">
                <thead>
                    <tr>
                        <th>Nom Spot</th>
                        <th>Département</th>
                        <th>Ville</th>
                        <th>Cotation mini</th>
                        <th>Cotation max</th>
                        <th>Nb Voies</th>
                        <th>Officiel</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${spots}" var="spot">
                    <tr class="item">
                        <c:set var="idSpot" value="${spot.id}"/>
                        <td><a href="display.do?idSpot=${spot.id}"><c:out value="${spot.nom}"/></a></td>
                        <td><c:out value="${spot.departementCode} - ${spot.departementNom}"/></td>
                        <td><c:out value="${spot.ville}"/></td>
                        <td><c:out value="${spot.minCotation}"/></td>
                        <td><c:out value="${spot.maxCotation}"/></td>
                        <td><c:out value="${spot.nbSecteur}"/></td>
                        <c:choose>
                            <c:when test="${spot.officiel == true}"><td><c:out value="Oui"/></td></c:when>
                            <c:otherwise><td><c:out value="Non"/></td></c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>
    <%@include file="social.jsp"%>
    <script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function(){
        // $(".item").click(function(){
        //     var href = $(this).find("a").attr("href");
        //     if (href){
        //         window.location = href;
        //     }
        // });
    });
</script>
</body>
</html>
