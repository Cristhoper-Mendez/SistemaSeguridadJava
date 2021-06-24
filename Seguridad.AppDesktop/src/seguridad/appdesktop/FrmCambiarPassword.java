package seguridad.appdesktop;

// Importaciones para el funcionamiento de la pantalla de Cambiar password
import seguridad.appdesktop.utils.*; // importar todas las clases de utilidades de la aplicaciones escritorio
import seguridad.accesoadatos.*; // importar todas la clases de la capa de acceso a datos
import seguridad.entidades.*; // importar todas la clases de la capa de entidades de negocio
import javax.swing.JOptionPane; // importa la clase JOptionPane para mostrar alertas o advertencias a los usuarios

public class FrmCambiarPassword extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Metodos y propiedades locales de la clase FrmCambiarPassword">
    private javax.swing.JFrame frmPadre; // Propiedad para almacenar la pantalla de Inicio del sistema
    private int idUsuario; // Propiedad para almacenar el Id del usuario actal del sistema

    // Metodo que se ejecutara en el contructor de esta clase para obtener y iniciar los datos del formulario
    private void iniciarDatos(javax.swing.JFrame pFrmPadre) {
        this.frmPadre = pFrmPadre; // asignar a la propiedad frmPadre la instancia del formulario FrmInicio
        this.txtLogin.setEnabled(false); // deshabilitar la caja de texto de txtLogin para que no se pueda editar su contenido
        Usuario usuarioB = new Usuario(); // instancia de usuario que utilizaremos para hacer un filtro en la base de datos
        // asignar el Id al usuario que se buscara en la base de datos con el Id que tenemos en cache del usuario
        // que inicio sesion 
        usuarioB.setId(UsuarioAutorizado.getId());
        try {
            Usuario usuario = UsuarioDAL.obtenerPorId(usuarioB); // Obtener un usuario por Id en la DAL de usuario 
            if (usuario.getId() > 0) { // validar que el usuario que retorno su id sea mayor a cero
                // asignar el login a la caja de texto txtLogin con el valor que regreso el metodo obtenerPorId en la propiedad Login
                this.txtLogin.setText(usuario.getLogin());
                // asignar a la propiedad idUsuario con el valor que retorno el metodo obtenerPorId en la propiedad Id  
                this.idUsuario = usuario.getId();
            }
        } catch (Exception ex) {
            // mostrar al usuario el error en el caso que suceda al momento obtener los datos 
            JOptionPane.showMessageDialog(pFrmPadre, "Sucedio el siguiente error: " + ex.getMessage());
        }

    }

    // metodo para validar si un valor tipo texto no esta vacio
    private boolean esTextoValido(String pTexto) {
        boolean esValido = false;
        if (pTexto != null && pTexto.trim().isEmpty() == false) {
            esValido = true;
        }
        return esValido;
    }

    // metodo para enviar los datos a la capa de acceso a datos y esta la envie a la base de datos
    private void enviarDatos() {
        Usuario usuario = new Usuario();
        usuario.setId(this.idUsuario);
        usuario.setLogin(this.txtLogin.getText());
        usuario.setPassword(new String(this.txtNuevoPassword.getPassword()));
        usuario.setConfirmPassword_aux(new String(this.txtConfirmarPassword.getPassword()));
        String passwordActual = new String(this.txtPasswordActual.getPassword());
        boolean esValido = true;
        if (!esTextoValido(usuario.getPassword())) {
            esValido = false;
        } else if (!esTextoValido(usuario.getConfirmPassword_aux())) {
            esValido = false;
        } else if (!esTextoValido(passwordActual)) {
            esValido = false;
        }
        if (esValido) {
            if (usuario.getPassword().equals(usuario.getConfirmPassword_aux())) {
                try {
                    int result = UsuarioDAL.cambiarPassword(usuario, passwordActual);
                    if (result != 0) {
                        if (frmPadre != null) {
                            frmPadre.setEnabled(false);
                            frmPadre.setVisible(false);
                            FrmLogin frmLogin = new FrmLogin();
                            frmLogin.setVisible(true);
                        }
                        this.setVisible(false);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Sucedio un error al momento de realizar el cambio de password");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "El nuevo password y confirmar password tiene que ser iguales");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Los campos con * son obligatorios");
        }
    }

    // cerrar el formulario
    private void cerrarFormulario(boolean pIsEvtClosing) {
        if (frmPadre != null) {
            frmPadre.setEnabled(true); // habilitar el formulario de Inicio
        }
        if (pIsEvtClosing == false) {
            this.setVisible(false); // cerrar el formulario de FrmRolLec
            this.dispose(); // cerrar el formulario de FrmRolLec
        }
    }

    // </editor-fold> 
    /**
     * Creates new form FrmCambiarPassword
     */
    public FrmCambiarPassword(javax.swing.JFrame pFrmPadre) {
        initComponents();
        this.iniciarDatos(pFrmPadre);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        txtPasswordActual = new javax.swing.JPasswordField();
        txtNuevoPassword = new javax.swing.JPasswordField();
        txtConfirmarPassword = new javax.swing.JPasswordField();
        btnCambiarPassword = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cambiar Password");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Login");

        jLabel2.setText("Passoword actual*");

        jLabel3.setText("Nuevo password*");

        jLabel4.setText("Confirmar password*");

        btnCambiarPassword.setMnemonic('P');
        btnCambiarPassword.setText("Cambiar password");
        btnCambiarPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarPasswordActionPerformed(evt);
            }
        });

        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtConfirmarPassword)
                            .addComponent(txtNuevoPassword)
                            .addComponent(txtPasswordActual)
                            .addComponent(txtLogin)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCambiarPassword)
                        .addGap(69, 69, 69)
                        .addComponent(btnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPasswordActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNuevoPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtConfirmarPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCambiarPassword)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.cerrarFormulario(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCambiarPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarPasswordActionPerformed
        this.enviarDatos();
    }//GEN-LAST:event_btnCambiarPasswordActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.cerrarFormulario(true);
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarPassword;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField txtConfirmarPassword;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JPasswordField txtNuevoPassword;
    private javax.swing.JPasswordField txtPasswordActual;
    // End of variables declaration//GEN-END:variables
}
