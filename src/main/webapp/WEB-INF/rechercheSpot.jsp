<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>Rechercher un spot</title>
    <%@ include file="includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/table.css">
    <link rel="stylesheet" type="text/css" href="../css/form.css">
    <link rel="stylesheet" type="text/css" charset="UTF-8" href="../css/rechercheSpotResp.css">
    <link rel="stylesheet" type="text/css" charset="UTF-8" href="../css/tableResp.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
    <%@ include file= "header.jsp"%>
    <section class="mainDiv">
        <div class="formDiv">
            <h1>Rechercher un spot</h1>
            <form method="post" action="rechercheSpot.do">
                <div class="rechercheForm">
                    <input type="text" name="nomSpot" id="nomSpot" placeholder="Nom du spot"/>
                    <input type="checkbox" name="officiel" id="officiel"/>
                    <label for="officiel">Officiel "Les amis de l'escalade"</label>
                    <br>
                </div>
                <div class="rechercheForm">
                    <select class="optionBigger" name="departement" id="departement">
                        <option value="">Choisissez un département</option>
                        <c:forEach items="${listDepartement}" var="departement">
                            <option value="${departement.key}">${departement.key} - ${departement.value}</option>
                        </c:forEach>
                    </select>
                    <select class="optionBigger" name="ville" id="ville">
                        <option value="">Choisissez une ville</option>
                    </select>
                </div>
                <div class="rechercheForm">
                    <select name="cotationMin" id="cotationMin">
                        <option value="">Cotation Min</option>
                        <c:forEach items="${cotations}" var="cotation">
                            <option value="${cotation.id}">${cotation.code}</option>
                        </c:forEach>
                    </select>
                    <select name="cotationMax" id="cotationMax">
                        <option value="">Cotation Max</option>
                        <c:forEach items="${cotations}" var="cotation">
                            <option value="${cotation.id}">${cotation.code}</option>
                        </c:forEach>
                    </select>
                    <input type="number" step="1" min="0" name="secteurMin" id="secteurMin" placeholder="nb min Secteurs">
                    <input type="number" step="1" min="0" name="secteurMax" id="secteurMax" placeholder="nb max Secteurs">
                </div>
                <div class="bouton">
                    <input type="submit" value="Rechercher">
                    <input type="reset" value="Réinitialiser">
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
                        <th>Nb Secteurs</th>
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
                        <td><c:out value="${spot.minCotationCode}"/></td>
                        <td><c:out value="${spot.maxCotationCode}"/></td>
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

        //Rend chaque ligne du tableau de résultat entièrement cliquable
        $(".item").click(function(){
            var href = $(this).find("a").attr("href");
            if (href){
                window.location = href;
            }
        });

        $('#departement').change(function() {
            var choixDep = $('#departement').val();
            $.getJSON("choixVille.do",{codeDep: choixDep},
                function (data) {
                    $("#ville").empty();
                    var option = "<option value=''>Choisissez une ville</option>";
                    $("#ville").append(option);
                    $.each(data, function(key, val) {
                        var option = "<option value=" + val + ">" + val + "</option>";
                        $("#ville").append(option);
                    });
                }
            );

        });
    });
</script>
</body>
</html>
