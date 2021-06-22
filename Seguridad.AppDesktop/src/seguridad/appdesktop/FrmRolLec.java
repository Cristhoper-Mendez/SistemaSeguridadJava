package seguridad.appdesktop;

import seguridad.appdesktop.utils.*;
import seguridad.accesoadatos.*;
import seguridad.entidades.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmRolLec extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Codigo para las clases,propiedades y metodos locales del formulario FrmRolLec">
    private javax.swing.JFrame frmPadre; // Propiedad para almacenar la pantalla de Inicio del sistema

    // Crear la clase anidada ColumnaTabla para saber la posicion de las columnas en la tabla de datos
    public class ColumnaTabla {

        static final int ID = 0; // El campo Id sera  la primera columna en la tabla de datos
        static final int NOMBRE = 1; // El campo Nombre sera  la segunda columna en la tabla de datos
    }

    // Metodo para ocultar columnas de nuestra tabla de datos
    private void ocultarColumnasDeLaTabla(int pColumna) {
        this.tbRoles.getColumnModel().getColumn(pColumna).setMaxWidth(0); // le dejamos en el ancho maximo de la tabla en cero en la columna
        this.tbRoles.getColumnModel().getColumn(pColumna).setMinWidth(0);// le dejamos en el ancho minimo de la tabla en cero  en la columna
        // le dejamos en el ancho maximo de la tabla en cero en el header
        this.tbRoles.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        // le dejamos en el ancho minimo de la tabla en cero  en el header
        this.tbRoles.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    // Metodo para iniciar los datos de la tabla a partir de una lista de Roles
    public void iniciarDatosDeLaTabla(ArrayList<Rol> pRoles) {
        // Iniciamos el DefaultTableModel utilizaremos para crear las columnas y los datos en nuestra tabla    
        DefaultTableModel model = new DefaultTableModel() {
            // aplicamos override al metodo isCellEditable para deshabilitar la edicion en la filas de la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // retornamos false para deshabilitar todas las fila y no puedan ser editables en la tabla de datos
            }
        };
        model.addColumn("Id"); // agregar la columna Id a la tabla de datos 
        model.addColumn("Nombre"); // agregar la columna Nombre a la tabla de Datos           
        this.tbRoles.setModel(model); // iniciar el DefaultTableModel en nuestra tabla de datos
        Object row[] = null; // crear un array de un objecto para iniciar los datos de la fila
        for (int i = 0; i < pRoles.size(); i++) { // Recorrer el array de Roles
            Rol rol = pRoles.get(i);  // Obtener un Rol de array de Roles por su indice
            model.addRow(row); // Iniciar una fila vacia en la tabla de datos
            model.setValueAt(rol.getId(), i, ColumnaTabla.ID); // agregar el valor de la columna Id en la fila
            model.setValueAt(rol.getNombre(), i, ColumnaTabla.NOMBRE); // agregar el valor de la columna Nombre en la fila
        }
        ocultarColumnasDeLaTabla(ColumnaTabla.ID); // Ocultar la columna de Id en la tabla 
    }

    // Metodo para llenar la clase de Rol con los datos que tiene la fila seleccionada de la tabla
    private boolean llenarEntidadConLaFilaSeleccionadaDeLaTabla(Rol pRol) {
        int filaSelect; // variable para almacenar el indice de la fila seleccionada
        boolean isSelectRow = false; // variable para saber si esta seleccionada una fila o no
        filaSelect = this.tbRoles.getSelectedRow(); // obtener el indice de la fila seleccionada
        if (filaSelect != -1) { // verificar que se ha seleccionado una fila el cual la variable filaSelect debe ser diferente a -1
            isSelectRow = true; // colocar en true la variable isSelectRow porque si esta seleccionada una fila
            int id = (int) this.tbRoles.getValueAt(filaSelect, ColumnaTabla.ID); // obtener el valor de la fila en la columna Id
            String nombre = (String) this.tbRoles.getValueAt(filaSelect, ColumnaTabla.NOMBRE);// obtener el valor de la fila en la columna Nombre
            pRol.setId(id); // Llenar propiedad de Id de Rol con el valor obtenido de la fila de la tabla 
            pRol.setNombre(nombre); // Llenar propiedad de Nombre de Rol con el valor obtenido de la fila de la tabla 
        }
        return isSelectRow; // devolver el valor de isSelectRow 
    }

    // El metodo abrirFormularioDeEscritura lo utilizaremos para abrir el formulario de FrmRolEsc
    private void abrirFormularioDeEscritura(int pOpcionForm) {
        Rol rol = new Rol(); // Crear una instancia de la clase de Rol
        // Verificar si pOpcionForm es Crear abrimos el formulario de FrmRolEsc
        // en el caso que la pOpcionForm sea diferente a Crear, se va a verificar el metodo de  llenarEntidadConLaFilaSeleccionadaDeLaTabla
        // para llenar la instancia de Rol y verificar que este seleccionada una fila en la tabla de datos
        if (pOpcionForm == FrmEscOpcion.CREAR || this.llenarEntidadConLaFilaSeleccionadaDeLaTabla(rol)) {
            // Abrir el formulario FrmRolEsc utilizando el contructor lleno con los parametros de Rol,OpcionForm y enviando el
            // formulario actual de FrmRolLec
            FrmRolEsc frmRolEsc = new FrmRolEsc(rol, pOpcionForm, this);
            frmRolEsc.setVisible(true); // Mostrar el formulario FrmRolEsc
            this.setVisible(false); // ocultar el formulario FrmRolLec
        } else {
            // en el caso que pOpcionForm sea diferente a Crear y el metodo llenarEntidadConLaFilaSeleccionadaDeLaTabla devuelva false
            // se le notificara al usuario del sistema que debe de seleccionar una fila de la tabla 
            JOptionPane.showMessageDialog(this, "No ha seleccionado ninguna fila.");
        }

    }

    // metodo que se utilizara para buscar los datos en la base de datos al dar click en el boton de buscar
    private void buscar() {
        try {
            Rol rolSearch = new Rol();
            // llenar la propiedad de Top de la instancia de Rol con el valor seleccionado en el combobox cbTop
            rolSearch.setTop_aux(TopRegistro.obtenerValorSeleccionado(CbTop));
            // llenar la propiedad Nombre de la instancia de Rol con el valor de caja de texto txtNombre
            rolSearch.setNombre(this.TxtNombre.getText());
            ArrayList<Rol> roles = RolDAL.buscar(rolSearch); // buscar el rol en la base de datps
            iniciarDatosDeLaTabla(roles); // iniciar la tabla con los datos obtenidos en el metodo de buscar de la DAL de Rol
        } catch (Exception ex) {
            // mostrar un error al usuario de pantalla en el caso que suceda al momento de obtener los datos
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    // limpiar los controles que tienen la informacion a enviar a buscar los roles en la base de datos
    private void limpiarControles() {
        this.TxtNombre.setText(""); // limpiar la caja de texto txtNombre
        TopRegistro.limpiarTopRegistro(CbTop); // iniciar el combo box de cbTop al valor 10
    }

    // cerrar el formulario de FrmRolLect
    private void cerrarFormulario(boolean pIsEvtClosing) {
        if (frmPadre != null) {
            frmPadre.setEnabled(true); // habilitar el formulario de Inicio
        }
        if (pIsEvtClosing == false) {
            this.setVisible(false); // cerrar el formulario de FrmRolLec
            this.dispose(); // cerrar el formulario de FrmRolLec
        }
    }

    // contructor de la clae FrmRolLec que pide como parametro un JFrame
    // en nuestro caso pedira como parametro el formulario FrmInicio
    public FrmRolLec(javax.swing.JFrame pFrmPadre) {
        initComponents();
        frmPadre = pFrmPadre;
        pFrmPadre.setEnabled(true); // deshabilitar el formulario FrmInicio
        TopRegistro.llenarDatos(CbTop); // iniciar los datos del combo box cbTop con los valores a enviar en el top registro 
    }

    public FrmRolLec() {
        initComponents();
        TopRegistro.llenarDatos(CbTop);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        CbTop = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbRoles = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnVer = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar Rol");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Top");

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

        tbRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbRoles);

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

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnVer.setText("Ver");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVer)
                        .addGap(18, 18, 18)
                        .addComponent(btnCerrar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(CbTop, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBuscar)
                                .addGap(26, 26, 26)
                                .addComponent(btnLimpiar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(CbTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnVer)
                    .addComponent(btnCerrar))
                .addContainerGap(24, Short.MAX_VALUE))
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
    private javax.swing.JComboBox<ItemsCombo> CbTop;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbRoles;
    // End of variables declaration//GEN-END:variables
}
