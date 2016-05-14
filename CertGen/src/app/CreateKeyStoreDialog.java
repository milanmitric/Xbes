package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CreateKeyStoreDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField name;
	private JTextField password;

	
	/**
	 * Create the dialog.
	 */
	public CreateKeyStoreDialog() {
		setBounds(100, 100, 391, 138);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][]"));
		{
			JLabel lblName = new JLabel("Name");
			contentPanel.add(lblName, "cell 0 0,alignx trailing");
		}
		{
			name = new JTextField();
			contentPanel.add(name, "cell 1 0,growx");
			name.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword, "cell 0 1,alignx trailing");
		}
		{
			password = new JTextField();
			contentPanel.add(password, "cell 1 1,growx");
			password.setColumns(10);
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
