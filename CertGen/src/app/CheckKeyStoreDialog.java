package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyStore;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import actions.LoadKeyStoreAction;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class CheckKeyStoreDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private CertificateDialog parentDialog;
	private String fileName;

	/**
	 * Create the dialog.
	 */
	public CheckKeyStoreDialog(String fileName, CertificateDialog parentDialog) {
		this.fileName = fileName;
		this.parentDialog = parentDialog;
		setTitle("Enter password for chosen KeyStore");
		setBounds(100, 100, 316, 168);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][]"));
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword, "cell 0 1,alignx trailing");
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, "cell 1 1,growx");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new LoadKeyStoreAction(this, parentDialog, passwordField, fileName));
				buttonPane.add(okButton);
				
				getRootPane().setDefaultButton(okButton);
			}
			{
				final CheckKeyStoreDialog that = this;
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						that.dispose();
					}
				});
				buttonPane.add(cancelButton);
				
			}
		}
	}



	public JPasswordField getPasswordField() {
		return passwordField;
	}



	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

}
