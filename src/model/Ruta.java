/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WiseKingJeremy
 */
public class Ruta {
    
    private int idRuta;
    private String nombreRuta;
    private String direccion;
    private String distanciaKm;
    private String duracionEstimada;
    private int precioBase;
    private boolean estado;

    public Ruta() {
    }

    public Ruta(int idRuta, String nombreRuta, String direccion, String distanciaKm, String duracionEstimada, int precioBase, boolean estado) {
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
        this.direccion = direccion;
        this.distanciaKm = distanciaKm;
        this.duracionEstimada = duracionEstimada;
        this.precioBase = precioBase;
        this.estado = true;
    }

    public Ruta(String nombreRuta, String direccion, String distanciaKm, String duracionEstimada, int precioBase, boolean estado) {
        this.nombreRuta = nombreRuta;
        this.direccion = direccion;
        this.distanciaKm = distanciaKm;
        this.duracionEstimada = duracionEstimada;
        this.precioBase = precioBase;
        this.estado = true;
    }
    
    

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(String distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public String getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(String duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public int getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString(){
        return "ID: " + idRuta +
                "Nombre: " + nombreRuta +
                "Direccion: " + direccion +
                "Distacia Km: " + distanciaKm +
                "Duracion: " + duracionEstimada +
                "Precio base: " + precioBase;      
                }
        
}
