package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bouncycastle.asn1.x500.X500NameBuilder;

import actions.GenerateCertificateAction;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class CertificateDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField surname;
	private JTextField common_name;
	private JTextField organization_unit;
	private JTextField organization_name;
	private JTextField given_name;
	private JTextField state_name;
	private JTextField country_code;
	private JTextField email_address;
	private JTextField dateField;
	private JTextField endDate;
	private JComboBox ca;
	private JComboBox keyStoreComboBox;
	private JTextField alias;
	private JPasswordField passwordField;
	private KeyStore selectedKeyStore;
	private boolean first = true;
	private boolean firstCa = true;
	
	/**
	 * Issuer data if root certificate is selected.
	 */
	private X500NameBuilder rootNameBuilder = null;
	/**
	 * Password if root certificate is selected.
	 */
	private PrivateKey rootCertificatePrivateKey = null;
	/**
	 * Password if keyStore is loaded.
	 */
	private char[] keyStorePassword;
	
	private final int KEYSTORE_INDICATOR = 0;
	private final int ROOT_CERTIFICATE_INDICATOR = 1;
	/**
	 * Create the dialog.
	 */
	public CertificateDialog() {
		final CertificateDialog that = this;
		
		
		setBounds(100, 100, 265, 444);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][][grow]", "[][][][][][][][][][][][][][]"));
		{
			JLabel CA = new JLabel("CA");
			contentPanel.add(CA, "cell 1 0");
		}
		{
			ca = new JComboBox();
			ca.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (firstCa){
						firstCa = false;
					}
					else if (ca.getSelectedIndex() == 0)
					{
						return;
					} else {
						CheckKeyStoreDialog cksd = new CheckKeyStoreDialog(ca.getSelectedItem().toString(), that,"Enter password for root certificate",ROOT_CERTIFICATE_INDICATOR);
						cksd.setModal(true);
						cksd.setLocationRelativeTo(BaseWindow.getInstance());
						cksd.setVisible(true);	
					}
					
				}
			});
			contentPanel.add(ca, "cell 3 0,growx");
		}
		{
			JLabel lblSurname = new JLabel("Surname(S)");
			contentPanel.add(lblSurname, "cell 1 1");
		}
		{
			surname = new JTextField();
			contentPanel.add(surname, "cell 3 1,growx");
			surname.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Common name(CN)");
			contentPanel.add(lblNewLabel, "cell 1 2");
		}
		{
			common_name = new JTextField();
			contentPanel.add(common_name, "cell 3 2,growx");
			common_name.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Organization unit(OU)");
			contentPanel.add(lblNewLabel_1, "cell 1 3");
		}
		{
			organization_unit = new JTextField();
			contentPanel.add(organization_unit, "cell 3 3,growx");
			organization_unit.setColumns(10);
		}
		{
			JLabel lblOrganizationNameon = new JLabel("Organization name(O)");
			contentPanel.add(lblOrganizationNameon, "cell 1 4");
		}
		{
			organization_name = new JTextField();
			contentPanel.add(organization_name, "cell 3 4,growx");
			organization_name.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Given name");
			contentPanel.add(lblNewLabel_2, "cell 1 5");
		}
		{
			given_name = new JTextField();
			contentPanel.add(given_name, "cell 3 5,growx");
			given_name.setColumns(10);
		}
		{
			JLabel lblStateName = new JLabel("State name");
			contentPanel.add(lblStateName, "cell 1 6");
		}
		{
			state_name = new JTextField();
			contentPanel.add(state_name, "cell 3 6,growx");
			state_name.setColumns(10);
		}
		{
			JLabel lblCountryCode = new JLabel("Country code(C)");
			contentPanel.add(lblCountryCode, "cell 1 7");
		}
		{
			country_code = new JTextField();
			contentPanel.add(country_code, "cell 3 7,growx");
			country_code.setColumns(10);
		}
		{
			JLabel lblEmailAdress = new JLabel("e-mail adress");
			contentPanel.add(lblEmailAdress, "cell 1 8");
		}
		{
			email_address = new JTextField();
			contentPanel.add(email_address, "cell 3 8,growx");
			email_address.setColumns(10);
		}
		{
			JLabel lblDate = new JLabel("Start Date ");
			contentPanel.add(lblDate, "cell 1 9");
		}
		{
			dateField = new JTextField();
			contentPanel.add(dateField, "cell 3 9,growx");
			dateField.setColumns(10);
		}
		{
			JLabel lblEndDate = new JLabel("End Date");
			contentPanel.add(lblEndDate, "cell 1 10");
		}
		{
			endDate = new JTextField();
			contentPanel.add(endDate, "cell 3 10,growx");
			endDate.setColumns(10);
		}
		{
			JLabel lblKeystore = new JLabel("KeyStore");
			contentPanel.add(lblKeystore, "cell 1 11");
		}
		{
			
			keyStoreComboBox = new JComboBox();
			keyStoreComboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (first){
						first = false;
					} 
					else if (keyStoreComboBox.getSelectedIndex() == 0)
					{
						return;
					}
					else {
						CheckKeyStoreDialog cksd = new CheckKeyStoreDialog(keyStoreComboBox.getSelectedItem().toString(), that,"Enter password for chosen KeyStore",KEYSTORE_INDICATOR);
						cksd.setModal(true);
						cksd.setLocationRelativeTo(BaseWindow.getInstance());
						cksd.setVisible(true);	
					}
				}
			});
			contentPanel.add(keyStoreComboBox, "cell 3 11,growx");
		}
		{
			JLabel lblAlias = new JLabel("Alias");
			contentPanel.add(lblAlias, "cell 1 12");
		}
		{
			alias = new JTextField();
			contentPanel.add(alias, "cell 3 12,growx");
			alias.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword, "cell 1 13");
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, "cell 3 13,growx");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new GenerateCertificateAction(this));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
			}
			{
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
		loadKeyStores();
	}
	
	public KeyStore getSelectedKeyStore() {
		return selectedKeyStore;
	}

	public void setSelectedKeyStore(KeyStore selectedKeyStore) {
		this.selectedKeyStore = selectedKeyStore;
	}

	public JTextField getSurname() {
		return surname;
	}
	public void setSurname(JTextField surname) {
		this.surname = surname;
	}
	public JTextField getCommon_name() {
		return common_name;
	}
	public void setCommon_name(JTextField common_name) {
		this.common_name = common_name;
	}
	public JTextField getOrganization_unit() {
		return organization_unit;
	}
	public void setOrganization_unit(JTextField organization_unit) {
		this.organization_unit = organization_unit;
	}
	public JTextField getOrganization_name() {
		return organization_name;
	}
	public void setOrganization_name(JTextField organization_name) {
		this.organization_name = organization_name;
	}
	public JTextField getGiven_name() {
		return given_name;
	}
	public void setGiven_name(JTextField given_name) {
		this.given_name = given_name;
	}
	public JTextField getState_name() {
		return state_name;
	}
	public void setState_name(JTextField state_name) {
		this.state_name = state_name;
	}
	public JTextField getCountry_code() {
		return country_code;
	}
	public void setCountry_code(JTextField country_code) {
		this.country_code = country_code;
	}
	public JTextField getEmail_address() {
		return email_address;
	}
	public void setEmail_address(JTextField email_address) {
		this.email_address = email_address;
	}
	public JTextField getDateField() {
		return dateField;
	}
	public void setDateField(JTextField dateField) {
		this.dateField = dateField;
	}
	public JTextField getEndDate() {
		return endDate;
	}
	public void setEndDate(JTextField endDate) {
		this.endDate = endDate;
	}
	public JPanel getContentPanel() {
		return contentPanel;
	}

	public JComboBox getCa() {
		return ca;
	}

	public void setCa(Enumeration<String> aliases) {
		ca.addItem(" ");
		while(aliases.hasMoreElements()){
			String alias = (String)aliases.nextElement();
			ca.addItem(alias);
		}
	}

	public JComboBox getKeyStore() {
		return keyStoreComboBox;
	}

	public void setKeyStore(JComboBox keyStore) {
		this.keyStoreComboBox = keyStore;
	}

	public JTextField getAlias() {
		return alias;
	}

	public void setAlias(JTextField alias) {
		this.alias = alias;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}
	
	/**
	 * Loads all keyStores from folder 'keyStore'.
	 */
	private void loadKeyStores(){
		File folder = new File("./keyStore");
		File[] keyStoreFiles = folder.listFiles();
		
		KeyStore keyStore;
		this.keyStoreComboBox.addItem(" ");
		for(File keyStoreFile : keyStoreFiles){
			this.keyStoreComboBox.addItem(keyStoreFile.getName());
		}
	}

	public char[] getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(char[] keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public PrivateKey getRootCertificatePrivateKey() {
		return rootCertificatePrivateKey;
	}

	public void setRootCertificatePrivateKey(PrivateKey rootCertificatePrivateKey) {
		this.rootCertificatePrivateKey = rootCertificatePrivateKey;
	}

	public X500NameBuilder getRootNameBuilder() {
		return rootNameBuilder;
	}

	public void setRootNameBuilder(X500NameBuilder rootNameBuilder) {
		this.rootNameBuilder = rootNameBuilder;
	}


}
