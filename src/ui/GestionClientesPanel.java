package ui;

import administrador.ListaCliente;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cliente;

/**
 *
 * @author WiseKingJeremy
 */
public class GestionClientesPanel extends javax.swing.JPanel {
    
    private DefaultTableModel modeloCliente;
    private ListaCliente listaCliente;
    private int idClienteSeleccionado = -1;



    public GestionClientesPanel() {
        initComponents();
    }
    
    public void cargarClientes(){
        String[] columnas = {"ID","Nombre","Primer apellido","Segundo apellido","Cedula","Telefono","Correo", "Estado"};
        
        modeloCliente = new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        jTableClientes.setModel(modeloCliente);
        
        jTableClientes.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()){
                
            }
        });
    }
    
    private void cargarDatos() {
        try {
            modeloCliente.setRowCount(0);
            
            List<Cliente> clientes = listaCliente.obtenerClientes();
            
            for (Cliente c : clientes) {
                Object[] fila = {
                    c.getIdCliente(),
                    c.getNombre(),
                    c.getPrimerApellido(),
                    c.getSegundoApellido(),
                    c.getCedula(),
                    c.getTelefono(),
                    c.getEmail()
                };
                modeloCliente.addRow(fila);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar clientes: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDatosSeleccionados() {
        int filaSeleccionada = jTableClientes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            idClienteSeleccionado = (int) modeloCliente.getValueAt(filaSeleccionada, 0);
            System.out.println("Cliente seleccionado ID: " + idClienteSeleccionado);
        }
    }
    
    private void buscarPorID() {
        String idTexto = jTextFieldBuscarID.getText().trim();
        
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID para buscar");
            return;
        }
        
        try {
            int id = Integer.parseInt(idTexto);
            Cliente cliente = listaCliente.buscarPorID(id);
            
            if (cliente != null) {
                modeloCliente.setRowCount(0);
                Object[] fila = {
                    cliente.getIdCliente(),
                    cliente.getNombre(),
                    cliente.getPrimerApellido(),
                    cliente.getSegundoApellido(),
                    cliente.getCedula(),
                    cliente.getTelefono(),
                    cliente.getEmail()
                };
                modeloCliente.addRow(fila);
                
                JOptionPane.showMessageDialog(this, "Cliente encontrado");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró cliente con ID: " + id, 
                    "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos(); 
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El ID debe ser un número válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarPorCedula() {
        String cedula = jTextFieldBuscarCedula.getText().trim();
        
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula para buscar");
            return;
        }
        
        try {
            Cliente cliente = listaCliente.buscarPorCedula(cedula);
            
            if (cliente != null) {
                // Limpiar tabla y mostrar solo ese cliente
                modeloCliente.setRowCount(0);
                Object[] fila = {
                    cliente.getIdCliente(),
                    cliente.getNombre(),
                    cliente.getPrimerApellido(),
                    cliente.getSegundoApellido(),
                    cliente.getCedula(),
                    cliente.getTelefono(),
                    cliente.getEmail()
                };
                modeloCliente.addRow(fila);
                
                JOptionPane.showMessageDialog(this, "Cliente encontrado");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró cliente con cédula: " + cedula, 
                    "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos(); // Recargar todos
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al buscar: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarPorCorreo() {
        String email = jTextFieldBuscarCorreo.getText().trim();
        
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo para buscar");
            return;
        }
        
        try {
            Cliente cliente = listaCliente.buscarPorEmail(email);
            
            if (cliente != null) {
                // Limpiar tabla y mostrar solo ese cliente
                modeloCliente.setRowCount(0);
                Object[] fila = {
                    cliente.getIdCliente(),
                    cliente.getNombre(),
                    cliente.getPrimerApellido(),
                    cliente.getSegundoApellido(),
                    cliente.getCedula(),
                    cliente.getTelefono(),
                    cliente.getEmail()
                };
                modeloCliente.addRow(fila);
                
                JOptionPane.showMessageDialog(this, "Cliente encontrado");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró cliente con correo: " + email, 
                    "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos(); // Recargar todos
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al buscar: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCamposBusqueda() {
        jTextFieldBuscarID.setText("");
        jTextFieldBuscarCedula.setText("");
        jTextFieldBuscarCorreo.setText("");
        idClienteSeleccionado = -1;
        jTableClientes.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jLabelClientes = new javax.swing.JLabel();
        jLabelBuscarID = new javax.swing.JLabel();
        jTextFieldBuscarID = new javax.swing.JTextField();
        jButtonBusacarID = new javax.swing.JButton();
        jLabelBuscarCedula = new javax.swing.JLabel();
        jTextFieldBuscarCedula = new javax.swing.JTextField();
        jButtonBusacarCedula = new javax.swing.JButton();
        jLabelBuscarCorreo = new javax.swing.JLabel();
        jTextFieldBuscarCorreo = new javax.swing.JTextField();
        jButtonBusacarCorreo = new javax.swing.JButton();

        jPanelPrincipal.setBackground(new java.awt.Color(225, 225, 225));

        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableClientes);

        jLabelClientes.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabelClientes.setForeground(new java.awt.Color(0, 0, 0));
        jLabelClientes.setText("       Clientes");

        jLabelBuscarID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelBuscarID.setForeground(new java.awt.Color(0, 0, 0));
        jLabelBuscarID.setText("Buscar por ID: ");

        jButtonBusacarID.setBackground(new java.awt.Color(78, 153, 255));
        jButtonBusacarID.setForeground(new java.awt.Color(0, 0, 0));
        jButtonBusacarID.setText("Buscar");

        jLabelBuscarCedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelBuscarCedula.setForeground(new java.awt.Color(0, 0, 0));
        jLabelBuscarCedula.setText("Buscar por cedula:");

        jTextFieldBuscarCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBuscarCedulaActionPerformed(evt);
            }
        });

        jButtonBusacarCedula.setBackground(new java.awt.Color(78, 153, 255));
        jButtonBusacarCedula.setForeground(new java.awt.Color(0, 0, 0));
        jButtonBusacarCedula.setText("Buscar");

        jLabelBuscarCorreo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelBuscarCorreo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelBuscarCorreo.setText("Buscar por correo:");

        jButtonBusacarCorreo.setBackground(new java.awt.Color(78, 153, 255));
        jButtonBusacarCorreo.setForeground(new java.awt.Color(0, 0, 0));
        jButtonBusacarCorreo.setText("Buscar");

        javax.swing.GroupLayout jPanelPrincipalLayout = new javax.swing.GroupLayout(jPanelPrincipal);
        jPanelPrincipal.setLayout(jPanelPrincipalLayout);
        jPanelPrincipalLayout.setHorizontalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                .addComponent(jLabelClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 971, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelBuscarID)
                            .addComponent(jButtonBusacarID)
                            .addComponent(jTextFieldBuscarID, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(129, 129, 129)
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelBuscarCedula)
                            .addComponent(jButtonBusacarCedula)
                            .addComponent(jTextFieldBuscarCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(125, 125, 125)
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonBusacarCorreo)
                            .addComponent(jLabelBuscarCorreo)
                            .addComponent(jTextFieldBuscarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(340, Short.MAX_VALUE))
        );
        jPanelPrincipalLayout.setVerticalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrincipalLayout.createSequentialGroup()
                .addComponent(jLabelClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBuscarID)
                    .addComponent(jLabelBuscarCedula)
                    .addComponent(jLabelBuscarCorreo))
                .addGap(18, 18, 18)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldBuscarID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBuscarCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBuscarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBusacarID)
                    .addComponent(jButtonBusacarCedula)
                    .addComponent(jButtonBusacarCorreo))
                .addGap(79, 79, 79)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldBuscarCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBuscarCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBuscarCedulaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBusacarCedula;
    private javax.swing.JButton jButtonBusacarCorreo;
    private javax.swing.JButton jButtonBusacarID;
    private javax.swing.JLabel jLabelBuscarCedula;
    private javax.swing.JLabel jLabelBuscarCorreo;
    private javax.swing.JLabel jLabelBuscarID;
    private javax.swing.JLabel jLabelClientes;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTextField jTextFieldBuscarCedula;
    private javax.swing.JTextField jTextFieldBuscarCorreo;
    private javax.swing.JTextField jTextFieldBuscarID;
    // End of variables declaration//GEN-END:variables
}
