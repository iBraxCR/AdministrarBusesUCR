/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author Moose
 */
public class Chofer {
    
   private int idChofer;
   private String nombre;
   private String primerApellido;
   private String segundoApellido;
   private String cedula;
   private String telefono;
   private String correo;
   private String licencia;
   private Date fechaContratacion;
   private boolean estado;

    public Chofer() {
    }
  
    public Chofer(String nombre, String primerApellido, String segundoNombre, String cedula, String telefono, String correo, String licencia, Date fechaContratacion, boolean estado) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.correo = correo;
        this.licencia = licencia;
        this.fechaContratacion = fechaContratacion;
        this.estado = true;
    }
      
    public Chofer(int idChofer, String nombre, String primerApellido, String segundoNombre, String cedula, String telefono, String correo, String licencia, Date fechaContratacion, String estado) {
        this.idChofer = idChofer;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.correo = correo;
        this.licencia = licencia;
        this.fechaContratacion = fechaContratacion;
        this.estado = true;
    }

    public int getIdChofer() {
        return idChofer;
    }

    public void setIdChofer(int idChofer) {
        this.idChofer = idChofer;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido () {
        return segundoApellido;
    }

    public void setSegundoApellido (String segundoNombre) {
        this.segundoApellido = segundoNombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    public String toString(){
        return "ID: " + idChofer +
                "Nombre: " + nombre +
                "Primer apellido: " + primerApellido +
                "Segundo apellido: " + segundoApellido +
                "Cedula: " + cedula +
                "Telefono: " + telefono + 
                "Correo: " + correo +
                "Licencia: " + licencia +
                "Fecha de contratacion: " + fechaContratacion +
                " Estado: " + (estado ? "Activo" : "Inactivo");
    }
}