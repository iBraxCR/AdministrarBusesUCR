package mysql;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import model.Chofer;
import java.sql.Date;

/**
 *
 * @author WiseKingJeremy
 */
public class ChoferDAO {
    
    private ConexionBD cnx;

    public ChoferDAO(ConexionBD cnx) {
        cnx = new  ConexionBD();
    }
    
    public int agregarChofer(Chofer c){
        String sql = "INSERT INTO choferes(nombre, primer_apellido, segundo_apellido, "
                + "cedula, telefono, correo, licencia, fecha_contratacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getPrimerApellido());
            ps.setString(3, c.getSegundoApellido());
            ps.setString(4, c.getCedula());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getCorreo());
            ps.setString(7, c.getLicencia());
            ps.setDate(8, c.getFechaContratacion());
            ps.setBoolean(9, c.isEstado());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        c.setIdChofer(idGenerado);
                        return idGenerado;
                    }
                }
            }
            return -1;
        }catch(SQLException e){
    System.out.println("Error insertando chofer: " + e.getMessage());
    return 0;
        }
    }
    
    public boolean modificarChofer(Chofer c){
        String sql= "UPDATE choferes SET nombre=?, primer_apellido=?, segundo_apellido=?, "
                + "cedula=?, telefono=?, correo=?, licencia=?, fecha_contratacion=?, estado=? WHERE id_chofer=?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getPrimerApellido());
            ps.setString(3, c.getSegundoApellido());
            ps.setString(4, c.getCedula());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getCorreo());
            ps.setString(7, c.getLicencia());
            ps.setDate(8, c.getFechaContratacion());
            ps.setBoolean(9, c.isEstado());
            ps.setInt(10, c.getIdChofer());

           int filas = ps.executeUpdate();

           return filas > 0;
        } catch(SQLException e){
            System.out.println("Error al modificar un chofer: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminarChofer(int idChofer){
        String sql= "DELETE FROM choferes WHERE id_chofer=?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, idChofer);
            
            int filas= ps.executeUpdate();
            
            return filas > 0;
        }catch(SQLException e){
            System.out.println("Error al eliminar una ruta " + e.getMessage());
            return false;
        }
    }
    
    public Chofer buscarChofer(int idChofer){
        String sql= "SELECT * FROM choferes WHERE id_chofer= ?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, idChofer);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Chofer c = new Chofer();

                    c.setIdChofer(rs.getInt("id_chofer"));
                    c.setNombre(rs.getString("nombre"));
                    c.setPrimerApellido(rs.getString("primer_apellido"));
                    c.setSegundoApellido(rs.getString("segundo_apellido"));
                    c.setCedula(rs.getString("cedula"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setCorreo(rs.getString("correo"));
                    c.setLicencia(rs.getString("licencia"));
                    c.setFechaContratacion(rs.getDate("fecha_contratacion"));
                    c.setEstado(rs.getBoolean("estado"));
                    return c;
           }      
        }
    }catch(SQLException e){
       System.out.println("Error al buscar un chofer: " + e.getMessage());
    }
        return null;
    }
    
    public List<Chofer> listarChofer(){
    List<Chofer> lista = new ArrayList<>();
    String sql = "SELECT * FROM choferes";
    
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Chofer c = new Chofer();

                    c.setIdChofer(rs.getInt("id_chofer"));
                    c.setNombre(rs.getString("nombre"));
                    c.setPrimerApellido(rs.getString("primer_apellido"));
                    c.setSegundoApellido(rs.getString("segundo_apellido"));
                    c.setCedula(rs.getString("cedula"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setCorreo(rs.getString("correo"));
                    c.setLicencia(rs.getString("licencia"));
                    c.setFechaContratacion(rs.getDate("fecha_contratacion"));
                    c.setEstado(rs.getBoolean("estado"));
                    lista.add(c);
            }
        }catch (SQLException e) {
           System.out.println("Error listando choferes: " + e.getMessage());
        }
        return lista;
    }
}
