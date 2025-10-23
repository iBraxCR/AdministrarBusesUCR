package mysql;

import model.Buses;
import  mysql.ConexionBD.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {

    private Connection conn;

    public BusDAO() {
        conn = Conexion.conectar(); 
    }

    
    public boolean agregar(Buses bus) {
        String sql = "INSERT INTO buses (numeroPlaca, marca, capacidad, estadoMantenimiento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, bus.getNumeroPlaca());
            ps.setString(2, bus.getMarca());
            ps.setInt(3, bus.getCapacidad());
            ps.setBoolean(4, bus.getEstadoMantenimiento());

            int filas = ps.executeUpdate();

            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    bus.setIdBus(rs.getInt(1));
                }
            }

            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean modificar(Buses bus) {
        String sql = "UPDATE buses SET numeroPlaca=?, marca=?, capacidad=?, estadoMantenimiento=? WHERE idBus=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bus.getNumeroPlaca());
            ps.setString(2, bus.getMarca());
            ps.setInt(3, bus.getCapacidad());
            ps.setBoolean(4, bus.getEstadoMantenimiento());
            ps.setInt(5, bus.getIdBus());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean eliminar(int idBus) {
        String sql = "DELETE FROM buses WHERE idBus=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBus);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public Buses buscar(int idBus) {
        String sql = "SELECT * FROM buses WHERE idBus=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBus);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Buses(
                    rs.getInt("idBus"),
                    rs.getString("numeroPlaca"),
                    rs.getString("marca"),
                    rs.getInt("capacidad"),
                    rs.getBoolean("estadoMantenimiento")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public List<Buses> listar() {
        List<Buses> lista = new ArrayList<>();
        String sql = "SELECT * FROM buses";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Buses bus = new Buses(
                    rs.getInt("idBus"),
                    rs.getString("numeroPlaca"),
                    rs.getString("marca"),
                    rs.getInt("capacidad"),
                    rs.getBoolean("estadoMantenimiento")
                );
                lista.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
