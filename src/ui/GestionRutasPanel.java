package ui;

import javax.swing.table.DefaultTableModel;
import model.Ruta;
import administrador.ListaRuta;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import mysql.ConexionBD;
import mysql.RutaDAO;


/**
 *
 * @author josch
 */
public class GestionRutasPanel extends javax.swing.JPanel {
    
    private DefaultTableModel modeloRuta;
    private ListaRuta listaRuta;
    private int idRutaSeleccionada = -1;
    
    public GestionRutasPanel() {
        initComponents();
        cargarRutas();


        jTextFieldID.setEditable(false);
        jTextFieldID.setEnabled(false);

        setPreferredSize(new java.awt.Dimension(1100, 500));
    }

    public void actualizarDatos() {
        cargarDatos();
        limpiarCampos();
    }
    
    
    public void cargarRutas(){
        String[] columnas = {"ID", "Nombre de la Ruta", "Direccion", "Distancia Km", "Duracion Estimada", "Precio Base", "Estado"};
        
         modeloRuta = new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int r, int c){
                return false;
            }
        };
       jTableRuta.setModel(modeloRuta);
       
       jTableRuta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });
    }
    
    private void cargarDatosSeleccionados() {
    int filaSeleccionada = jTableRuta.getSelectedRow();
    if (filaSeleccionada >= 0) {
        try {
            idRutaSeleccionada = (int) modeloRuta.getValueAt(filaSeleccionada, 0);
            
            RutaDAO rutaDAO = new RutaDAO(new ConexionBD());
            Ruta ruta = rutaDAO.buscarRuta(idRutaSeleccionada);
            
            if (ruta != null) {
                jTextFieldID.setText(String.valueOf(ruta.getIdRuta()));
                jTextFieldNombreRuta.setText(ruta.getNombreRuta());
                jTextFieldDireccion.setText(ruta.getDireccion());
                jTextFieldDistancia.setText(ruta.getDistanciaKm());
                jTextFieldDuracion.setText(ruta.getDuracionEstimada());
                jTextFieldPrecio.setText(String.valueOf(ruta.getPrecioBase()));
                jCheckBoxEstado.setSelected(ruta.isEstado());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
}

    public void cargarDatos(){
        modeloRuta.setRowCount(0);
        
            try { 
        RutaDAO rutaDAO = new RutaDAO(new ConexionBD());

        List<Ruta> rutasBD = rutaDAO.listarRutas();
        
        for (Ruta r : rutasBD) {
            Object[] registro = {
                r.getIdRuta(),
                r.getNombreRuta(),
                r.getDireccion(),
                r.getDistanciaKm(),
                r.getDuracionEstimada(),
                r.getPrecioBase(),
                r.isEstado() ? "Activo" : "Inactivo"
            };
            modeloRuta.addRow(registro);
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar rutas: " + e.getMessage());
    }
}
    
    private void agregarRuta() {
    
    if (jTextFieldNombreRuta.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese el nombre de la ruta");
        return;
    }
    if (jTextFieldDireccion.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese la dirección");
        return;
    }
    if (jTextFieldDistancia.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese la distancia");
        return;
    }
    if (jTextFieldDuracion.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese la duración");
        return;
    }
    if (jTextFieldPrecio.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese el precio");
        return;
    }
    
    try {
        String nombre = jTextFieldNombreRuta.getText().trim();
        String direccion = jTextFieldDireccion.getText().trim();
        String distancia = jTextFieldDistancia.getText().trim();
        String duracion = jTextFieldDuracion.getText().trim();
        int precio = Integer.parseInt(jTextFieldPrecio.getText().trim());
        boolean estado = jCheckBoxEstado.isSelected(); 
        
        Ruta nuevaRuta = new Ruta();
        nuevaRuta.setNombreRuta(nombre);
        nuevaRuta.setDireccion(direccion);
        nuevaRuta.setDistanciaKm(distancia);
        nuevaRuta.setDuracionEstimada(duracion);
        nuevaRuta.setPrecioBase(precio);
        nuevaRuta.setEstado(estado);
        
        RutaDAO rutaDAO = new RutaDAO(new ConexionBD());
        rutaDAO.agregarRuta(nuevaRuta);
        
        JOptionPane.showMessageDialog(this, "Ruta agregada exitosamente");
        cargarDatos();
        limpiarCampos();
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", 
            "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void modificarRuta() {
    // Verificar que haya una ruta seleccionada
    if (idRutaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una ruta de la tabla para modificar");
        return;
    }
    
    // Validaciones
    if (jTextFieldNombreRuta.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese el nombre de la ruta");
        return;
    }
    if (jTextFieldDireccion.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese la dirección");
        return;
    }
    if (jTextFieldDistancia.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese la distancia");
        return;
    }
    if (jTextFieldDuracion.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese la duración");
        return;
    }
    if (jTextFieldPrecio.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese el precio");
        return;
    }
    
    try {
        // Usar idRutaSeleccionada en lugar de leer del campo
        String nombre = jTextFieldNombreRuta.getText().trim();
        String direccion = jTextFieldDireccion.getText().trim();
        String distancia = jTextFieldDistancia.getText().trim();
        String duracion = jTextFieldDuracion.getText().trim();
        int precio = Integer.parseInt(jTextFieldPrecio.getText().trim());
        boolean estado = jCheckBoxEstado.isSelected();
        
        // Crear ruta con el ID guardado
        Ruta rutaModificada = new Ruta();
        rutaModificada.setIdRuta(idRutaSeleccionada); 
        rutaModificada.setNombreRuta(nombre);
        rutaModificada.setDireccion(direccion);
        rutaModificada.setDistanciaKm(distancia);
        rutaModificada.setDuracionEstimada(duracion);
        rutaModificada.setPrecioBase(precio);
        rutaModificada.setEstado(estado);
        
        // Modificar en BD
        RutaDAO rutaDAO = new RutaDAO(new ConexionBD());
        boolean exito = rutaDAO.modificarRuta(rutaModificada);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, "Ruta modificada exitosamente");
            cargarDatos();
            limpiarCampos();
            idRutaSeleccionada = -1; // ← RESETEAR ID
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar la ruta", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", 
            "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void eliminarRuta() {
    if (idRutaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una ruta de la tabla para eliminar");
        return;
    }

    int confirmacion = JOptionPane.showConfirmDialog(this, 
        "¿Está seguro de eliminar esta ruta?", 
        "Confirmar eliminación", 
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
    
    if (confirmacion == JOptionPane.YES_OPTION) {
        try {
            RutaDAO rutaDAO = new RutaDAO(new ConexionBD());
            boolean exito = rutaDAO.eliminarRuta(idRutaSeleccionada);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Ruta eliminada exitosamente");
                cargarDatos();
                limpiarCampos();
                idRutaSeleccionada = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la ruta", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    
    private void limpiarCampos() {
    jTextFieldID.setText("");
    jTextFieldNombreRuta.setText("");
    jTextFieldDireccion.setText("");
    jTextFieldDistancia.setText("");
    jTextFieldDuracion.setText("");
    jTextFieldPrecio.setText("");
    jTableRuta.clearSelection();
    jCheckBoxEstado.setSelected(true); 
    jTableRuta.clearSelection();
    idRutaSeleccionada = -1;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelRuta = new javax.swing.JLabel();
        jLabelID = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jLabelNombreRuta = new javax.swing.JLabel();
        jTextFieldNombreRuta = new javax.swing.JTextField();
        jTextFieldDireccion = new javax.swing.JTextField();
        jLabelDireccion = new javax.swing.JLabel();
        jTextFieldDistancia = new javax.swing.JTextField();
        jLabelDistancia = new javax.swing.JLabel();
        jTextFieldDuracion = new javax.swing.JTextField();
        jLabelDuracion = new javax.swing.JLabel();
        jTextFieldPrecio = new javax.swing.JTextField();
        jLabelPrecio = new javax.swing.JLabel();
        jScrollPaneRuta = new javax.swing.JScrollPane();
        jTableRuta = new javax.swing.JTable();
        jPanelbarra1 = new javax.swing.JPanel();
        jPanelbarra2 = new javax.swing.JPanel();
        jPanelbarra3 = new javax.swing.JPanel();
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jCheckBoxEstado = new javax.swing.JCheckBox();

        jPanel1.setBackground(new java.awt.Color(212, 212, 214));

        jLabelRuta.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        jLabelRuta.setForeground(new java.awt.Color(0, 0, 0));
        jLabelRuta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRuta.setText("Rutas");
        jLabelRuta.setToolTipText("");

        jLabelID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelID.setForeground(new java.awt.Color(0, 0, 0));
        jLabelID.setText("ID Ruta");

        jTextFieldID.setBorder(null);

        jLabelNombreRuta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNombreRuta.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNombreRuta.setText("Nombre de la ruta");

        jLabelDireccion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelDireccion.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDireccion.setText("Direccion");

        jLabelDistancia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelDistancia.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDistancia.setText("Distancia");

        jLabelDuracion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelDuracion.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDuracion.setText("Duracion h");

        jTextFieldPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPrecioActionPerformed(evt);
            }
        });

        jLabelPrecio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelPrecio.setForeground(new java.awt.Color(0, 0, 0));
        jLabelPrecio.setText("Precio");

        jTableRuta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneRuta.setViewportView(jTableRuta);

        jPanelbarra1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelbarra1Layout = new javax.swing.GroupLayout(jPanelbarra1);
        jPanelbarra1.setLayout(jPanelbarra1Layout);
        jPanelbarra1Layout.setHorizontalGroup(
            jPanelbarra1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelbarra1Layout.setVerticalGroup(
            jPanelbarra1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        jPanelbarra2.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelbarra2Layout = new javax.swing.GroupLayout(jPanelbarra2);
        jPanelbarra2.setLayout(jPanelbarra2Layout);
        jPanelbarra2Layout.setHorizontalGroup(
            jPanelbarra2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelbarra2Layout.setVerticalGroup(
            jPanelbarra2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        jPanelbarra3.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelbarra3Layout = new javax.swing.GroupLayout(jPanelbarra3);
        jPanelbarra3.setLayout(jPanelbarra3Layout);
        jPanelbarra3Layout.setHorizontalGroup(
            jPanelbarra3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        jPanelbarra3Layout.setVerticalGroup(
            jPanelbarra3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 389, Short.MAX_VALUE)
        );

        jButtonAgregar.setBackground(new java.awt.Color(6, 130, 255));
        jButtonAgregar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonAgregar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAgregar.setText("Agregar");
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });

        jButtonModificar.setBackground(new java.awt.Color(0, 255, 16));
        jButtonModificar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonModificar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jButtonEliminar.setBackground(new java.awt.Color(255, 0, 25));
        jButtonEliminar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonEliminar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jCheckBoxEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCheckBoxEstado.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBoxEstado.setText("Activo");
        jCheckBoxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbarra2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelbarra1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPaneRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 968, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 668, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNombreRuta)
                            .addComponent(jLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNombreRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelDireccion)
                                    .addComponent(jTextFieldDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(65, 65, 65)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelDuracion)
                                    .addComponent(jLabelPrecio)
                                    .addComponent(jTextFieldPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelDistancia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCheckBoxEstado)
                                .addGap(30, 30, 30)))))
                .addComponent(jPanelbarra3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(135, 135, 135))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelID, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDireccion)
                            .addComponent(jLabelDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelNombreRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelPrecio))
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBoxEstado)
                                .addGap(56, 56, 56)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldDistancia, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(jTextFieldNombreRuta)
                            .addComponent(jTextFieldPrecio, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(67, 67, 67))
                    .addComponent(jPanelbarra3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)))
                .addComponent(jPanelbarra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPaneRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanelbarra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPrecioActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        // TODO add your handling code here:
        agregarRuta();
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jCheckBoxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxEstadoActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        // TODO add your handling code here:
        modificarRuta();
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        // TODO add your handling code here:
        eliminarRuta();
    }//GEN-LAST:event_jButtonEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JCheckBox jCheckBoxEstado;
    private javax.swing.JLabel jLabelDireccion;
    private javax.swing.JLabel jLabelDistancia;
    private javax.swing.JLabel jLabelDuracion;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelNombreRuta;
    private javax.swing.JLabel jLabelPrecio;
    private javax.swing.JLabel jLabelRuta;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelbarra1;
    private javax.swing.JPanel jPanelbarra2;
    private javax.swing.JPanel jPanelbarra3;
    private javax.swing.JScrollPane jScrollPaneRuta;
    private javax.swing.JTable jTableRuta;
    private javax.swing.JTextField jTextFieldDireccion;
    private javax.swing.JTextField jTextFieldDistancia;
    private javax.swing.JTextField jTextFieldDuracion;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldNombreRuta;
    private javax.swing.JTextField jTextFieldPrecio;
    // End of variables declaration//GEN-END:variables
}

