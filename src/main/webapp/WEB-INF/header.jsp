<header>
    <div id="logo">
        <a href="index.do"><img src="../images/logo.png" alt="Logo association" title="Notre association" id="img-logo"/></a>
    </div>
    <div id="banner">
        <nav class="menu">
            <div class="menu-button"><a href="index.do">Accueil</a></div>
            <div class="menu-button"><a href="listeSpot.do">Les Spots</a></div>
            <div class="menu-button"><a href="listeTopo.do">Les Topos</a></div>
            <div class="menu-button"><a href="">Apprendre</a></div>
            <div class="menu-button"><a href="">Liens</a></div>
            <c:if test="${!empty sessionScope.sessionUtilisateur}">
                <div class="menu-button"><a href="dashboard.do">Mon Espace</a></div>
            </c:if>
        </nav>
        <c:choose>
            <c:when test="${empty sessionScope.sessionUtilisateur}">
                <nav class="user">
                    <div class="menu-button" id="userConnexion"><a href="connexion.do"><img src="../images/User.png" title="Se connecter" alt="Se connecter"/></a></div>
                    <div class="menu-button" id="addUser"><a href="inscription.do"><img src="../images/addUser.png" title="Créer un compte" alt="Créer un compte"/></a></div>
                </nav>
            </c:when>
            <c:otherwise>
                <nav class="userDrop">
                    <div class="container">
                        <div class="menu-button" id="userConnected"><a href="dashboard.do" id="username"><span><c:out value="${sessionScope.sessionUtilisateur}"/></span><img id="iconUser" src="../images/UserConnected.png" title="Tableau de bord" alt="Tableau de bord"/></a></div>
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