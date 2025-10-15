
package model;

public class Buses {
 
    
private int  idBus;
private String numeroPlaca;
private String marca;
private int capacidad;
private boolean estadoMantenimiento;

    public Buses(int idBus, String numeroPlaca, String marca, int capacidad, boolean estadoMantenimiento) {
        this.idBus = idBus;
        this.numeroPlaca = numeroPlaca;
        this.marca = marca;
        this.capacidad = capacidad;
        this.estadoMantenimiento = estadoMantenimiento;
    }





    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Boolean getEstadoMantenimiento() {
        return estadoMantenimiento;
    }

    public void setEstadoMantenimiento(Boolean estadoMantenimiento) {
        this.estadoMantenimiento = estadoMantenimiento;
    }

    @Override
    public String toString() {
        return "Buses{" + "idBus=" + idBus + ", numeroPlaca=" + numeroPlaca + ", marca=" + marca + ", capacidad=" + capacidad + ", estadoMantenimiento=" + estadoMantenimiento + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
