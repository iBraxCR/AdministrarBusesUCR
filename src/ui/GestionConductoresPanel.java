/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import javax.swing.table.DefaultTableModel;
import model.Chofer;
import java.util.List;
import javax.swing.JOptionPane;
import mysql.ConexionBD;
import mysql.ChoferDAO;
import java.sql.Date;
import com.toedter.calendar.JDateChooser;

/**
 *
 * @author josch
 */
public class GestionConductoresPanel extends javax.swing.JPanel {

    private DefaultTableModel modeloConductor;
    private int idChoferSeleccionado = -1;

    /**
     * Creates new form GestionConductoresPanel
     */
    public GestionConductoresPanel() {
        initComponents();
        cargarConductores();

        jTextFieldID.setEditable(false);
        jTextFieldID.setEnabled(false);

        setPreferredSize(new java.awt.Dimension(1100, 500));
    }

    public void actualizarDatos() {
        cargarDatos();
        limpiarCampos();
    }

    public void cargarConductores(){
        String[] columnas = {"ID", "Nombre", "Primer Apellido", "Segundo Apellido", "Cedula", "Telefono", "Correo", "Licencia", "Fecha Contratacion", "Estado"};

        modeloConductor = new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        tablaConductores.setModel(modeloConductor);

        tablaConductores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });
    }

    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tablaConductores.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                idChoferSeleccionado = (int) modeloConductor.getValueAt(filaSeleccionada, 0);

                ChoferDAO choferDAO = new ChoferDAO(new ConexionBD());
                Chofer chofer = choferDAO.buscarChofer(idChoferSeleccionado);

                if (chofer != null) {
                    jTextFieldID.setText(String.valueOf(chofer.getIdChofer()));
                    txtNombreConductor.setText(chofer.getNombre());
                    txtPrimerApellidoConductor.setText(chofer.getPrimerApellido());
                    txtSegundoApellidoConductor.setText(chofer.getSegundoApellido());
                    txtCedulaConductor.setText(chofer.getCedula());
                    txtTelefonoConductor.setText(chofer.getTelefono());
                    txtCorreoConductor.setText(chofer.getCorreo());
                    txtLicenciaConductor.setText(chofer.getLicencia());
                    FechaContratacion.setDate(chofer.getFechaContratacion());
                    jCheckBoxEstado.setSelected(chofer.isEstado());
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
            }
        }
    }

    public void cargarDatos(){
        modeloConductor.setRowCount(0);

        try {
            ChoferDAO choferDAO = new ChoferDAO(new ConexionBD());
            List<Chofer> choferesBD = choferDAO.listarChofer();

            for (Chofer c : choferesBD) {
                Object[] registro = {
                    c.getIdChofer(),
                    c.getNombre(),
                    c.getPrimerApellido(),
                    c.getSegundoApellido(),
                    c.getCedula(),
                    c.getTelefono(),
                    c.getCorreo(),
                    c.getLicencia(),
                    c.getFechaContratacion(),
                    c.isEstado() ? "Activo" : "Inactivo"
                };
                modeloConductor.addRow(registro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar conductores: " + e.getMessage());
        }
    }

    private void agregarChofer() {
        if (txtNombreConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del conductor");
            return;
        }
        if (txtPrimerApellidoConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el primer apellido");
            return;
        }
        if (txtSegundoApellidoConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el segundo apellido");
            return;
        }
        if (txtCedulaConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cedula");
            return;
        }
        if (txtTelefonoConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el telefono");
            return;
        }
        if (txtCorreoConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el correo");
            return;
        }
        if (txtLicenciaConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la licencia");
            return;
        }
        if (FechaContratacion.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione la fecha de contratacion");
            return;
        }

        try {
            String nombre = txtNombreConductor.getText().trim();
            String primerApellido = txtPrimerApellidoConductor.getText().trim();
            String segundoApellido = txtSegundoApellidoConductor.getText().trim();
            String cedula = txtCedulaConductor.getText().trim();
            String telefono = txtTelefonoConductor.getText().trim();
            String correo = txtCorreoConductor.getText().trim();
            String licencia = txtLicenciaConductor.getText().trim();
            Date fechaContratacion = new Date(FechaContratacion.getDate().getTime());
            boolean estado = jCheckBoxEstado.isSelected();

            Chofer nuevoChofer = new Chofer();
            nuevoChofer.setNombre(nombre);
            nuevoChofer.setPrimerApellido(primerApellido);
            nuevoChofer.setSegundoApellido(segundoApellido);
            nuevoChofer.setCedula(cedula);
            nuevoChofer.setTelefono(telefono);
            nuevoChofer.setCorreo(correo);
            nuevoChofer.setLicencia(licencia);
            nuevoChofer.setFechaContratacion(fechaContratacion);
            nuevoChofer.setEstado(estado);

            ChoferDAO choferDAO = new ChoferDAO(new ConexionBD());
            int resultado = choferDAO.agregarChofer(nuevoChofer);

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Conductor agregado exitosamente");
                cargarDatos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar conductor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarChofer() {
        if (idChoferSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un conductor de la tabla para modificar");
            return;
        }

        if (txtNombreConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del conductor");
            return;
        }
        if (txtPrimerApellidoConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el primer apellido");
            return;
        }
        if (txtSegundoApellidoConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el segundo apellido");
            return;
        }
        if (txtCedulaConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cedula");
            return;
        }
        if (txtTelefonoConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el telefono");
            return;
        }
        if (txtCorreoConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el correo");
            return;
        }
        if (txtLicenciaConductor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la licencia");
            return;
        }
        if (FechaContratacion.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione la fecha de contratacion");
            return;
        }

        try {
            String nombre = txtNombreConductor.getText().trim();
            String primerApellido = txtPrimerApellidoConductor.getText().trim();
            String segundoApellido = txtSegundoApellidoConductor.getText().trim();
            String cedula = txtCedulaConductor.getText().trim();
            String telefono = txtTelefonoConductor.getText().trim();
            String correo = txtCorreoConductor.getText().trim();
            String licencia = txtLicenciaConductor.getText().trim();
            Date fechaContratacion = new Date(FechaContratacion.getDate().getTime());
            boolean estado = jCheckBoxEstado.isSelected();

            Chofer choferModificado = new Chofer();
            choferModificado.setIdChofer(idChoferSeleccionado);
            choferModificado.setNombre(nombre);
            choferModificado.setPrimerApellido(primerApellido);
            choferModificado.setSegundoApellido(segundoApellido);
            choferModificado.setCedula(cedula);
            choferModificado.setTelefono(telefono);
            choferModificado.setCorreo(correo);
            choferModificado.setLicencia(licencia);
            choferModificado.setFechaContratacion(fechaContratacion);
            choferModificado.setEstado(estado);

            ChoferDAO choferDAO = new ChoferDAO(new ConexionBD());
            boolean exito = choferDAO.modificarChofer(choferModificado);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Conductor modificado exitosamente");
                cargarDatos();
                limpiarCampos();
                idChoferSeleccionado = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar el conductor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarChofer() {
        if (idChoferSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un conductor de la tabla para eliminar");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Esta seguro de eliminar este conductor?",
            "Confirmar eliminacion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                ChoferDAO choferDAO = new ChoferDAO(new ConexionBD());
                boolean exito = choferDAO.eliminarChofer(idChoferSeleccionado);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Conductor eliminado exitosamente");
                    cargarDatos();
                    limpiarCampos();
                    idChoferSeleccionado = -1;
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el conductor",
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
        txtNombreConductor.setText("");
        txtPrimerApellidoConductor.setText("");
        txtSegundoApellidoConductor.setText("");
        txtCedulaConductor.setText("");
        txtTelefonoConductor.setText("");
        txtCorreoConductor.setText("");
        txtLicenciaConductor.setText("");
        FechaContratacion.setDate(null);
        jCheckBoxEstado.setSelected(true);
        tablaConductores.clearSelection();
        idChoferSeleccionado = -1;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel2 = new javax.swing.JPanel();
        lblTelefonoConductor = new javax.swing.JLabel();
        btnModificarConductor = new javax.swing.JButton();
        txtSegundoApellidoConductor = new javax.swing.JTextField();
        btnEliminarConductor = new javax.swing.JButton();
        lblPrimerApellidoConductor = new javax.swing.JLabel();
        jCheckBoxEstado = new javax.swing.JCheckBox();
        txtTelefonoConductor = new javax.swing.JTextField();
        lblCorreoConductor = new javax.swing.JLabel();
        jScrollPaneRuta = new javax.swing.JScrollPane();
        tablaConductores = new javax.swing.JTable();
        jPanelbarra3 = new javax.swing.JPanel();
        jPanelbarra4 = new javax.swing.JPanel();
        jLabelRuta = new javax.swing.JLabel();
        lblIdConductor = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        lblCedulaConductor = new javax.swing.JLabel();
        txtPrimerApellidoConductor = new javax.swing.JTextField();
        txtNombreConductor = new javax.swing.JTextField();
        lblNombreConductor = new javax.swing.JLabel();
        jPanelbarra5 = new javax.swing.JPanel();
        txtCedulaConductor = new javax.swing.JTextField();
        btnAgregarConductor = new javax.swing.JButton();
        lblSegundoApellidoConductor = new javax.swing.JLabel();
        txtCorreoConductor = new javax.swing.JTextField();
        lblLicenciaConductor = new javax.swing.JLabel();
        lblFechaContratacionConductor = new javax.swing.JLabel();
        FechaContratacion = new com.toedter.calendar.JDateChooser();
        txtLicenciaConductor = new javax.swing.JTextField();

        lblTelefonoConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTelefonoConductor.setText("Telefono");

        btnModificarConductor.setBackground(new java.awt.Color(0, 255, 16));
        btnModificarConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnModificarConductor.setText("Modificar");
        btnModificarConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarConductorActionPerformed(evt);
            }
        });

        txtSegundoApellidoConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSegundoApellidoConductorActionPerformed(evt);
            }
        });

        btnEliminarConductor.setBackground(new java.awt.Color(255, 0, 25));
        btnEliminarConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEliminarConductor.setText("Eliminar");
        btnEliminarConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarConductorActionPerformed(evt);
            }
        });

        lblPrimerApellidoConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPrimerApellidoConductor.setText("Primer Apellido");

        jCheckBoxEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCheckBoxEstado.setText("Activo");
        jCheckBoxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxEstadoActionPerformed(evt);
            }
        });

        txtTelefonoConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoConductorActionPerformed(evt);
            }
        });

        lblCorreoConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCorreoConductor.setText("Correo");

        tablaConductores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPaneRuta.setViewportView(tablaConductores);

        jPanelbarra3.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelbarra3Layout = new javax.swing.GroupLayout(jPanelbarra3);
        jPanelbarra3.setLayout(jPanelbarra3Layout);
        jPanelbarra3Layout.setHorizontalGroup(
            jPanelbarra3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelbarra3Layout.setVerticalGroup(
            jPanelbarra3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        jPanelbarra4.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelbarra4Layout = new javax.swing.GroupLayout(jPanelbarra4);
        jPanelbarra4.setLayout(jPanelbarra4Layout);
        jPanelbarra4Layout.setHorizontalGroup(
            jPanelbarra4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        jPanelbarra4Layout.setVerticalGroup(
            jPanelbarra4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        jLabelRuta.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        jLabelRuta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRuta.setText("Conductores");
        jLabelRuta.setToolTipText("");

        lblIdConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdConductor.setText("ID Chofer");

        jTextFieldID.setBorder(null);

        lblCedulaConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCedulaConductor.setText("Cedula");

        lblNombreConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNombreConductor.setText("Nombre");

        jPanelbarra5.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelbarra5Layout = new javax.swing.GroupLayout(jPanelbarra5);
        jPanelbarra5.setLayout(jPanelbarra5Layout);
        jPanelbarra5Layout.setHorizontalGroup(
            jPanelbarra5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        jPanelbarra5Layout.setVerticalGroup(
            jPanelbarra5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 389, Short.MAX_VALUE)
        );

        btnAgregarConductor.setBackground(new java.awt.Color(6, 130, 255));
        btnAgregarConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAgregarConductor.setText("Agregar");
        btnAgregarConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarConductorActionPerformed(evt);
            }
        });

        lblSegundoApellidoConductor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSegundoApellidoConductor.setText("Segundo Apellido");

        lblLicenciaConductor.setText("Licencia");

        lblFechaContratacionConductor.setText("Fecha de contratacion");

        txtLicenciaConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLicenciaConductorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCedulaConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxEstado)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lblIdConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblCedulaConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTelefonoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefonoConductor)
                    .addComponent(lblNombreConductor))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLicenciaConductor)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(txtPrimerApellidoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPrimerApellidoConductor)
                            .addComponent(txtLicenciaConductor))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSegundoApellidoConductor)
                    .addComponent(txtSegundoApellidoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCorreoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(FechaContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCorreoConductor)
                        .addGap(112, 112, 112)
                        .addComponent(lblFechaContratacionConductor)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelbarra5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanelbarra4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelbarra3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 1050, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabelRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIdConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(lblNombreConductor)
                                .addGap(35, 35, 35)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTelefonoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCedulaConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCedulaConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefonoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLicenciaConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCorreoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(26, 26, 26)
                        .addComponent(jCheckBoxEstado))
                    .addComponent(jPanelbarra5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnAgregarConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(btnModificarConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(btnEliminarConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSegundoApellidoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrimerApellidoConductor))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPrimerApellidoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSegundoApellidoConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblLicenciaConductor)
                                    .addComponent(lblCorreoConductor)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(lblFechaContratacionConductor)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FechaContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jPanelbarra4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPaneRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanelbarra3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarConductorActionPerformed
        modificarChofer();
    }//GEN-LAST:event_btnModificarConductorActionPerformed

    private void btnEliminarConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarConductorActionPerformed
        eliminarChofer();
    }//GEN-LAST:event_btnEliminarConductorActionPerformed

    private void jCheckBoxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxEstadoActionPerformed

    private void txtTelefonoConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoConductorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoConductorActionPerformed

    private void btnAgregarConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarConductorActionPerformed
        agregarChofer();
    }//GEN-LAST:event_btnAgregarConductorActionPerformed

    private void txtSegundoApellidoConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSegundoApellidoConductorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSegundoApellidoConductorActionPerformed

    private void txtLicenciaConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLicenciaConductorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLicenciaConductorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser FechaContratacion;
    private javax.swing.JButton btnAgregarConductor;
    private javax.swing.JButton btnEliminarConductor;
    private javax.swing.JButton btnModificarConductor;
    private javax.swing.JCheckBox jCheckBoxEstado;
    private javax.swing.JLabel jLabelRuta;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelbarra3;
    private javax.swing.JPanel jPanelbarra4;
    private javax.swing.JPanel jPanelbarra5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPaneRuta;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JLabel lblCedulaConductor;
    private javax.swing.JLabel lblCorreoConductor;
    private javax.swing.JLabel lblFechaContratacionConductor;
    private javax.swing.JLabel lblIdConductor;
    private javax.swing.JLabel lblLicenciaConductor;
    private javax.swing.JLabel lblNombreConductor;
    private javax.swing.JLabel lblPrimerApellidoConductor;
    private javax.swing.JLabel lblSegundoApellidoConductor;
    private javax.swing.JLabel lblTelefonoConductor;
    private javax.swing.JTable tablaConductores;
    private javax.swing.JTextField txtCedulaConductor;
    private javax.swing.JTextField txtCorreoConductor;
    private javax.swing.JTextField txtLicenciaConductor;
    private javax.swing.JTextField txtNombreConductor;
    private javax.swing.JTextField txtPrimerApellidoConductor;
    private javax.swing.JTextField txtSegundoApellidoConductor;
    private javax.swing.JTextField txtTelefonoConductor;
    // End of variables declaration//GEN-END:variables
}
