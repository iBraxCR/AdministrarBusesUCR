package administrador;

import model.Chofer;
import java.util.ArrayList;
import java.util.List;
import mysql.ChoferDAO;
import mysql.ConexionBD;

/**
 *
 * @author WiseKingJeremy
 */
public class ListaChofer {
    
    private ChoferDAO dao;
    
    public ListaChofer() {
        ConexionBD conexion = new ConexionBD(); 
        dao = new ChoferDAO(conexion); 
    }
    
    //Agrega un nuevo chofer a la base de datos
    
    public int agregarChofer(Chofer chofer){
        return dao.agregarChofer(chofer);
    }
    
    //Modifica los datos de una ruta existente
    
    public boolean modificarChofer(Chofer chofer){
        return dao.modificarChofer(chofer);
    }
    
    //Elimina un chofer por su ID
    
    public boolean eliminiarChofer(int idChofer){
        return dao.eliminarChofer(idChofer);
    }
    
    //Obtiene todas los choferes registrados
    
    public List<Chofer> obtener(){
        return dao.listarChofer();
    }
    
    //Busca un chofer especifico por su ID
    
    public Chofer obtenerPorId(int id){
        return dao.buscarChofer(id);
    }
}
