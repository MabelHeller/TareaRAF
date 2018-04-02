/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Heller
 */
public class Vehicles {

    //atributos
    private String name;
    private int year;
    private float mileage;
    private boolean american;
    private int serie;

    //constructores
    public Vehicles() {
        this.name = "";
        this.year = 0;
        this.mileage = 0;
        this.american = true;
        this.serie = 0;
    }

    public Vehicles(String nombre, int año, float kilometraje, boolean americano, int serie) {
        this.name = nombre;
        this.year = año;
        this.mileage = kilometraje;
        this.american = americano;
        this.serie = serie;
    }

    //métodos accesores
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public boolean isAmerican() {
        return american;
    }

    public void setAmerican(boolean american) {
        this.american = american;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "Vehicles{" + "name=" + name + ", year=" + year + ", mileage=" + mileage + ", american=" + american + ", serie=" + serie + '}';
    }

    public int sizeInBytes() {
        //long necesita dos bytes 
        //String: necesita 2 bytes de espacio
        return this.getName().length() * 2 + 4 + 4 + 2 + 4;
    }
}
