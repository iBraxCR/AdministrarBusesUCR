/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Moose
 */
public class Chofer {
    
   private int idChofer;
   private String nombre;
   private String primerApellido;
   private String segundoNombre;
   private String cedula;
   private String telefono;
   private String correo;
   private String licencia;
   private Date fechaContratacion;
   private String estado;

   private static ArrayList<Chofer> listaChoferes= new ArrayList<>();
   
    public Chofer(int idChofer, String nombre, String primerApellido, String segundoNombre, String cedula, String telefono, String correo, String licencia, Date fechaContratacion, String estado) {
        this.idChofer = idChofer;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoNombre = segundoNombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.correo = correo;
        this.licencia = licencia;
        this.fechaContratacion = fechaContratacion;
        this.estado = estado;
    }
   
    
    public static void agregar(Chofer chofer){
        listaChoferes.add(chofer);
    }
    
    public static void modificar(int idChofer, Chofer modificarChofer) {
    for (int i = 0; i < listaChoferes.size(); i++) {
        if (listaChoferes.get(i).getIdChofer() == idChofer) {
            listaChoferes.set(i, modificarChofer);
            break;
        }
    }
}
    public static void eliminar(int idChofer) {
    for (int i = 0; i < listaChoferes.size(); i++) {
        if (listaChoferes.get(i).getIdChofer() == idChofer) {
            listaChoferes.remove(i);
            break;   
        }
    }
}
     public static Chofer buscar(int idChofer) {
        for (Chofer chofer : listaChoferes) {
            if (chofer.getIdChofer() == idChofer) {
                return chofer;
            }
        }
        return null;
    }
     
     public static void listar() {
        for (Chofer chofer : listaChoferes) {
            System.out.println(chofer);
        }
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

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static ArrayList<Chofer> getListaChoferes() {
        return listaChoferes;
    }

    public static void setListaChoferes(ArrayList<Chofer> listaChoferes) {
        Chofer.listaChoferes = listaChoferes;
    }
    
    @Override
public String toString() {
    return "Chofer:\n" +
           "  ID: " + idChofer + "\n" +
           "  Nombre: " + nombre + " " + primerApellido + " " + segundoNombre + "\n" +
           "  Cédula: " + cedula + "\n" +
           "  Teléfono: " + telefono + "\n" +
           "  Correo: " + correo + "\n" +
           "  Licencia: " + licencia + "\n" +
           "  Fecha de Contratación: " + fechaContratacion + "\n" +
           "  Estado: " + estado;
}

    

   
}
