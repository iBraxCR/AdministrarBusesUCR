package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Ruta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WiseKingJeremy
 */
public class RutaDAO {

     //Agrega una nueva ruta a la base de datos
     
    public void agregarRuta(String nombre, String direccion, String distanciaKm, 
                           String duracionEstimada, int precioBase, String estado) {
        String sql = "INSERT INTO rutas(nombre, direccion, distancia_km, duracion_estimada, precio_base, estado) " +
                     "VALUES(?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setString(3, distanciaKm);
            ps.setString(4, duracionEstimada);
            ps.setInt(5, precioBase);
            ps.setString(6, estado);
            ps.executeUpdate();
            
            System.out.println("Ruta agregada correctamente.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      //Lista todas las rutas de la base de datos
     
    public void listarRutas() {
        String sql = "SELECT * FROM rutas ORDER BY id_ruta";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id_ruta");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String distancia = rs.getString("distancia_km");
                String duracion = rs.getString("duracion_estimada");
                int precio = rs.getInt("precio_base");
                String estado = rs.getString("estado");
                
                System.out.println(id + " - " + nombre + " - " + direccion + " - " + 
                                 distancia + " - " + duracion + " - ₡" + precio + " - " + estado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
      //Actualiza una ruta existente
     
    public void actualizarRuta(int idRuta, String nombre, String direccion, String distanciaKm, 
                              String duracionEstimada, int precioBase, String estado) {
        String sql = "UPDATE rutas SET nombre=?, direccion=?, distancia_km=?, duracion_estimada=?, " +
                     "precio_base=?, estado=? WHERE id_ruta=?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setString(3, distanciaKm);
            ps.setString(4, duracionEstimada);
            ps.setInt(5, precioBase);
            ps.setString(6, estado);
            ps.setInt(7, idRuta);
            ps.executeUpdate();
            
            System.out.println("Ruta actualizada correctamente.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      //Elimina una ruta de la base de datos
     
    public void eliminarRuta(int idRuta) {
        String sql = "DELETE FROM rutas WHERE id_ruta=?";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idRuta);
            ps.executeUpdate();
            
            System.out.println("Ruta eliminada correctamente.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
      //Obtiene todas las rutas en una lista (para la GUI)
     
    public List<Ruta> obtenerRutas() {
        List<Ruta> rutas = new ArrayList<>();
        String sql = "SELECT * FROM rutas ORDER BY id_ruta";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Ruta ruta = new Ruta(
                    rs.getInt("id_ruta"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("distancia_km"),
                    rs.getString("duracion_estimada"),
                    rs.getInt("precio_base"),
                    rs.getString("estado")
                );
                rutas.add(ruta);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return rutas;
    }
    
     //Busca rutas por nombre
     
    public List<Ruta> buscarPorNombre(String nombre) {
        List<Ruta> rutas = new ArrayList<>();
        String sql = "SELECT * FROM rutas WHERE nombre LIKE ? ORDER BY nombre";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Ruta ruta = new Ruta(
                    rs.getInt("id_ruta"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("distancia_km"),
                    rs.getString("duracion_estimada"),
                    rs.getInt("precio_base"),
                    rs.getString("estado")
                );
                rutas.add(ruta);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return rutas;
    }

     //Obtiene solo las rutas activas
     
    public List<Ruta> obtenerRutasActivas() {
        List<Ruta> rutas = new ArrayList<>();
        String sql = "SELECT * FROM rutas WHERE estado='activo' ORDER BY nombre";
        
        try (Connection con = ConexionBD.Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Ruta ruta = new Ruta(
                    rs.getInt("id_ruta"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("distancia_km"),
                    rs.getString("duracion_estimada"),
                    rs.getInt("precio_base"),
                    rs.getString("estado")
                );
                rutas.add(ruta);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return rutas;
    }
}