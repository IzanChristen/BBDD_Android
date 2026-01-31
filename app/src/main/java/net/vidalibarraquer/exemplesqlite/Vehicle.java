package net.vidalibarraquer.exemplesqlite;

public class Vehicle {

    private int id;
    private String nom;
    private String cognoms;
    private String telefon;
    private String marca;
    private String model;
    private String matricula;

    public Vehicle() {}

    public Vehicle(int id, String nom, String cognoms, String telefon,
                   String marca, String model, String matricula) {
        this.id = id;
        this.nom = nom;
        this.cognoms = cognoms;
        this.telefon = telefon;
        this.marca = marca;
        this.model = model;
        this.matricula = matricula;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCognoms() { return cognoms; }
    public void setCognoms(String cognoms) { this.cognoms = cognoms; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}
