/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WiseKingJeremy
 */
public class Cliente {
    
    private int idCliente;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String cedula;
    private String telefono;
    private String email;
    private boolean estado;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, String primerApellido, String segundoApellido, String cedula, String telefono, String email, boolean estado) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
        this.estado = true;
    }

    public Cliente(String nombre, String primerApellido, String segundoApellido, String cedula, String telefono, String email, boolean estado) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
        this.estado = true;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
    
    @Override
    public String toString(){
        return "ID: " + idCliente +
                "Nombre: " + nombre +
                "Primer apellido: " + primerApellido +
                "Segundo apellido: " + segundoApellido +
                "Cedula: " + cedula +
                "Email: " + email +
                "Telefono: " + telefono +
                " Estado: " + (estado ? "Activo" : "Inactivo");
    }
    
}


