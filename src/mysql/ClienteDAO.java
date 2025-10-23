package mysql;

import java.sql.*;

import model.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WiseKingJeremy
 */
public class ClienteDAO {
    
    private ConexionBD cnx;
    
    public ClienteDAO(ConexionBD cnx) {
        cnx = new  ConexionBD();
    }
    
    //Registro para un nuevo cliente em la base de datos
    
    public int registrarCliente(Cliente c){
        String sql = "INSERT INT clientes(nombre, primer_apellido, segundo_apellido, cedi;a, email)";
        
        try(Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getPrimerApellido());
            ps.setString(3, c.getCedula());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            
            int filas = ps.executeUpdate();
            
            if(filas > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()){
                        int idGenerado = rs.getInt(1);
                        c.setIdCliente(idGenerado);
                        return idGenerado;
                    }
                }
            }
            return 0;
        }catch(SQLException e){
            System.err.println("Error al registrar cliente: " + e.getMessage());
            return 0;
        }
    }
    
    //Buscar un cliente por su ID
    
    public Cliente buscarPorID(int idCliente){
        String sql = "SELECT * FROM clientes WHERE id_cliente ?";

        try(Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primer_apellido"));
                cliente.setSegundoApellido(rs.getString("segundo_apellido"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                return cliente;
                 }
            }catch(SQLException e){
                System.err.println("Error al buscar cliente por ID: " + e.getMessage());
        }
        return null;
    }
    
    //Buscar un cliente por su correo
    
    public Cliente buscarPorEmail(String email){
        String sql = "SELECT * FROM clientes WHERE email = ?";
        
        try(Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primer_apellido"));
                cliente.setSegundoApellido(rs.getString("segundo_apellido"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                return cliente;
                 }
            }catch(SQLException e){
                System.err.println("Error al buscar cliente por su correo: " + e.getMessage());
        }
        return null;   
        }
    
    //Buscar un cliente por su cedula
    
    public Cliente buscarPorCedula(String cedula){
        String sql = "SELECT * FROM clientes WHERE cedula = ?";
        
        try(Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primer_apellido"));
                cliente.setSegundoApellido(rs.getString("segundo_apellido"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                return cliente;
                 }
            }catch(SQLException e){
                System.err.println("Error al buscar cliente por cedula: " + e.getMessage());
        }
        return null;   
        }
    
    public List<Cliente> listarClientes(){
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nombre, primer_apellido, segundo_apellido";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primer_apellido"));
                cliente.setSegundoApellido(rs.getString("segundo_apellido"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                lista.add(cliente);
                }
            }catch(SQLException e){
                System.err.println("Error al listar clientes: " + e.getMessage());
            }
        return lista;
        }
    
    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE email = ?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar email: " + e.getMessage());
        }
        
        return false;
        }
    
    public boolean cedulaExiste(String cedula) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE cedula = ?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar cédula: " + e.getMessage());
        }
        
        return false;
        }
    }

