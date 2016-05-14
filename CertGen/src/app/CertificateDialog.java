package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CertificateDialog dialog = new CertificateDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CertificateDialog() {
		setBounds(100, 100, 265, 338);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][][grow]", "[][][][][][][][][]"));
		{
			JLabel CA = new JLabel("CA");
			contentPanel.add(CA, "cell 1 0");
		}
		{
			JComboBox ca = new JComboBox();
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
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
