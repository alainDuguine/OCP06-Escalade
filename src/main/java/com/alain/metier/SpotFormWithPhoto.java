//package com.alain.metier;
//
//import com.alain.dao.entities.PhotoSpot;
//import com.alain.dao.entities.Spot;
//import com.alain.dao.impl.SpotDaoImpl;
//import eu.medsea.mimeutil.MimeUtil;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.Part;
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SpotFormWithPhoto {
//
//    private static final String CHAMP_NOM = "nom";
//    private static final String CHAMP_ADRESSE = "adresse";
//    private static final String CHAMP_DEPARTEMENT = "departement";
//    private static final String CHAMP_VILLE = "ville";
//    private static final String CHAMP_DESCRIPTION = "description";
//    private static final String CHAMP_IMAGE = "photo";
//
//    private static final int TAILLE_TAMPON = 10240;
//    private static final String[] EXTENSION_ACCEPTEE = {".jpg",".jpeg",".png"};
//
//    private String resultat;
//    private Map<String, String> erreurs = new HashMap<String, String>();
//
//    private SpotDaoImpl spotDao;
//
//    // ============= Constructeur ==================== //
//
//    public SpotFormWithPhoto(SpotDaoImpl spotDao) {
//        this.spotDao = spotDao;
//    }
//
//    // ============= Getters & Setters ==================== //
//
//    public String getResultat() {
//        return resultat;
//    }
//
//    public Map<String, String> getErreurs() {
//        return erreurs;
//    }
//
//    // ============= Méthodes ==================== //
//
//    public Spot creerSpot(HttpServletRequest req, String chemin){
//        String nom = getValeurChamp(req, CHAMP_NOM);
//        String adresse = getValeurChamp(req, CHAMP_ADRESSE);
//        String departement = getValeurChamp(req, CHAMP_DEPARTEMENT);
//        String ville = getValeurChamp(req, CHAMP_VILLE);
//        String description = getValeurChamp(req, CHAMP_DESCRIPTION);
//
//        Spot spot = new Spot();
//
//        traiterNom(nom,spot);
//        traiterAdresse(adresse,spot);
//        traiterDepartement(departement,spot);
//        traiterVille(ville,spot);
//        traiterDescription(description,spot);
//        traiterImage(spot,req,chemin);
//        try{
//            if (erreurs.isEmpty()){
//
//            }
//        }
//        return spot;
//
//    }
//
//    private void traiterNom(String nom, Spot spot) {
//        try{
//            validationNom(nom);
//        }catch(Exception e){
//            erreurs.put(CHAMP_NOM, e.getMessage());
//        }
//        spot.setNom(nom);
//    }
//
//    private void traiterAdresse(String adresse, Spot spot) {
//        try{
//            validationAdresse(adresse);
//        }catch(Exception e){
//            erreurs.put(CHAMP_ADRESSE, e.getMessage());
//        }
//        spot.setAdresse(adresse);
//    }
//
//    private void traiterDepartement(String departement, Spot spot) {
//        spot.setDepartement(departement);
//    }
//
//    private void traiterVille(String ville, Spot spot) {
//        try{
//            validationVille(ville);
//        }catch(Exception e){
//            erreurs.put(CHAMP_VILLE, e.getMessage());
//        }
//        spot.setVille(ville);
//    }
//
//    private void traiterDescription(String description, Spot spot) {
//        try{
//            validationDescription(description);
//        }catch(Exception e){
//            erreurs.put(CHAMP_VILLE, e.getMessage());
//        }
//        spot.setDescription(description);
//    }
//
//    private void validationDescription(String description) throws Exception {
//        if (description.length() < 10) {
//            throw new Exception("Veuillez entrer une description d'au moins 50 caractères");
//        }else if (description.length() > 2000) {
//            throw new Exception("Veuillez entrer une description de maximum 2000 caractères.");
//        }
//    }
//
//    private void validationVille(String ville) throws Exception {
//        if (ville == null){
//            throw new Exception("Merci d'entrer un nom de ville");
//        }
//    }
//
//
//    private void validationAdresse(String adresse) throws Exception {
//        if (adresse != null) {
//            if (adresse.length() <2 ){
//                throw new Exception("L'adresse du spot doit contenir au moins 2 caractères");
//            }
//        }else{
//            throw new Exception("Merci d'entrer une adresse de spot");
//        }
//    }
//
//    private void validationNom(String nom) throws Exception {
//        if (nom != null) {
//            if (nom.length() <2 ){
//                throw new Exception("Le nom du spot doit contenir au moins 2 caractères");
//            }
//        }else{
//            throw new Exception("Merci d'entrer un nom de spot");
//        }
//    }
//
//    private static String getValeurChamp(HttpServletRequest req, String nomChamp){
//        String valeur = req.getParameter(nomChamp);
//        if (valeur == null || valeur.trim().length() == 0){
//            return null;
//        }else{
//            return valeur;
//        }
//    }
//
//    // ============= Gestion de l'image ==================== //
//
//    private void traiterImage(Spot spot, HttpServletRequest req, String chemin) {
//
//        PhotoSpot image = null;
//        try {
//            image = validationImage(req, chemin);
//        }catch(Exception e){
//            erreurs.put(CHAMP_IMAGE, e.getMessage());
//        }
//        spot.addPhotoSpot(image);
//    }
//
//    private PhotoSpot validationImage(HttpServletRequest req, String chemin) {
//        PhotoSpot photo = new PhotoSpot();
//        String nomFichier = null;
//        InputStream contenuFichier = null;
//
//
//        try{
//            //Génération du nom du fichier
//            Part part = req.getPart(CHAMP_IMAGE);
//            nomFichier = genererNomFichier(part);
//
//            //Traitement contenu fichier
//            contenuFichier = part.getInputStream();
//            validerFormatFichier(contenuFichier);
//            ecrireFichier(contenuFichier, nomFichier, chemin);
//        }catch (Exception e){
//            erreurs.put(CHAMP_IMAGE, e.getMessage());
//        }
//        photo.setNom(nomFichier);
//
//        return photo;
//    }
//
//    private void ecrireFichier(InputStream contenuFichier, String nomFichier, String chemin) throws Exception {
//        BufferedInputStream entree = null;
//        BufferedOutputStream sortie = null;
//        try{
//            entree = new BufferedInputStream(contenuFichier, TAILLE_TAMPON);
//            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);
//
//            byte[] tampon = new byte[TAILLE_TAMPON];
//            int longueur = 0;
//            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
//                sortie.write( tampon, 0, longueur );
//            }
//        } catch (Exception e) {
//            throw new Exception("Erreur lors de l'écriture du fichier");
//        } finally {
//            try {
//                sortie.close();
//            } catch ( IOException ignore ) {
//            }
//            try {
//                entree.close();
//            } catch ( IOException ignore ) {
//            }
//        }
//    }
//
//    private void validerFormatFichier(InputStream contenuFichier) throws Exception {
//        MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
//        Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );
//        if ( !mimeTypes.toString().startsWith( "image" ) ) {
//            throw new Exception("Les fichiers doivent être des images");
//        }
//    }
//
//    private String genererNomFichier(Part part){
//        // TODO AJOUTER INDEX DE getParts()
//        String dateTime;
//        String extension;
//        String nomGenere;
//        extension = getExtension(part);
//        dateTime = formatDate(LocalDateTime.now());
//        nomGenere = dateTime + "." + extension;
//        return nomGenere;
//    }
//
//    private static String getExtension(Part part) {
//        for (String contentDisposition : part.getHeader("content-disposition").split(";")){
//            if(contentDisposition.trim().startsWith("filename")){
//                return contentDisposition.substring(contentDisposition.indexOf('.')+ 1).trim().replace("\"","");
//            }
//        }
//        return null;
//    }
//
//    private static String formatDate(LocalDateTime now) {
//        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        String formattedDate = now.format(myFormatObj);
//        return formattedDate;
//    }
//}
