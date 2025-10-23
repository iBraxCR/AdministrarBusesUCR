package administrador;

import model.Cliente;
import java.util.List;
import mysql.ClienteDAO;
import mysql.ConexionBD;

/**
 *
 * @author WiseKingJeremy
 */
public class ListaCliente {
    
    private ClienteDAO dao;
    
    public ListaCliente(){
        ConexionBD conexion = new ConexionBD();
        dao = new ClienteDAO(conexion);
    }
    
    //Registra a un nuevo cliente en la base de datos
    
    public int registrarCliente(Cliente cliente){
        return dao.registrarCliente(cliente);
    }
    
    //Obtener todos los clientes registrados
    
    public List<Cliente> obtenerClientes(){
        return dao.listarClientes();
    }
    
    //Buscar cliente por su ID
    
    public Cliente buscarPorID(int id){
        return dao.buscarPorID(id);
    }
    
    //Bucar cliente por cedula
    
    public Cliente buscarPorCedula(String cedula){
        return dao.buscarPorEmail(cedula);
    }
    
    //Buscar cliente por email
    
    public Cliente buscarPorEmail(String email){
        return dao.buscarPorEmail(email);
    }
    
    //Verificaciones de email y cedula
    
    public boolean existeEmal(String email){
        return dao.emailExiste(email);
    }
    
    public boolean existeCedula(String cedula){
        return dao.cedulaExiste(cedula);
    }
    
}
