<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Rechercher un spot</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
    <%@ include file= "header.jsp"%>
    <section id="rechercheSpot">
        <div id="rechercheDiv">
            <h1>Rechercher un spot</h1>
            <form method="post">
                <div class="rechercheForm">
                    <input type="text" name="nom" id="nom" placeholder=" Nom du spot">
                    <input type="checkbox" name="officiel" id="officiel">
                    <label for="officiel">Officiel "Les amis de l'escalade"</label>
                    <br>
                </div>
                <div class="rechercheForm">
                    <select name="region">
                        <option value="">Région</option>
                    </select>
                    <select name="departement">
                        <option>Département</option>
                    </select>
                    <select name="cotation">
                        <option>Cotation</option>
                    </select>
                    <select name="voies">
                        <option>Nb Voies</option>
                    </select>
                </div>
            </form>
        </div>
        <div id="descriptionDiv">
        </div>
    </section>
    <%@include file="social.jsp"%>
</body>
</html>
