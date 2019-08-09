<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="includeCss.jsp"%>
    <link rel="stylesheet" type="text/css" href="../css/gallery.css">
    <link rel="stylesheet" type="text/css" href="../css/displaySpot.css">
    <link rel="stylesheet" type="text/css" href="../css/lightbox.min.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
    <title>Spot : ${spot.nom}</title>
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv" id="displayDiv">
    <div class="descriptionDiv">
        <h1>${spot.nom} - ${spot.departement.nom} - ${spot.ville.nom}</h1>
        <h3>${spot.description}</h3>
    </div>
    <div class="gallery">
        <div id="scroller">
            <a href="../img/adventure-1807524_1920.jpg" data-lightbox="mygallery"><img src="../img/adventure-1807524_1920.jpg"></a>
            <a href="../img/climb-2805903_1920.jpg" data-lightbox="mygallery"><img src="../img/climb-2805903_1920.jpg"></a>
            <a href="../img/climber-299018_1920.jpg" data-lightbox="mygallery"><img src="../img/climber-299018_1920.jpg"></a>
            <a href="../img/climber-984380_1920.jpg" data-lightbox="mygallery"><img src="../img/climber-984380_1920.jpg"></a>
            <a href="../img/climbing-1761386_1920.jpg" data-lightbox="mygallery"><img src="../img/climbing-1761386_1920.jpg"></a>
            <a href="../img/mountain-806915_1920.jpg" data-lightbox="mygallery"><img src="../img/mountain-806915_1920.jpg"></a>
            <a href="../img/mountain-918637_1920.jpg" data-lightbox="mygallery"><img src="../img/mountain-918637_1920.jpg"></a>
            <a href="../img/mountain-climber-2427191_1920.jpg" data-lightbox="mygallery"><img src="../img/mountain-climber-2427191_1920.jpg"></a>
            <a href="../img/mountain-climbing-802099_1920.jpg" data-lightbox="mygallery"><img src="../img/mountain-climbing-802099_1920.jpg"></a>
            <a href="../img/mountaineer-2100050_1920.jpg" data-lightbox="mygallery"><img src="../img/mountaineer-2100050_1920.jpg"></a>
            <a href="../img/mountaineering-895659_1920.jpg" data-lightbox="mygallery"><img src="../img/mountaineering-895659_1920.jpg"></a>
            <a href="../img/person-1245959_1920.jpg" data-lightbox="mygallery"><img src="../img/person-1245959_1920.jpg"></a>
            <a href="../img/rock-climbing-403487_1920.jpg" data-lightbox="mygallery"><img src="../img/rock-climbing-403487_1920.jpg"></a>
            <a href="../img/rock-climbing-1283693_1920.jpg" data-lightbox="mygallery"><img src="../img/rock-climbing-1283693_1920.jpg"></a>
            <a href="../img/saxon-switzerland-539418_1920.jpg" data-lightbox="mygallery"><img src="../img/saxon-switzerland-539418_1920.jpg"></a>
            <a href="../img/summit-1209168_1920.jpg" data-lightbox="mygallery"><img src="../img/summit-1209168_1920.jpg"></a>
            <a href="../img/woman-2594934_1920.jpg" data-lightbox="mygallery"><img src="../img/woman-2594934_1920.jpg"></a>
        </div>
    </div>
    <div class="dataSpot">
        <div>
            <h3>Secteurs:</h3>
            <c:forEach items="${spot.secteurs}" var="secteur">
                <p>${secteur.nom} - ${secteur.description}</p>
                <h4>Photos :</h4>
                <c:forEach items="${secteur.photos}" var="photo">
                    <p>${photo.nom}</p>
                </c:forEach>
                <h4>Voies :</h4>
                <c:forEach items="${secteur.voies}" var="voie">
                    <p>Nom : ${voie.nom}</p>
                    <p>Cotation : ${voie.cotation.code}</p>
                    <p>Altitude :${voie.altitude}</p>
                </c:forEach>
            </c:forEach>
        </div>
    </div>

</section>
<%@include file="social.jsp"%>

<script type="text/javascript" src="../js/lightbox-plus-jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
</body>
</html>
