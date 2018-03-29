package com.example.timo.cartet;

/**
 * Created by timojansen on 23-3-18.
 */

class Car {
    private String aantalCilinders;
    private String aantalZitplaatsen;
    private String cilinderInhoud;
    private String kleur;
    private String handelsBenaming;
    private String voertuigType;
    private String kenteken;
    private String lengte;
    private String massa;
    private String merk;
    private String voertuigSoort;


    public String getAantalCilinders() {
        return aantalCilinders;
    }

    public String getAantalZitplaatsen() {
        return aantalZitplaatsen;
    }

    public String getCilinderInhoud() {
        return cilinderInhoud;
    }

    public String getKleur() {
        return kleur;
    }

    public String getHandelsBenaming() {
        return handelsBenaming;
    }

    public String getVoertuigType() {
        return voertuigType;
    }

    public String getKenteken() {
        return kenteken;
    }

    public String getLengte() {
        return lengte;
    }

    public String getMassa() {
        return massa;
    }

    public String getMerk() {
        return merk;
    }

    public String getVoertuigSoort() {
        return voertuigSoort;
    }

    public Car(String aantalCilinders, String aantalZitplaatsen, String cilinderInhoud, String kleur,
               String handelsBenaming, String voertuigType, String kenteken, String lengte,
               String massa, String merk, String voertuigSoort) {
        this.aantalCilinders = aantalCilinders;
        this.aantalZitplaatsen = aantalZitplaatsen;
        this.cilinderInhoud = cilinderInhoud;
        this.kleur = kleur;
        this.handelsBenaming = handelsBenaming;
        this.voertuigType = voertuigType;
        this.kenteken = kenteken;
        this.lengte = lengte;
        this.massa = massa;
        this.merk = merk;
        this.voertuigSoort = voertuigSoort;
    }

    public String ToString(){
        return "Kenteken: " + getKenteken() + "\n"
                + "Merk: " + getMerk() + "\n"
                + "Voertuigsoort: " + getVoertuigSoort() + "\n"
                + "Voertuigtype: " + getVoertuigType() + "\n"
                + "Kleur: " + getKleur() + "\n"
                + "Serie: " + getHandelsBenaming() + "\n"
                + "Cilinderinhoud: " + getCilinderInhoud() + "\n"
                + "Aantal cilinders: " + getAantalCilinders() + "\n"
                + "Lengte: " + getLengte() + "\n"
                + "Massa: " + getMassa();
    }
}
