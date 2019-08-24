<header>
    <div id="logo">
        <a href=""><img src="../img/logo.png" alt="Logo association" title="Notre association" id="img-logo"/></a>
    </div>
    <div id="banner">
        <nav class="menu">
            <p class="menu-button"><a href="index.do">Accueil</a></p>
            <p class="menu-button"><a href="listeSpot.do">Les Spots</a></p>
            <p class="menu-button"><a href="">Les Topos</a></p>
            <p class="menu-button"><a href="">Apprendre</a></p>
            <p class="menu-button"><a href="">Liens</a></p>
            <c:if test="${!empty sessionScope.username}">
                <p class="menu-button"><a href="dashboard.do">Mon Espace</a></p>
            </c:if>
        </nav>
        <c:choose>
            <c:when test="${empty sessionScope.username}">
                <nav class="user">
                    <p class="menu-button"><a href="connexion.do"><img src="../img/User.png" title="Se connecter" alt="Se connecter"/></a></p>
                    <p class="menu-button"><a href="inscription.do"><img src="../img/addUser.png" title="Créer un compte" alt="Créer un compte"/></a></p>
                </nav>
            </c:when>
            <c:otherwise>
                <nav class="userDrop">
                    <div class="container">
                        <p class="menu-button" id="userConnected"><a href="dashboard.do" id="username"><span>${sessionScope.username}</span><img id="iconUser" src="../img/UserConnected.png" title="Tableau de bord" alt="Tableau de bord"/></a></p>
                        <ul id="items">
                            <li><a href="dashboard.do">Tableau de bord</a></li>
                            <li><a href="deconnexion.do">Se déconnecter</a></li>
                        </ul>
                    </div>
                </nav>
            </c:otherwise>
        </c:choose>
    </div>
</header>