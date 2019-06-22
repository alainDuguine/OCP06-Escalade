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
                <div id="boutonSpot">
                    <input type="submit" value="Rechercher">
                </div>
            </form>
        </div>
        <div id="resultatDiv">
            <table id="tableauResult">
                <thead>
                    <tr>
                        <th>Nom Spot</th>
                        <th>Région</th>
                        <th>Département</th>
                        <th>Ville</th>
                        <th>Cotation mini</th>
                        <th>Cotation max</th>
                        <th>Nb Voies</th>
                        <th>Officiel<br>"Les amis de l'escalade"</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Ablon</td>
                        <td>Auvergne-Rhône-Alpes</td>
                        <td>Haute-Savoie</td>
                        <td>Ablon</td>
                        <td>4b</td>
                        <td>8b</td>
                        <td>400</td>
                        <td>Non</td>
                    </tr>
                    <tr>
                        <td>Ailefroide</td>
                        <td>Provence-Alpes-Côte d'Azur</td>
                        <td>Hautes-Alpes</td>
                        <td>L’Argentière la Bessée</td>
                        <td>3a</td>
                        <td>8a</td>
                        <td>500</td>
                        <td>Oui</td>
                    </tr>
                    <tr>
                        <td>Bavella</td>
                        <td>Corse</td>
                        <td>Corse-du-Sud</td>
                        <td>Col de Bavella</td>
                        <td>3c</td>
                        <td>8b</td>
                        <td>200</td>
                        <td>Non</td>
                    </tr>
                    <tr>
                        <td>Céüse</td>
                        <td>Provence-Alpes-Côte d'Azur</td>
                        <td>Hautes-Alpes</td>
                        <td>Col des Guérins</td>
                        <td>5a</td>
                        <td>9a</td>
                        <td>400</td>
                        <td>Oui</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </section>
    <%@include file="social.jsp"%>
</body>
</html>
