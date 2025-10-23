package mysql;

import java.sql.*;

import model.Ruta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WiseKingJeremy
 */
public class RutaDAO {
    
    private ConexionBD cnx;

    public RutaDAO(ConexionBD cnx) {
        cnx = new  ConexionBD();
    }

     //Agrega una nueva ruta a la base de datos
     
    public int agregarRuta(Ruta r) {
        String sql = "INSERT INTO rutas(nombre, direccion, distancia_km, duracion_estimada, precio_base, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, r.getNombreRuta());
            ps.setString(2, r.getDireccion());
            ps.setString(3, r.getDistanciaKm());
            ps.setString(4, r.getDuracionEstimada());
            ps.setInt(5, r.getPrecioBase());
            ps.setBoolean(6, r.isEstado());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        r.setIdRuta(idGenerado);
                        return idGenerado;
                }
            }
        }
        return -1;
} catch (SQLException e) {
    System.out.println("Error insertando ruta: " + e.getMessage());
    return 0;
        }   
    }
    
    public boolean modificarRuta(Ruta r){
        String sql= "UPDATE rutas SET nombre=?, direccion=?, distancia_km=?, duracion_estimada=?"
                + "precio_base=?, estado=? WHERE id_Ruta=?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, r.getNombreRuta());
            ps.setString(2, r.getDireccion());
            ps.setString(3, r.getDistanciaKm());
            ps.setString(4, r.getDuracionEstimada());
            ps.setInt(5, r.getPrecioBase());
            ps.setBoolean(6, r.isEstado());
            ps.setInt(7, r.getIdRuta());
            
            int filas = ps.executeUpdate();
            
            return filas > 0;
            
            } catch(SQLException e){
                System.out.println("Error al modificar una ruta " + e.getMessage());
                return false;
        }
    }
    
    public boolean eliminarRuta(int idRuta){
        String sql= "DELETE FROM rutas WHERE id_ruta=?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, idRuta);
            
            int filas= ps.executeUpdate();
            
            return filas > 0;
            
        }catch(SQLException e){
            System.out.println("Error al eliminar una ruta " + e.getMessage());
            return false;
        }
    }
    
    public Ruta buscarRuta(int idRuta){
        String sql= "SELECT * FROM rutas WHERE id_ruta= ?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, idRuta);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Ruta r= new Ruta();
                    
                    r.setIdRuta(rs.getInt("id_ruta"));
                    r.setNombreRuta(rs.getString("nombre"));
                    r.setDireccion(rs.getString("direccion"));
                    r.setDistanciaKm(rs.getString("distancia_km"));
                    r.setDuracionEstimada(rs.getString("duracion_estimada"));
                    r.setPrecioBase(rs.getInt("precio_base"));
                    r.setEstado(rs.getBoolean("estado"));
                    return r;
                }
            }
        }catch(SQLException e){
            System.out.println("Error al buscar una ruta: " + e.getMessage());
        }  
            return null;
    }
    
    public List<Ruta> listarRutas(){
    List<Ruta> lista = new ArrayList<>();
    String sql = "SELECT * FROM rutas";
    
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ruta r = new Ruta();
                r.setIdRuta(rs.getInt("id_ruta"));
                r.setNombreRuta(rs.getString("nombre"));
                r.setDireccion(rs.getString("direccion"));
                r.setDistanciaKm(rs.getString("distancia_km"));
                r.setDuracionEstimada(rs.getString("duracion_estimada"));
                r.setPrecioBase(rs.getInt("precio_base"));
                r.setEstado(rs.getBoolean("estado"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error listando rutas: " + e.getMessage());
        }
        return lista;
    }
}