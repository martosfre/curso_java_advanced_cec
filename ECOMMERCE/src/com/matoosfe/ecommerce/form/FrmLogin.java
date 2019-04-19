package com.matoosfe.ecommerce.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.TitledBorder;

import com.matoosfe.ecommerce.modelo.Usuario;
import com.matoosfe.ecommerce.negocio.UsuarioTrs;

import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class FrmLogin extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomUsu;
	private JPasswordField ptxtClaUsu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Setear el look and feel
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					// Crear y mostrar el formulario
					FrmLogin frame = new FrmLogin();
					frame.setVisible(true);
					// Ubicar en el centro
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmLogin() {
		setResizable(false);
		setTitle("::ECOMMERCE::");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 496, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 0));
		setContentPane(contentPane);

		JPanel panLog = new JPanel();
		panLog.setBackground(new Color(70, 130, 180));
		panLog.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Credenciales", TitledBorder.LEFT,
				TitledBorder.TOP, null, new Color(255, 255, 255)));
		contentPane.add(panLog, BorderLayout.CENTER);
		GridBagLayout gbl_panLog = new GridBagLayout();
		gbl_panLog.columnWidths = new int[] { 96, 46, 103, 0 };
		gbl_panLog.rowHeights = new int[] { 76, 20, 20, 23, 0 };
		gbl_panLog.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panLog.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panLog.setLayout(gbl_panLog);

		JLabel lblNomUsu = new JLabel("Usuario:");
		lblNomUsu.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNomUsu.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblNomUsu = new GridBagConstraints();
		gbc_lblNomUsu.anchor = GridBagConstraints.WEST;
		gbc_lblNomUsu.insets = new Insets(0, 0, 5, 5);
		gbc_lblNomUsu.gridx = 1;
		gbc_lblNomUsu.gridy = 1;
		panLog.add(lblNomUsu, gbc_lblNomUsu);

		txtNomUsu = new JTextField();
		GridBagConstraints gbc_txtNomUsu = new GridBagConstraints();
		gbc_txtNomUsu.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNomUsu.anchor = GridBagConstraints.NORTH;
		gbc_txtNomUsu.insets = new Insets(0, 0, 5, 0);
		gbc_txtNomUsu.gridx = 2;
		gbc_txtNomUsu.gridy = 1;
		panLog.add(txtNomUsu, gbc_txtNomUsu);
		txtNomUsu.setColumns(10);

		JLabel lblClaUsu = new JLabel("Clave:");
		lblClaUsu.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblClaUsu.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblClaUsu = new GridBagConstraints();
		gbc_lblClaUsu.anchor = GridBagConstraints.WEST;
		gbc_lblClaUsu.insets = new Insets(0, 0, 5, 5);
		gbc_lblClaUsu.gridx = 1;
		gbc_lblClaUsu.gridy = 2;
		panLog.add(lblClaUsu, gbc_lblClaUsu);

		ptxtClaUsu = new JPasswordField();
		ptxtClaUsu.setEchoChar('*');
		GridBagConstraints gbc_ptxtClaUsu = new GridBagConstraints();
		gbc_ptxtClaUsu.anchor = GridBagConstraints.NORTH;
		gbc_ptxtClaUsu.fill = GridBagConstraints.HORIZONTAL;
		gbc_ptxtClaUsu.insets = new Insets(0, 0, 5, 0);
		gbc_ptxtClaUsu.gridx = 2;
		gbc_ptxtClaUsu.gridy = 2;
		panLog.add(ptxtClaUsu, gbc_ptxtClaUsu);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btnIngLog = new JButton("Ingresar");
		btnIngLog.setIcon(
				new ImageIcon(FrmLogin.class.getResource("/com/matoosfe/ecommerce/resources/iconoSecurity48x48.png")));
		btnIngLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 1.Verificar los campos obligatorios según la bdd
					if (!txtNomUsu.getText().equals("") && !ptxtClaUsu.getText().equals("")) {
						// 2.Recuperar esos campos y llamar al controlador
						UsuarioTrs admUsu = new UsuarioTrs();
						Usuario usuario = admUsu.validarUsuario(txtNomUsu.getText(), ptxtClaUsu.getText());
						// 3. Verificar respuesta
						if (usuario != null) {
							// Cerrar la pantalla del login
							FrmLogin.this.dispose();
							// Llamar a la otra clase
							FrmPrincipal frmPrincipal = new FrmPrincipal();
							frmPrincipal.setVisible(true);
							// Maximizar la pantalla
							frmPrincipal.setExtendedState(MAXIMIZED_BOTH);
						}else {
							JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Errores", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Error credenciales", "Errores", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error Ingreso:" + e1.getMessage(), "Errores",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		panel.add(btnIngLog);
	}
}
