package administrador;

import model.Ruta;
import java.util.ArrayList;
import java.util.List;
import mysql.RutaDAO;
import mysql.ConexionBD;
/**
 *
 * @author WiseKingJeremy
 */
public class ListaRuta {

    private RutaDAO dao;

    public ListaRuta() {
        ConexionBD conexion = new ConexionBD(); 
        dao = new RutaDAO(conexion); 
     
    }

    //Agrega una nueva ruta a la base de datos.
     
    public int agregarRuta(Ruta ruta) {
        return dao.agregarRuta(ruta);
    }

    //Modifica los datos de una ruta existente.
   
    public boolean modificarRuta(Ruta ruta) {
        return dao.modificarRuta(ruta);
    }

    //Elimina una ruta por su ID.

    public boolean eliminarRuta(int idRuta) {
        return dao.eliminarRuta(idRuta);
    }

    //Obtiene todas las rutas registradas.

    public List<Ruta> obtener() {
        return dao.listarRutas();
    }

    
    //Busca una ruta específica por su ID.
 
    public Ruta obtenerPorId(int id) {
        return dao.buscarRuta(id);
    }
}