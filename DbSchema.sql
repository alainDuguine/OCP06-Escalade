--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.5

-- Started on 2019-09-20 10:45:59

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 35535)
-- Name: commentaire; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.commentaire (
    id bigint NOT NULL,
    contenu character varying(255),
    dateformat character varying(255),
    datetime timestamp without time zone,
    utilisateur_id bigint
);


ALTER TABLE public.commentaire OWNER TO adm_escalade;

--
-- TOC entry 196 (class 1259 OID 35533)
-- Name: commentaire_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.commentaire_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.commentaire_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3029 (class 0 OID 0)
-- Dependencies: 196
-- Name: commentaire_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.commentaire_id_seq OWNED BY public.commentaire.id;


--
-- TOC entry 198 (class 1259 OID 35544)
-- Name: commentairesecteur; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.commentairesecteur (
    id bigint NOT NULL,
    secteur_id bigint,
    utilisateur_id bigint
);


ALTER TABLE public.commentairesecteur OWNER TO adm_escalade;

--
-- TOC entry 199 (class 1259 OID 35549)
-- Name: commentairespot; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.commentairespot (
    id bigint NOT NULL,
    spot_id bigint,
    utilisateur_id bigint
);


ALTER TABLE public.commentairespot OWNER TO adm_escalade;

--
-- TOC entry 200 (class 1259 OID 35554)
-- Name: commentairevoie; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.commentairevoie (
    id bigint NOT NULL,
    voie_id bigint,
    utilisateur_id bigint
);


ALTER TABLE public.commentairevoie OWNER TO adm_escalade;

--
-- TOC entry 202 (class 1259 OID 35561)
-- Name: complement; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.complement (
    id bigint NOT NULL,
    contenu character varying(2000),
    dateheure timestamp without time zone,
    utilisateur_id bigint
);


ALTER TABLE public.complement OWNER TO adm_escalade;

--
-- TOC entry 201 (class 1259 OID 35559)
-- Name: complement_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.complement_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.complement_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3030 (class 0 OID 0)
-- Dependencies: 201
-- Name: complement_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.complement_id_seq OWNED BY public.complement.id;


--
-- TOC entry 203 (class 1259 OID 35570)
-- Name: complementsecteur; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.complementsecteur (
    id bigint NOT NULL,
    secteur_id bigint,
    utilisateur_id bigint
);


ALTER TABLE public.complementsecteur OWNER TO adm_escalade;

--
-- TOC entry 204 (class 1259 OID 35575)
-- Name: complementspot; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.complementspot (
    id bigint NOT NULL,
    spot_id bigint,
    utilisateur_id bigint
);


ALTER TABLE public.complementspot OWNER TO adm_escalade;

--
-- TOC entry 205 (class 1259 OID 35580)
-- Name: complementvoie; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.complementvoie (
    id bigint NOT NULL,
    voie_id bigint,
    utilisateur_id bigint
);


ALTER TABLE public.complementvoie OWNER TO adm_escalade;

--
-- TOC entry 207 (class 1259 OID 35587)
-- Name: cotation; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.cotation (
    id bigint NOT NULL,
    code character varying(2)
);


ALTER TABLE public.cotation OWNER TO adm_escalade;

--
-- TOC entry 206 (class 1259 OID 35585)
-- Name: cotation_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.cotation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cotation_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3031 (class 0 OID 0)
-- Dependencies: 206
-- Name: cotation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.cotation_id_seq OWNED BY public.cotation.id;


--
-- TOC entry 208 (class 1259 OID 35593)
-- Name: departement; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.departement (
    code character varying(255) NOT NULL,
    nom character varying(255)
);


ALTER TABLE public.departement OWNER TO adm_escalade;

--
-- TOC entry 210 (class 1259 OID 35603)
-- Name: photo; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.photo (
    id bigint NOT NULL,
    nom character varying(255)
);


ALTER TABLE public.photo OWNER TO adm_escalade;

--
-- TOC entry 209 (class 1259 OID 35601)
-- Name: photo_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.photo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.photo_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3032 (class 0 OID 0)
-- Dependencies: 209
-- Name: photo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.photo_id_seq OWNED BY public.photo.id;


--
-- TOC entry 211 (class 1259 OID 35609)
-- Name: photosecteur; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.photosecteur (
    id bigint NOT NULL,
    secteur_id bigint
);


ALTER TABLE public.photosecteur OWNER TO adm_escalade;

--
-- TOC entry 212 (class 1259 OID 35614)
-- Name: photospot; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.photospot (
    id bigint NOT NULL,
    spot_id bigint
);


ALTER TABLE public.photospot OWNER TO adm_escalade;

--
-- TOC entry 213 (class 1259 OID 35619)
-- Name: photovoie; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.photovoie (
    id bigint NOT NULL,
    voie_id bigint
);


ALTER TABLE public.photovoie OWNER TO adm_escalade;

--
-- TOC entry 215 (class 1259 OID 35626)
-- Name: reservation; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.reservation (
    id bigint NOT NULL,
    datedernierstatut character varying(255),
    dernierstatut character varying(255),
    emprunteur_id bigint,
    preteur_id bigint,
    topo_id bigint
);


ALTER TABLE public.reservation OWNER TO adm_escalade;

--
-- TOC entry 214 (class 1259 OID 35624)
-- Name: reservation_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.reservation_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3033 (class 0 OID 0)
-- Dependencies: 214
-- Name: reservation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.reservation_id_seq OWNED BY public.reservation.id;


--
-- TOC entry 217 (class 1259 OID 35637)
-- Name: reservationhistorique; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.reservationhistorique (
    id bigint NOT NULL,
    datetime timestamp without time zone,
    reservationstatut character varying(255),
    reservation_id bigint
);


ALTER TABLE public.reservationhistorique OWNER TO adm_escalade;

--
-- TOC entry 216 (class 1259 OID 35635)
-- Name: reservationhistorique_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.reservationhistorique_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.reservationhistorique_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3034 (class 0 OID 0)
-- Dependencies: 216
-- Name: reservationhistorique_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.reservationhistorique_id_seq OWNED BY public.reservationhistorique.id;


--
-- TOC entry 219 (class 1259 OID 35645)
-- Name: secteur; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.secteur (
    id bigint NOT NULL,
    description character varying(2000),
    nom character varying(50),
    spot_id bigint,
    utilisateur_id bigint
);


ALTER TABLE public.secteur OWNER TO adm_escalade;

--
-- TOC entry 218 (class 1259 OID 35643)
-- Name: secteur_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.secteur_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.secteur_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3035 (class 0 OID 0)
-- Dependencies: 218
-- Name: secteur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.secteur_id_seq OWNED BY public.secteur.id;


--
-- TOC entry 221 (class 1259 OID 35656)
-- Name: spot; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.spot (
    id bigint NOT NULL,
    adresse character varying(255),
    description character varying(2000),
    nom character varying(50),
    officiel boolean,
    departement_code character varying(255),
    utilisateur_id bigint,
    ville_id bigint
);


ALTER TABLE public.spot OWNER TO adm_escalade;

--
-- TOC entry 220 (class 1259 OID 35654)
-- Name: spot_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.spot_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.spot_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3036 (class 0 OID 0)
-- Dependencies: 220
-- Name: spot_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.spot_id_seq OWNED BY public.spot.id;


--
-- TOC entry 223 (class 1259 OID 35667)
-- Name: topo; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.topo (
    id bigint NOT NULL,
    dateedition character varying(255),
    description character varying(2000),
    disponible boolean NOT NULL,
    nom character varying(255),
    utilisateur_id bigint
);


ALTER TABLE public.topo OWNER TO adm_escalade;

--
-- TOC entry 222 (class 1259 OID 35665)
-- Name: topo_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.topo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.topo_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3037 (class 0 OID 0)
-- Dependencies: 222
-- Name: topo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.topo_id_seq OWNED BY public.topo.id;


--
-- TOC entry 224 (class 1259 OID 35676)
-- Name: topo_spot; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.topo_spot (
    topos_id bigint NOT NULL,
    spots_id bigint NOT NULL
);


ALTER TABLE public.topo_spot OWNER TO adm_escalade;

--
-- TOC entry 226 (class 1259 OID 35681)
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.utilisateur (
    id bigint NOT NULL,
    admin boolean NOT NULL,
    email character varying(255),
    encryptedpassword character varying(255),
    nom character varying(255),
    prenom character varying(255),
    salt bytea,
    username character varying(255)
);


ALTER TABLE public.utilisateur OWNER TO adm_escalade;

--
-- TOC entry 225 (class 1259 OID 35679)
-- Name: utilisateur_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.utilisateur_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.utilisateur_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3038 (class 0 OID 0)
-- Dependencies: 225
-- Name: utilisateur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.utilisateur_id_seq OWNED BY public.utilisateur.id;


--
-- TOC entry 228 (class 1259 OID 35692)
-- Name: ville; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.ville (
    id bigint NOT NULL,
    nom character varying(255),
    departement_code character varying(255)
);


ALTER TABLE public.ville OWNER TO adm_escalade;

--
-- TOC entry 227 (class 1259 OID 35690)
-- Name: ville_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.ville_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ville_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3039 (class 0 OID 0)
-- Dependencies: 227
-- Name: ville_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.ville_id_seq OWNED BY public.ville.id;


--
-- TOC entry 230 (class 1259 OID 35703)
-- Name: voie; Type: TABLE; Schema: public; Owner: adm_escalade
--

CREATE TABLE public.voie (
    id bigint NOT NULL,
    altitude integer NOT NULL,
    description character varying(2000),
    nblongueurs integer NOT NULL,
    nom character varying(50),
    cotation_id bigint,
    secteur_id bigint,
    utilisateur_id bigint
);


ALTER TABLE public.voie OWNER TO adm_escalade;

--
-- TOC entry 229 (class 1259 OID 35701)
-- Name: voie_id_seq; Type: SEQUENCE; Schema: public; Owner: adm_escalade
--

CREATE SEQUENCE public.voie_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.voie_id_seq OWNER TO adm_escalade;

--
-- TOC entry 3040 (class 0 OID 0)
-- Dependencies: 229
-- Name: voie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: adm_escalade
--

ALTER SEQUENCE public.voie_id_seq OWNED BY public.voie.id;


--
-- TOC entry 2805 (class 2604 OID 35538)
-- Name: commentaire id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentaire ALTER COLUMN id SET DEFAULT nextval('public.commentaire_id_seq'::regclass);


--
-- TOC entry 2806 (class 2604 OID 35564)
-- Name: complement id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complement ALTER COLUMN id SET DEFAULT nextval('public.complement_id_seq'::regclass);


--
-- TOC entry 2807 (class 2604 OID 35590)
-- Name: cotation id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.cotation ALTER COLUMN id SET DEFAULT nextval('public.cotation_id_seq'::regclass);


--
-- TOC entry 2808 (class 2604 OID 35606)
-- Name: photo id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photo ALTER COLUMN id SET DEFAULT nextval('public.photo_id_seq'::regclass);


--
-- TOC entry 2809 (class 2604 OID 35629)
-- Name: reservation id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.reservation ALTER COLUMN id SET DEFAULT nextval('public.reservation_id_seq'::regclass);


--
-- TOC entry 2810 (class 2604 OID 35640)
-- Name: reservationhistorique id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.reservationhistorique ALTER COLUMN id SET DEFAULT nextval('public.reservationhistorique_id_seq'::regclass);


--
-- TOC entry 2811 (class 2604 OID 35648)
-- Name: secteur id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.secteur ALTER COLUMN id SET DEFAULT nextval('public.secteur_id_seq'::regclass);


--
-- TOC entry 2812 (class 2604 OID 35659)
-- Name: spot id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.spot ALTER COLUMN id SET DEFAULT nextval('public.spot_id_seq'::regclass);


--
-- TOC entry 2813 (class 2604 OID 35670)
-- Name: topo id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.topo ALTER COLUMN id SET DEFAULT nextval('public.topo_id_seq'::regclass);


--
-- TOC entry 2814 (class 2604 OID 35684)
-- Name: utilisateur id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.utilisateur ALTER COLUMN id SET DEFAULT nextval('public.utilisateur_id_seq'::regclass);


--
-- TOC entry 2815 (class 2604 OID 35695)
-- Name: ville id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.ville ALTER COLUMN id SET DEFAULT nextval('public.ville_id_seq'::regclass);


--
-- TOC entry 2816 (class 2604 OID 35706)
-- Name: voie id; Type: DEFAULT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.voie ALTER COLUMN id SET DEFAULT nextval('public.voie_id_seq'::regclass);


--
-- TOC entry 2818 (class 2606 OID 35543)
-- Name: commentaire commentaire_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentaire
    ADD CONSTRAINT commentaire_pkey PRIMARY KEY (id);


--
-- TOC entry 2820 (class 2606 OID 35548)
-- Name: commentairesecteur commentairesecteur_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairesecteur
    ADD CONSTRAINT commentairesecteur_pkey PRIMARY KEY (id);


--
-- TOC entry 2822 (class 2606 OID 35553)
-- Name: commentairespot commentairespot_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairespot
    ADD CONSTRAINT commentairespot_pkey PRIMARY KEY (id);


--
-- TOC entry 2824 (class 2606 OID 35558)
-- Name: commentairevoie commentairevoie_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairevoie
    ADD CONSTRAINT commentairevoie_pkey PRIMARY KEY (id);


--
-- TOC entry 2826 (class 2606 OID 35569)
-- Name: complement complement_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complement
    ADD CONSTRAINT complement_pkey PRIMARY KEY (id);


--
-- TOC entry 2828 (class 2606 OID 35574)
-- Name: complementsecteur complementsecteur_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementsecteur
    ADD CONSTRAINT complementsecteur_pkey PRIMARY KEY (id);


--
-- TOC entry 2830 (class 2606 OID 35579)
-- Name: complementspot complementspot_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementspot
    ADD CONSTRAINT complementspot_pkey PRIMARY KEY (id);


--
-- TOC entry 2832 (class 2606 OID 35584)
-- Name: complementvoie complementvoie_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementvoie
    ADD CONSTRAINT complementvoie_pkey PRIMARY KEY (id);


--
-- TOC entry 2834 (class 2606 OID 35592)
-- Name: cotation cotation_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.cotation
    ADD CONSTRAINT cotation_pkey PRIMARY KEY (id);


--
-- TOC entry 2836 (class 2606 OID 35600)
-- Name: departement departement_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.departement
    ADD CONSTRAINT departement_pkey PRIMARY KEY (code);


--
-- TOC entry 2838 (class 2606 OID 35608)
-- Name: photo photo_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photo
    ADD CONSTRAINT photo_pkey PRIMARY KEY (id);


--
-- TOC entry 2840 (class 2606 OID 35613)
-- Name: photosecteur photosecteur_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photosecteur
    ADD CONSTRAINT photosecteur_pkey PRIMARY KEY (id);


--
-- TOC entry 2842 (class 2606 OID 35618)
-- Name: photospot photospot_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photospot
    ADD CONSTRAINT photospot_pkey PRIMARY KEY (id);


--
-- TOC entry 2844 (class 2606 OID 35623)
-- Name: photovoie photovoie_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photovoie
    ADD CONSTRAINT photovoie_pkey PRIMARY KEY (id);


--
-- TOC entry 2846 (class 2606 OID 35634)
-- Name: reservation reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);


--
-- TOC entry 2848 (class 2606 OID 35642)
-- Name: reservationhistorique reservationhistorique_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.reservationhistorique
    ADD CONSTRAINT reservationhistorique_pkey PRIMARY KEY (id);


--
-- TOC entry 2850 (class 2606 OID 35653)
-- Name: secteur secteur_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.secteur
    ADD CONSTRAINT secteur_pkey PRIMARY KEY (id);


--
-- TOC entry 2852 (class 2606 OID 35664)
-- Name: spot spot_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.spot
    ADD CONSTRAINT spot_pkey PRIMARY KEY (id);


--
-- TOC entry 2854 (class 2606 OID 35675)
-- Name: topo topo_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.topo
    ADD CONSTRAINT topo_pkey PRIMARY KEY (id);


--
-- TOC entry 2856 (class 2606 OID 35689)
-- Name: utilisateur utilisateur_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_pkey PRIMARY KEY (id);


--
-- TOC entry 2858 (class 2606 OID 35700)
-- Name: ville ville_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.ville
    ADD CONSTRAINT ville_pkey PRIMARY KEY (id);


--
-- TOC entry 2860 (class 2606 OID 35711)
-- Name: voie voie_pkey; Type: CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.voie
    ADD CONSTRAINT voie_pkey PRIMARY KEY (id);


--
-- TOC entry 2875 (class 2606 OID 35782)
-- Name: complementspot fk1j54pf9kajn3p86126pilnj5b; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementspot
    ADD CONSTRAINT fk1j54pf9kajn3p86126pilnj5b FOREIGN KEY (spot_id) REFERENCES public.spot(id);


--
-- TOC entry 2891 (class 2606 OID 35862)
-- Name: secteur fk24ci201b8uup0yxivsl68vd22; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.secteur
    ADD CONSTRAINT fk24ci201b8uup0yxivsl68vd22 FOREIGN KEY (spot_id) REFERENCES public.spot(id);


--
-- TOC entry 2870 (class 2606 OID 35757)
-- Name: commentairevoie fk2j3w4l5uuoq3hbwwjktxv1o4h; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairevoie
    ADD CONSTRAINT fk2j3w4l5uuoq3hbwwjktxv1o4h FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2884 (class 2606 OID 35827)
-- Name: photospot fk2tpy138fulj55pi0krn3nn4r8; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photospot
    ADD CONSTRAINT fk2tpy138fulj55pi0krn3nn4r8 FOREIGN KEY (id) REFERENCES public.photo(id) ON DELETE CASCADE;


--
-- TOC entry 2879 (class 2606 OID 35802)
-- Name: complementvoie fk37g7uqor0yytay3yns9jpehr6; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementvoie
    ADD CONSTRAINT fk37g7uqor0yytay3yns9jpehr6 FOREIGN KEY (id) REFERENCES public.complement(id);


--
-- TOC entry 2882 (class 2606 OID 35817)
-- Name: photosecteur fk3qtjdgnvn2pv3a9k08vm3ovyr; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photosecteur
    ADD CONSTRAINT fk3qtjdgnvn2pv3a9k08vm3ovyr FOREIGN KEY (id) REFERENCES public.photo(id) ON DELETE CASCADE;


--
-- TOC entry 2867 (class 2606 OID 35742)
-- Name: commentairespot fk3ugra6m0kbvw0vx2a2ablw6q8; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairespot
    ADD CONSTRAINT fk3ugra6m0kbvw0vx2a2ablw6q8 FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2864 (class 2606 OID 35727)
-- Name: commentairesecteur fk414pyocinu53avyeqnari65yl; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairesecteur
    ADD CONSTRAINT fk414pyocinu53avyeqnari65yl FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2895 (class 2606 OID 35882)
-- Name: spot fk4af9n5wp80gwmhs9i3w0dhf6d; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.spot
    ADD CONSTRAINT fk4af9n5wp80gwmhs9i3w0dhf6d FOREIGN KEY (ville_id) REFERENCES public.ville(id);


--
-- TOC entry 2862 (class 2606 OID 35717)
-- Name: commentairesecteur fk54nj7a1a65fi0pu2i5dvf51sq; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairesecteur
    ADD CONSTRAINT fk54nj7a1a65fi0pu2i5dvf51sq FOREIGN KEY (secteur_id) REFERENCES public.secteur(id);


--
-- TOC entry 2871 (class 2606 OID 35762)
-- Name: complement fk5ef2b29y91je00nt9p16ekjnr; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complement
    ADD CONSTRAINT fk5ef2b29y91je00nt9p16ekjnr FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2877 (class 2606 OID 35792)
-- Name: complementspot fk5ybmk0bh8w1bfbpotv7v7143p; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementspot
    ADD CONSTRAINT fk5ybmk0bh8w1bfbpotv7v7143p FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2896 (class 2606 OID 35887)
-- Name: topo fk7jnb0511aod2q06sg3seytggp; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.topo
    ADD CONSTRAINT fk7jnb0511aod2q06sg3seytggp FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2893 (class 2606 OID 35872)
-- Name: spot fk7q254mh4kmj6rhtqhmkgb07b5; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.spot
    ADD CONSTRAINT fk7q254mh4kmj6rhtqhmkgb07b5 FOREIGN KEY (departement_code) REFERENCES public.departement(code);


--
-- TOC entry 2888 (class 2606 OID 35847)
-- Name: reservation fk8esckvkuhlcw6mmlmkn5802ac; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fk8esckvkuhlcw6mmlmkn5802ac FOREIGN KEY (preteur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2861 (class 2606 OID 35712)
-- Name: commentaire fkb7i29ppss6dbhwjihif0x2sg6; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentaire
    ADD CONSTRAINT fkb7i29ppss6dbhwjihif0x2sg6 FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2892 (class 2606 OID 35867)
-- Name: secteur fkbcsd93dx0vl050pjyoltlv79c; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.secteur
    ADD CONSTRAINT fkbcsd93dx0vl050pjyoltlv79c FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2866 (class 2606 OID 35737)
-- Name: commentairespot fkbryw1k8vc4oarotvxdxw77ah7; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairespot
    ADD CONSTRAINT fkbryw1k8vc4oarotvxdxw77ah7 FOREIGN KEY (id) REFERENCES public.commentaire(id) ON DELETE CASCADE;


--
-- TOC entry 2886 (class 2606 OID 35837)
-- Name: photovoie fkbsv2fw70qdfg67l9upuctr27w; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photovoie
    ADD CONSTRAINT fkbsv2fw70qdfg67l9upuctr27w FOREIGN KEY (id) REFERENCES public.photo(id) ON DELETE CASCADE;


--
-- TOC entry 2881 (class 2606 OID 35812)
-- Name: photosecteur fkde842uq9tv3ua9u4pm6d1dyf4; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photosecteur
    ADD CONSTRAINT fkde842uq9tv3ua9u4pm6d1dyf4 FOREIGN KEY (secteur_id) REFERENCES public.secteur(id);


--
-- TOC entry 2901 (class 2606 OID 35912)
-- Name: voie fkdkri2vygcek6r4nb4lf601mjm; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.voie
    ADD CONSTRAINT fkdkri2vygcek6r4nb4lf601mjm FOREIGN KEY (secteur_id) REFERENCES public.secteur(id);


--
-- TOC entry 2885 (class 2606 OID 35832)
-- Name: photovoie fkfjijwe9e968c83ccijh05hjb4; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photovoie
    ADD CONSTRAINT fkfjijwe9e968c83ccijh05hjb4 FOREIGN KEY (voie_id) REFERENCES public.voie(id);


--
-- TOC entry 2898 (class 2606 OID 35897)
-- Name: topo_spot fkfpl9gchps64pl4bnf74fofwxq; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.topo_spot
    ADD CONSTRAINT fkfpl9gchps64pl4bnf74fofwxq FOREIGN KEY (topos_id) REFERENCES public.topo(id);


--
-- TOC entry 2865 (class 2606 OID 35732)
-- Name: commentairespot fkg5f8h2mrcikboiymuchmxll74; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairespot
    ADD CONSTRAINT fkg5f8h2mrcikboiymuchmxll74 FOREIGN KEY (spot_id) REFERENCES public.spot(id);


--
-- TOC entry 2899 (class 2606 OID 35902)
-- Name: ville fkgguta1qphow0m5q020i0c6q3s; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.ville
    ADD CONSTRAINT fkgguta1qphow0m5q020i0c6q3s FOREIGN KEY (departement_code) REFERENCES public.departement(code);


--
-- TOC entry 2883 (class 2606 OID 35822)
-- Name: photospot fkhnrv8xapjcfo1k2djg7fesb4o; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.photospot
    ADD CONSTRAINT fkhnrv8xapjcfo1k2djg7fesb4o FOREIGN KEY (spot_id) REFERENCES public.spot(id);


--
-- TOC entry 2889 (class 2606 OID 35852)
-- Name: reservation fkkb2wyhq39jwa4g4mkwahbgc00; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fkkb2wyhq39jwa4g4mkwahbgc00 FOREIGN KEY (topo_id) REFERENCES public.topo(id);


--
-- TOC entry 2869 (class 2606 OID 35752)
-- Name: commentairevoie fkkfjbsstsrj0ndl0icxwvvwuhw; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairevoie
    ADD CONSTRAINT fkkfjbsstsrj0ndl0icxwvvwuhw FOREIGN KEY (id) REFERENCES public.commentaire(id) ON DELETE CASCADE;


--
-- TOC entry 2878 (class 2606 OID 35797)
-- Name: complementvoie fkki5olbgg0gpenokojpui1wybk; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementvoie
    ADD CONSTRAINT fkki5olbgg0gpenokojpui1wybk FOREIGN KEY (voie_id) REFERENCES public.voie(id);


--
-- TOC entry 2902 (class 2606 OID 35917)
-- Name: voie fkl7lfk9b1t9vcc85bwcrqb59bm; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.voie
    ADD CONSTRAINT fkl7lfk9b1t9vcc85bwcrqb59bm FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2872 (class 2606 OID 35767)
-- Name: complementsecteur fkmd5msmt43vyymjtgxwd0ljv6n; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementsecteur
    ADD CONSTRAINT fkmd5msmt43vyymjtgxwd0ljv6n FOREIGN KEY (secteur_id) REFERENCES public.secteur(id);


--
-- TOC entry 2880 (class 2606 OID 35807)
-- Name: complementvoie fkmeo6e03vrf6lrldkv048lxav8; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementvoie
    ADD CONSTRAINT fkmeo6e03vrf6lrldkv048lxav8 FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2876 (class 2606 OID 35787)
-- Name: complementspot fkmgp25h2yb00ygbvuxy4y7mxtx; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementspot
    ADD CONSTRAINT fkmgp25h2yb00ygbvuxy4y7mxtx FOREIGN KEY (id) REFERENCES public.complement(id);


--
-- TOC entry 2900 (class 2606 OID 35907)
-- Name: voie fkmm03cwduvjypmvrfqhw3wirfn; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.voie
    ADD CONSTRAINT fkmm03cwduvjypmvrfqhw3wirfn FOREIGN KEY (cotation_id) REFERENCES public.cotation(id);


--
-- TOC entry 2873 (class 2606 OID 35772)
-- Name: complementsecteur fknqh7gg9t1rai7hcdn5wk8l24h; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementsecteur
    ADD CONSTRAINT fknqh7gg9t1rai7hcdn5wk8l24h FOREIGN KEY (id) REFERENCES public.complement(id);


--
-- TOC entry 2890 (class 2606 OID 35857)
-- Name: reservationhistorique fknw4b0iu9k9t6r4s8nf7xnkl70; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.reservationhistorique
    ADD CONSTRAINT fknw4b0iu9k9t6r4s8nf7xnkl70 FOREIGN KEY (reservation_id) REFERENCES public.reservation(id);


--
-- TOC entry 2863 (class 2606 OID 35722)
-- Name: commentairesecteur fkohxj441libiqw6oafmw0mab1r; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairesecteur
    ADD CONSTRAINT fkohxj441libiqw6oafmw0mab1r FOREIGN KEY (id) REFERENCES public.commentairespot(id) ON DELETE CASCADE;


--
-- TOC entry 2887 (class 2606 OID 35842)
-- Name: reservation fkpttr6ux0ur556hn8eq4vyxl6f; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fkpttr6ux0ur556hn8eq4vyxl6f FOREIGN KEY (emprunteur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2894 (class 2606 OID 35877)
-- Name: spot fkq2q5uk80dgpnjd97bt0ffaa07; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.spot
    ADD CONSTRAINT fkq2q5uk80dgpnjd97bt0ffaa07 FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2874 (class 2606 OID 35777)
-- Name: complementsecteur fkr73j3lb6ywsu6i90pyaiwle33; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.complementsecteur
    ADD CONSTRAINT fkr73j3lb6ywsu6i90pyaiwle33 FOREIGN KEY (utilisateur_id) REFERENCES public.utilisateur(id);


--
-- TOC entry 2897 (class 2606 OID 35892)
-- Name: topo_spot fkstsffs0bbhkiavnfoc1aknon8; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.topo_spot
    ADD CONSTRAINT fkstsffs0bbhkiavnfoc1aknon8 FOREIGN KEY (spots_id) REFERENCES public.spot(id);


--
-- TOC entry 2868 (class 2606 OID 35747)
-- Name: commentairevoie fkt1ncf3pnjw8mc7m8606qj4d7f; Type: FK CONSTRAINT; Schema: public; Owner: adm_escalade
--

ALTER TABLE ONLY public.commentairevoie
    ADD CONSTRAINT fkt1ncf3pnjw8mc7m8606qj4d7f FOREIGN KEY (voie_id) REFERENCES public.voie(id);


-- Completed on 2019-09-20 10:45:59

--
-- PostgreSQL database dump complete
--

