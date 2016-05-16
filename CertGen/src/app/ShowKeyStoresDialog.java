package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.security.KeyStore;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class ShowKeyStoresDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox keyStoresFromFileSystem;
	private KeyStore selectedKeyStore;
	private char[] keyStorePassword;
	private boolean first = true;

	/**
	 * Create the dialog.
	 */
	public ShowKeyStoresDialog() {
		setBounds(100, 100, 263, 122);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[]"));
		{
			JLabel lblChoseKeystore = new JLabel("Chose KeyStore");
			contentPanel.add(lblChoseKeystore, "cell 0 0,alignx trailing");
		}
		{
			final ShowKeyStoresDialog that = this;
			keyStoresFromFileSystem = new JComboBox();
			keyStoresFromFileSystem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (first){
						first = false;
					} 
					else if (keyStoresFromFileSystem.getSelectedIndex() == 0)
					{
						return;
					}
					else {
						CheckKeyStoreDialog cksd = new CheckKeyStoreDialog(keyStoresFromFileSystem.getSelectedItem().toString(), that);
						cksd.setModal(true);
						cksd.setLocationRelativeTo(BaseWindow.getInstance());
						cksd.setVisible(true);
					}
				}
			});
			
			
			contentPanel.add(keyStoresFromFileSystem, "cell 1 0,growx");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				final ShowKeyStoresDialog sksd = this;
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						EnterCertificateInfoDialog cid = new EnterCertificateInfoDialog(selectedKeyStore, keyStorePassword,keyStoresFromFileSystem.getSelectedItem().toString());
						cid.setModal(true);
						cid.setLocationRelativeTo(BaseWindow.getInstance());
						cid.setVisible(true);
						sksd.dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				final ShowKeyStoresDialog sksd = this;
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sksd.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		loadKeyStores();
	}
	/**
	 * Loads all keyStores from folder 'keyStore'.
	 */
	private void loadKeyStores(){
		File folder = new File("./keyStore");
		File[] keyStoreFiles = folder.listFiles();
		
		KeyStore keyStore;
		this.keyStoresFromFileSystem.addItem(" ");
		for(File keyStoreFile : keyStoreFiles){
			this.keyStoresFromFileSystem.addItem(keyStoreFile.getName());
		}
	}
	public JComboBox getKeyStoresFromFileSystem() {
		return keyStoresFromFileSystem;
	}
	public void setKeyStoresFromFileSystem(JComboBox keyStoresFromFileSystem) {
		this.keyStoresFromFileSystem = keyStoresFromFileSystem;
	}
	public KeyStore getSelectedKeyStore() {
		return selectedKeyStore;
	}
	public void setSelectedKeyStore(KeyStore selectedKeyStore) {
		this.selectedKeyStore = selectedKeyStore;
	}
	public char[] getKeyStorePassword() {
		return keyStorePassword;
	}
	public void setKeyStorePassword(char[] keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}
}
