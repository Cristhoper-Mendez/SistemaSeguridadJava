package seguridad.appdesktop;

public class FrmInicio extends javax.swing.JFrame {

    public FrmInicio() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        meInicio = new javax.swing.JMenuBar();
        meMantenimiento = new javax.swing.JMenu();
        meRol = new javax.swing.JMenuItem();
        meUsuario = new javax.swing.JMenuItem();
        meCambiarPassword = new javax.swing.JMenu();
        meCambiarUsuario = new javax.swing.JMenu();
        meSalir = new javax.swing.JMenu();

        jMenu5.setText("File");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar2.add(jMenu6);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        meMantenimiento.setText("Mantenimiento");

        meRol.setText("Rol");
        meRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meRolActionPerformed(evt);
            }
        });
        meMantenimiento.add(meRol);

        meUsuario.setText("Usuario");
        meUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meUsuarioActionPerformed(evt);
            }
        });
        meMantenimiento.add(meUsuario);

        meInicio.add(meMantenimiento);

        meCambiarPassword.setText("Cambiar Password");
        meCambiarPassword.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                meCambiarPasswordMenuSelected(evt);
            }
        });
        meInicio.add(meCambiarPassword);

        meCambiarUsuario.setText("Cambiar Usuario");
        meCambiarUsuario.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                meCambiarUsuarioMenuSelected(evt);
            }
        });
        meInicio.add(meCambiarUsuario);

        meSalir.setText("Salir");
        meSalir.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                meSalirMenuSelected(evt);
            }
        });
        meInicio.add(meSalir);

        setJMenuBar(meInicio);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 489, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void meRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meRolActionPerformed
        FrmRolLec frmRolLec = new FrmRolLec(this);
        frmRolLec.setVisible(true);
        this.setEnabled(false);
    }//GEN-LAST:event_meRolActionPerformed

    private void meUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meUsuarioActionPerformed
        FrmUsuarioLec frmUsuarioLec = new FrmUsuarioLec();
        frmUsuarioLec.setVisible(true);
//         this.setEnabled(false);
    }//GEN-LAST:event_meUsuarioActionPerformed

    private void meCambiarPasswordMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_meCambiarPasswordMenuSelected
        FrmCambiarPassword frmCambiarPassword = new FrmCambiarPassword(this);
        frmCambiarPassword.setVisible(true);
        frmCambiarPassword.setAlwaysOnTop(true);
         this.setEnabled(false);
    }//GEN-LAST:event_meCambiarPasswordMenuSelected

    private void meCambiarUsuarioMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_meCambiarUsuarioMenuSelected
        this.setVisible(false);
        FrmLogin frmLogin = new FrmLogin();
        frmLogin.setVisible(true);
    }//GEN-LAST:event_meCambiarUsuarioMenuSelected

    private void meSalirMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_meSalirMenuSelected
        this.setVisible(false);
        this.dispose();
        System.exit(0); // cerrar el sistema, usar para cerrar el sistema completamente
    }//GEN-LAST:event_meSalirMenuSelected


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenu meCambiarPassword;
    private javax.swing.JMenu meCambiarUsuario;
    private javax.swing.JMenuBar meInicio;
    private javax.swing.JMenu meMantenimiento;
    private javax.swing.JMenuItem meRol;
    private javax.swing.JMenu meSalir;
    private javax.swing.JMenuItem meUsuario;
    // End of variables declaration//GEN-END:variables
}
