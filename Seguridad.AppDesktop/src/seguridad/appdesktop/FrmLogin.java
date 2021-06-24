package seguridad.appdesktop;

import seguridad.appdesktop.utils.*;
import seguridad.accesoadatos.*;
import seguridad.entidades.*;
import javax.swing.JOptionPane;

public class FrmLogin extends javax.swing.JFrame {

    public FrmLogin() {
        initComponents();
    }

    private void iniciarSesion() {
        try {
            Usuario usuario = new Usuario();
            // Obtener el valor de la caja de texto Login de la pantalla y llenar la propiedad Login de la clase Usuario
            usuario.setLogin(this.TxtLogin.getText());
            String pass = new String(this.TxtPassword.getPassword()); // Obtener el valor de la caja de texto Password de la pantalla de Login
            usuario.setPassword(pass); // Llenar la propiedad Password de la clase Usuario
            // Validar que la propiedad Login y Password no este vacios
            if (usuario.getLogin().trim().isEmpty() == false && usuario.getPassword().trim().isEmpty() == false) {
                Usuario usuarioAut = UsuarioDAL.login(usuario); // Autentificar el usuario en la base de datos
                // Si el Id es mayor a cero y Login que retorno el metodo de login() es igual al login que enviamos es un
                // usuario autorizado en el sistema
                if (usuarioAut.getId() > 0 && usuarioAut.getLogin().equals(usuario.getLogin())) {
                    // Llenar la propiedad Id de la clase UsuarioAutorizado para mantenerla en cache en todo el sistema
                    UsuarioAutorizado.setId(usuarioAut.getId());
                    // Llenar la propiedad Login de la clase UsuarioAutorizado para mantenerla en cache en todo el sistema
                    UsuarioAutorizado.setLogin(usuarioAut.getLogin());
                    FrmInicio frmInicio = new FrmInicio(); // Instanciar el formulario de Inicio
                    frmInicio.setVisible(true); // Mostrar el formulario de Inicio
                    this.setVisible(false); // Cerrar el formulario del Login
                } else {
                    // Mostrar un mensaje al usuario que usa la pantalla  que login y password son incorrectos
                    JOptionPane.showMessageDialog(this, "El login y password son incorrectos");
                    // Limpiar la caja de texto del password en la pantalla de login
                    this.TxtPassword.setText("");
                }
            } else {
                // Mostrar un mensaje al usuario que usa la pantalla  que login y password son obligatorios
                JOptionPane.showMessageDialog(this, "El login y password son obligatorios");
            }
        } catch (Exception ex) {
            // Mostrar un mensaje al usuario que usa la pantalla  sucedio un error al momento de autentificar el usuario
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        TxtLogin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        TxtPassword = new javax.swing.JPasswordField();
        BtnIniciar = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Iniciar sesion");

        jLabel1.setText("Login");

        jLabel2.setText("Password");

        BtnIniciar.setMnemonic('I');
        BtnIniciar.setText("Iniciar");
        BtnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIniciarActionPerformed(evt);
            }
        });

        BtnSalir.setMnemonic('S');
        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnIniciar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addComponent(BtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtLogin)
                            .addComponent(TxtPassword))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnIniciar)
                    .addComponent(BtnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Events
    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void BtnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIniciarActionPerformed
        this.iniciarSesion();
    }//GEN-LAST:event_BtnIniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnIniciar;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JTextField TxtLogin;
    private javax.swing.JPasswordField TxtPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
