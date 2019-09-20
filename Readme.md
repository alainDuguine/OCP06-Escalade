# Les Amis de l'Escalade - Open Classroom Parcours Java Projet 6

### Site communautaire d'escalade en J2ee, Http/Css et Jquery/Ajax, Jpa/Hibernate

## Fonctionnalités

- Consultation de spots ajoutés par la communauté
- Inscription avec encodage de mot de passe
- Ajout de spots, de secteurs, de voies (avec upload de photos)
- Enregistrement et partage de topos
- Recherche multi-critères de spots
- Publication de commentaires
- Tableau de bord utilisateur et administrateur
- Site responsive

## Installation
* Prérequis :
    * [Java jdk 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
    * [Apache Maven](https://maven.apache.org/download.cgi)
    * [Apache Tomcat](http://tomcat.apache.org/)
    * [PostgreSQL11](https://www.postgresql.org/download/)
    
* Création de la base de données :
    * Créer un utilisateur username : "adm_escalade", password : "admin"
	* Créer une base de donnée "DB_ESCALADE" en utf-8
	* Exécuter le script "DbSchema.sql" (présent à la racine du projet)
	* Insérer le jeu de données avec clic-droit sur DB_ESCALADE -> RESTORE -> FILENAME -> Sélectionner le fichier DbData (présent à la racine du projet) -> Restore options -> Only data -> RESTORE

* Déploiement de l'application :
    * Télécharger le code source de l'application via GitHub
    * Dézipper le code
    * En invite de commande rendez vous à la racine du projet et faites un "mvn install" pour créer le fichier war
    * Allez dans le dossier "target", copiez le war créé et collez le dans le dossier "webapps" de votre installation Tomcat
    * En invite de commande rendez vous dans votre dossier Tomcat/bin et exécutez startup.bat
    * Aller sur "http://localhost:8080/Escalade-1.0-SNAPSHOT/index.do" pour commencer à naviguer sur le site (A noter que les liens vers les photos des spots pré-enregistrés ne fonctionneront pas car elles sont stockées sur le serveur).

