package administrador;

import model.Ruta;
import java.util.ArrayList;
/**
 *
 * @author WiseKingJeremy
 */
public class ListaRuta {
    
    ArrayList<Ruta> lista = new ArrayList<>();
    
    public int agregarRuta(Ruta ruta){
        lista.add(ruta);
        return ruta.getIdRuta();
    }
    
    public boolean buscarRuta(int idRuta){
        boolean rutaEncontrada = false;
        
        for(Ruta ruta : lista){
            if(ruta.getIdRuta() == idRuta){
                rutaEncontrada = true;
            }
        }
        return rutaEncontrada;
    }
    
    public boolean eliminarRuta(int idRuta){
        boolean eliminado = false;
        
        for(Ruta ruta : lista){
            if(ruta.getIdRuta() == idRuta){
                ruta.setEstado(false);
                eliminado = true;
            }
        }   
        return eliminado;
    }
    
    public boolean modificarRuta(int idRuta, String nombre, String direccion, String distanciaKm, String duracionEstimada, 
                                 int precioBase, boolean estado) {
        for (Ruta ruta : lista) {
            if (ruta.getIdRuta() == idRuta) {
                ruta.setNombreRuta(nombre);
                ruta.setDireccion(direccion);
                ruta.setDistanciaKm(distanciaKm);
                ruta.setDuracionEstimada(duracionEstimada);
                ruta.setPrecioBase(precioBase);
                ruta.setEstado(estado);
                return true;
            }
        }
        return false;
    }
    
    public ArrayList listaRuta(){
        return lista;
    }
    
}
