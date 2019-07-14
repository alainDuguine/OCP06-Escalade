//package com.alain.metier;
//
//import com.alain.dao.contract.EntityRepository;
//import com.alain.dao.entities.Entitie;
//import com.alain.dao.entities.Photo;
//import com.alain.dao.entities.Spot;
//import com.alain.dao.impl.PhotoDaoImpl;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.Part;
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Map;
//
//
//public class CheckFormUpload extends CheckForm{
//    private static final String CHAMP_PHOTO = "photo";
//    private static final int TAILLE_TAMPON = 10240;
//    private static final String CHEMIN_UPLOAD = "D:\\fichiers\\";
//    private static final Long TAILLE_PHOTO_MAX = 2000000L; // 2,5Mo
//    private static final Long TOTAL_PHOTO_MAX = 250000000L; // 25Mo
//
//    public static Photo hydratePhoto(Part part, int index) throws Exception {
//        String dateTime;
//        String nomGenere;
//
//        Photo photo = new Photo();
//        if (part.getSize() > TAILLE_PHOTO_MAX){
//            throw new Exception("Chaque fichier doit avoir une taille inférieure à 2,5Mo");
//        }
//
//        dateTime = formatDate(LocalDateTime.now());
//        // Création d'un nom unique à l'aide du timestamp et de l'index dans la collection
//        nomGenere = dateTime + index;
//        try {
//            Photo p = enregistrerFichiers(part, nomGenere, CHEMIN_UPLOAD);
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        photo.setNom(nomGenere);
//        return photo;
//    }
//
//
//    public static Map<String,String> checkAndUpload(HttpServletRequest req, String className, EntityRepository dao) {
//        String nomFichierGenere;
//        Long totalSizeFiles = 0L;
//        String dateTime;
//        Map<String,String> listErreur = null;
//        ArrayList<Part> photos;
//        Entitie bean = null;
//
//        CheckFormResult result = checkAndSave(req,className,dao);
//        if (result.listErreur.isEmpty()){
//
//            try {
//                photos = (ArrayList<Part>) req.getParts();
//            } catch (Exception e) {
//                listErreur.put(CHAMP_PHOTO,"Un des fichiers à importer est corrompu, ou est introuvable");
//                return listErreur;
//            }
//
//            for (Part photo : photos) {
//                totalSizeFiles += photo.getSize();
//                if (totalSizeFiles < TOTAL_PHOTO_MAX) {
//                    if (photo.getSize() > TAILLE_PHOTO_MAX) {
//                        listErreur.put(CHAMP_PHOTO, "Chaque fichier doit avoir une taille inférieure à 2,5Mo");
//                        return listErreur;
//                    }
//                }else {
//                    listErreur.put(CHAMP_PHOTO, "La taille totale des fichiers doit être inférieure à 25 Mo");
//                    return listErreur;
//                }
//            }
//
//            for (Part photo : photos) {
//                dateTime = formatDate(LocalDateTime.now());
//                // Création d'un nom unique à l'aide du timestamp et de l'index dans la collection
//                nomFichierGenere = dateTime + photos.indexOf(photo);
//                try {
//                    Photo p = enregistrerFichiers(photo, nomFichierGenere, CHEMIN_UPLOAD);
//                    if (result.entitie instanceof Spot){
//                        ((Spot) result.entitie).addPhoto(p);
//                        PhotoDaoImpl photoDao = new PhotoDaoImpl();
//                        photoDao.save(p);
//                    }
//
//                } catch (Exception e) {
//                    listErreur.put(CHAMP_PHOTO, e.getMessage());
//                    return listErreur;
//                }
//            }
//        }
//        return listErreur;
//    }
//
//    private static Photo enregistrerFichiers(Part file, String nomFichier, String chemin) throws Exception {
//
//        Photo photo = new Photo();
//
//        String extFichier;
//        InputStream contenuFichier=null;
//        try {
//            contenuFichier = file.getInputStream();
//        } catch (IOException e) {
//            throw new Exception ("Photo introuvable");
//        }
//
//        extFichier = getExtensionfichier(file);
//        nomFichier = nomFichier + "." + extFichier;
//
//        try {
//            validationFichier(contenuFichier);
//        } catch (Exception e) {
//            throw new Exception ("Une ou plusieurs images sont vides");
//        }
//        photo.setNom(nomFichier);
//
//        try{
//            ecrireFichier( contenuFichier, nomFichier, chemin);
//        }catch (Exception e){
//            throw new Exception ("Erreur lors de l'écriture du fichier sur le disque");
//        }
//        return photo;
//    }
//
//    private static String getExtensionfichier(Part part) {
//        for (String contentDisposition : part.getHeader("content-disposition").split(";")){
//            if(contentDisposition.trim().startsWith("filename")){
//                return contentDisposition.substring(contentDisposition.indexOf('.')+ 1).trim().replace("\"","");
//            }
//        }
//        return null;
//    }
//
//    private static void ecrireFichier(InputStream contenu, String nomFichier, String chemin) throws IOException {
//        BufferedInputStream entree = null;
//        BufferedOutputStream sortie = null;
//        try{
//            entree = new BufferedInputStream(contenu, TAILLE_TAMPON);
//            sortie = new BufferedOutputStream (new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);
//            byte[] tampon = new byte[TAILLE_TAMPON];
//            int longueur = 0;
//            while(( longueur = entree.read(tampon)) > 0){
//                sortie.write(tampon, 0, longueur);
//            }
//        }finally{
//            try{
//                sortie.close();
//            }catch( IOException ignore){}
//            try{
//                entree.close();
//            }catch (IOException ignore ){}
//        }
//    }
//
//    private static void validationFichier(InputStream contenuFichier) throws Exception {
//        if (contenuFichier == null){
//            throw new Exception("Fichier vide");
//        }
//    }
//
//    private static String formatDate(LocalDateTime now) {
//        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        String formattedDate = now.format(myFormatObj);
//        return formattedDate;
//    }
//
//}
