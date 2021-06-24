package seguridad.appdesktop;

import seguridad.appdesktop.utils.*;
import seguridad.accesoadatos.*; // importar todas la clases de la capa de acceso a datos
import seguridad.entidades.*; // importar todas la clases de la capa de entidades de negocio
import java.util.ArrayList; // importar el ArrayList para recibir la lista de Usuarios de la base de datos
import javax.swing.JOptionPane; // importa la clase JOptionPane para mostrar alertas o advertencias a los usuarios
import javax.swing.table.DefaultTableModel; // importar la clase DefaultTableModel para llenar los datos de la tabla

public class FrmUsuarioLec extends javax.swing.JFrame {
    
    // <editor-fold defaultstate="collapsed" desc="Codigo para las clases,propiedades y metodos locales del formulario FrmUsuarioLec">
    private javax.swing.JFrame frmPadre; // Propiedad para almacenar la pantalla de Inicio del sistema

    // Crear la clase anidada ColumnaTabla para saber la posicion de las columnas en la tabla de datos
    public class ColumnaTabla {

        static final int ID = 0;
        static final int IDROL = 1;
        static final int ESTATUS = 2;
        static final int LOGIN = 3;
        static final int NOMBRE = 4;
        static final int APELLIDO = 5;
        static final int ROL = 6;
        static final int ESTATUSSTR = 7;
        static final int FECHAREGISTRO = 8;
    }

    // Metodo para ocultar columnas de nuestra tabla de datos
    private void ocultarColumnasDeLaTabla(int pColumna) {
        this.tbUsuarios.getColumnModel().getColumn(pColumna).setMaxWidth(0); // le dejamos en el ancho maximo de la tabla en cero en la columna
        this.tbUsuarios.getColumnModel().getColumn(pColumna).setMinWidth(0);// le dejamos en el ancho minimo de la tabla en cero  en la columna
        // le dejamos en el ancho maximo de la tabla en cero en el header
        this.tbUsuarios.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        // le dejamos en el ancho minimo de la tabla en cero  en el header
        this.tbUsuarios.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    // Metodo para iniciar los datos de la tabla a partir de una lista de Usuarios
    public void iniciarDatosDeLaTabla(ArrayList<Usuario> pUsuarios) {
        // Iniciamos el DefaultTableModel utilizaremos para crear las columnas y los datos en nuestra tabla    
        DefaultTableModel model = new DefaultTableModel() {
            // aplicamos override al metodo isCellEditable para deshabilitar la edicion en la filas de la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // retornamos false para deshabilitar todas las fila y no puedan ser editables en la tabla de datos
            }
        };
        model.addColumn("Id"); // agregar la columna Id a la tabla de datos
        model.addColumn("IdRol"); // agregar la columna IdRol a la tabla de datos
        model.addColumn("EstatusEnt"); // agregar la columna EstatusEnt a la tabla de datos
        model.addColumn("Login"); // agregar la columna Login a la tabla de datos
        model.addColumn("Nombre"); // agregar la columna Nombre a la tabla de datos
        model.addColumn("Apellido"); // agregar la columna Apellido a la tabla de datos
        model.addColumn("Rol"); // agregar la columna Rol a la tabla de datos
        model.addColumn("Estatus"); // agregar la columna Estatus a la tabla de datos
        model.addColumn("Fecha Registro"); // agregar la columna Fecha Registro a la tabla de datos
        this.tbUsuarios.setModel(model);
        Object row[] = null;
        for (int i = 0; i < pUsuarios.size(); i++) {
            Usuario usuario = pUsuarios.get(i);
            model.addRow(row);
            model.setValueAt(usuario.getId(), i, ColumnaTabla.ID); // agregar el valor de la columna Id en la fila
            model.setValueAt(usuario.getEstatus(), i, ColumnaTabla.ESTATUS);
            model.setValueAt(usuario.getIdRol(), i, ColumnaTabla.IDROL);
            model.setValueAt(usuario.getLogin(), i, ColumnaTabla.LOGIN);
            model.setValueAt(usuario.getNombre(), i, ColumnaTabla.NOMBRE);
            model.setValueAt(usuario.getApellido(), i, ColumnaTabla.APELLIDO);
            model.setValueAt(usuario.getRol().getNombre(), i, ColumnaTabla.ROL);
            if (usuario.getEstatus() == Usuario.EstatusUsuario.ACTIVO) {
                model.setValueAt("ACTIVO", i, ColumnaTabla.ESTATUSSTR);
            } else if (usuario.getEstatus() == Usuario.EstatusUsuario.INACTIVO) {
                model.setValueAt("INACTIVO", i, ColumnaTabla.ESTATUSSTR);
            }
            model.setValueAt(usuario.getFechaRegistro().toString(), i, ColumnaTabla.FECHAREGISTRO);
        }
        ocultarColumnasDeLaTabla(ColumnaTabla.ID); // Ocultar la columna de Id en la tabla 
        ocultarColumnasDeLaTabla(ColumnaTabla.ESTATUS);
        ocultarColumnasDeLaTabla(ColumnaTabla.IDROL);
    }

    // Metodo para llenar la clase de Rol con los datos que tiene la fila seleccionada de la tabla
    private boolean llenarEntidadConLaFilaSeleccionadaDeLaTabla(Usuario pUsuario) {
        int filaSelect; // variable para almacenar el indice de la fila seleccionada
        boolean isSelectRow = false; // variable para saber si esta seleccionada una fila o no
        filaSelect = this.tbUsuarios.getSelectedRow(); // obtener el indice de la fila seleccionada
        if (filaSelect != -1) { // verificar que se ha seleccionado una fila el cual la variable filaSelect debe ser diferente a -1
            isSelectRow = true; // colocar en true la variable isSelectRow porque si esta seleccionada una fila
            pUsuario.setId((int) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.ID)); // agregar el valor de la columna Id a la propiedad Id
            pUsuario.setIdRol((int) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.IDROL));
            pUsuario.setNombre((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.NOMBRE));
            pUsuario.setApellido((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.APELLIDO));
            pUsuario.setEstatus((byte) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.ESTATUS));
            pUsuario.setLogin((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.LOGIN));
            pUsuario.setRol(new Rol());
            pUsuario.getRol().setNombre((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.ROL));
        }
        return isSelectRow; // devolver el valor de isSelectRow 
    }

    // El metodo abrirFormularioDeEscritura lo utilizaremos para abrir el formulario de FrmRolEsc
    private void abrirFormularioDeEscritura(int pOpcionForm) {
        Usuario usuario = new Usuario(); // Crear una instancia de la clase de Rol
        // Verificar si pOpcionForm es Crear abrimos el formulario de FrmUsuarioEsc
        // en el caso que la pOpcionForm sea diferente a Crear, se va a verificar el metodo de  llenarEntidadConLaFilaSeleccionadaDeLaTabla
        // para llenar la instancia de Usuario y verificar que este seleccionada una fila en la tabla de datos
        if (pOpcionForm == FrmEscOpcion.CREAR || this.llenarEntidadConLaFilaSeleccionadaDeLaTabla(usuario)) {
            // Abrir el formulario FrmUsuarioEsc utilizando el contructor lleno con los parametros de Usuario,OpcionForm y enviando el
            // formulario actual de FrmUsuarioEsc
            FrmUsuarioEsc frmUsuarioEsc = new FrmUsuarioEsc(usuario, pOpcionForm, this);
            frmUsuarioEsc.setVisible(true); // Mostrar el formulario FrmUsuarioEsc
            this.setVisible(false); // ocultar el formulario FrmUsuarioLec
        } else {
            // en el caso que pOpcionForm sea diferente a Crear y el metodo llenarEntidadConLaFilaSeleccionadaDeLaTabla devuelva false
            // se le notificara al usuario del sistema que debe de seleccionar una fila de la tabla 
            JOptionPane.showMessageDialog(this, "No ha seleccionado ninguna fila.");
        }

    }

    // metodo que se utilizara para buscar los datos en la base de datos al dar click en el boton de buscar
    private void buscar() {
        try {
            Usuario usuarioSearch = new Usuario();
            usuarioSearch.setTop_aux(TopRegistro.obtenerValorSeleccionado(cbTop));
            usuarioSearch.setLogin(this.txtLogin.getText());
            usuarioSearch.setNombre(this.txtNombre.getText());
            usuarioSearch.setApellido(this.txtApellido.getText());
            ItemsCombo itemsCbEstatus = (ItemsCombo) cbEstatus.getSelectedItem();
            usuarioSearch.setEstatus((byte) itemsCbEstatus.getId());
            ItemsCombo itemsCbRoles = (ItemsCombo) cbRol.getSelectedItem();
            usuarioSearch.setIdRol(itemsCbRoles.getId());
            ArrayList<Usuario> usuarios = UsuarioDAL.buscarIncluirRol(usuarioSearch); // buscar el usuario  en la base de datos
            iniciarDatosDeLaTabla(usuarios); // iniciar la tabla con los datos obtenidos en el metodo de buscar de la DAL de Usuario
        } catch (Exception ex) {
            // mostrar un error al usuario de pantalla en el caso que suceda al momento de obtener los datos
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    // limpiar los controles que tienen la informacion a enviar a buscar los Usuarios en la base de datos
    private void limpiarControles() {
        this.txtNombre.setText(""); // limpiar la caja de texto 
        TopRegistro.limpiarTopRegistro(cbTop); // iniciar el combo box de cbTop al valor 10
        this.txtApellido.setText(""); // limpiar la caja de texto 
        this.txtLogin.setText("");  // limpiar la caja de texto  
        this.cbRol.setSelectedItem(new ItemsCombo(0, null));  // iniciar  el combo box Rol al valor 0  "SELECCIONAR"
        this.cbEstatus.setSelectedItem(new ItemsCombo(0, null)); // iniciar  el combo box Estatus al valor 0  "SELECCIONAR"
    }

    // cerrar el formulario de FrmUsuarioLec
    private void cerrarFormulario(boolean pIsEvtClosing) {
        if (frmPadre != null) {
            frmPadre.setEnabled(true); // habilitar el formulario de Inicio
        }
        if (pIsEvtClosing == false) {
            this.setVisible(false); // cerrar el formulario de FrmUsuarioLec
            this.dispose(); // cerrar el formulario de FrmUsuarioLec
        }
    }

    // Metodo para agregar los valor al combo box de Estatus
    public void iniciarComboEstatus(javax.swing.JComboBox<ItemsCombo> pJComboBox) {
        pJComboBox.addItem(new ItemsCombo(0, "SELECCIONAR"));
        pJComboBox.addItem(new ItemsCombo(Usuario.EstatusUsuario.ACTIVO, "ACTIVO"));
        pJComboBox.addItem(new ItemsCombo(Usuario.EstatusUsuario.INACTIVO, "INACTIVO"));
    }
    // Metodo para agregar los valor al combo box de Rol obteniendo los datos desde la base de datos

    public void iniciarComboRol(javax.swing.JComboBox<ItemsCombo> pJComboBox, javax.swing.JFrame pFrame) {
        pJComboBox.addItem(new ItemsCombo(0, "SELECCIONAR"));
        try {
            ArrayList<Rol> roles = RolDAL.obtenerTodos();
            for (int i = 0; i < roles.size(); i++) {
                Rol rol = roles.get(i);
                pJComboBox.addItem(new ItemsCombo(rol.getId(), rol.getNombre()));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pFrame, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    private void iniciarDatos(javax.swing.JFrame pFrmPadre) {
        frmPadre = pFrmPadre;
        pFrmPadre.setEnabled(true); // deshabilitar el formulario FrmInicio
        TopRegistro.llenarDatos(cbTop); // iniciar los datos del combo box cbTop con los valores a enviar en el top registro 
        iniciarComboEstatus(this.cbEstatus); // iniciar del combo box estatus
        iniciarComboRol(this.cbRol, this.frmPadre);  // iniciar del combo box Rol
    }

    // contructor de la clae FrmRolLec que pide como parametro un JFrame
    // en nuestro caso pedira como parametro el formulario FrmInicio
    public FrmUsuarioLec(javax.swing.JFrame pFrmPadre) {
        initComponents();
        iniciarDatos(pFrmPadre); // llamar el metodo IniciarDatos para cargar los combo box y llenar propiedades iniciales del formulario
    }
// </editor-fold> 

    public FrmUsuarioLec() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbRol = new javax.swing.JComboBox<>();
        btnEliminar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnVer = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbEstatus = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        cbTop = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        txtApellido = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUsuarios = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel5.setText("Estatus");

        btnVer.setText("Ver");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        jLabel6.setText("Top");

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Apellido");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel3.setText("Login");

        tbUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbUsuarios);

        jLabel4.setText("Rol");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBuscar)
                                .addGap(38, 38, 38)
                                .addComponent(btnLimpiar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNuevo)
                                .addGap(18, 18, 18)
                                .addComponent(btnModificar)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminar)
                                .addGap(18, 18, 18)
                                .addComponent(btnVer)
                                .addGap(18, 18, 18)
                                .addComponent(btnCerrar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel5))
                                        .addGap(40, 40, 40)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLogin)
                                            .addComponent(cbEstatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(39, 39, 39)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(79, 79, 79)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtApellido)
                                    .addComponent(cbRol, 0, 132, Short.MAX_VALUE)
                                    .addComponent(cbTop, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(cbEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnVer)
                    .addComponent(btnCerrar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Events
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        this.limpiarControles();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.abrirFormularioDeEscritura(FrmEscOpcion.CREAR);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        this.abrirFormularioDeEscritura(FrmEscOpcion.MODIFICAR);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.abrirFormularioDeEscritura(FrmEscOpcion.ELIMINAR);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        this.abrirFormularioDeEscritura(FrmEscOpcion.VER);
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.cerrarFormulario(false);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.cerrarFormulario(true);
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVer;
    private javax.swing.JComboBox<ItemsCombo> cbEstatus;
    private javax.swing.JComboBox<ItemsCombo> cbRol;
    private javax.swing.JComboBox<ItemsCombo> cbTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbUsuarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
