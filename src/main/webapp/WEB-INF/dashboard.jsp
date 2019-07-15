<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv" id="mainDivCenter">
    <div id="inscriptionDiv">
        <div id="bannerResult">
            <div id="menuResult">
                <p class="menu-button"><a href="dashboard.do">Mes Spots</a></p>
                <p class="menu-button"><a href="ajoutSpot.do">Ajouter un spot</a></p>
            </div>
        </div>
        <div id="resultatDiv">
            <table>
                <tr>
                    <th>Nom Spot</th><th>Adresse</th><th>Ville</th><th>Département</th>
                </tr>
                <c:forEach items="${spots}" var="spot">
                    <tr>
                        <td>${spot.nom}</td>
                        <td>${spot.adresse}</td>
                        <td>${spot.ville}</td>
                        <td>${spot.departement}</td>
                        <td><a href="ajoutSecteur.do?idSpot=${spot.id}">Ajouter un secteur</a></td>
                        <td><a onclick="return confirm('Etes vous sûr de vouloir supprimer cet enregistrement');" href="supprimer.do?id=${p.id}">Supprimer</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</section>

</body>
</html>
