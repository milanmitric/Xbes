package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.BaseWindow;
import app.CertificateDialog;

public class GenerateCertificateAction extends AbstractAction{

	private CertificateDialog diag;
	
	public GenerateCertificateAction(CertificateDialog diag) {
		this.diag = diag;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
