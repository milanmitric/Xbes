package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class BaseWindow extends JFrame {
	
	private static BaseWindow instance = null;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	
	public BaseWindow() {
	}
	
	public static BaseWindow getInstance(){
		if (instance == null) {
			instance = new BaseWindow();
			instance.init();
		}
		return instance;
	}
	
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmGeneratecertificate = new JMenuItem("GenerateCertificate");
		mntmGeneratecertificate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		mntmGeneratecertificate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CertificateDialog cd = new CertificateDialog();
				cd.setModal(true);
				cd.setLocationRelativeTo(BaseWindow.getInstance());
				cd.setVisible(true);
				
			}
		});
		mnFile.add(mntmGeneratecertificate);
		
		JMenuItem mntmCreateKeystore = new JMenuItem("Create KeyStore");
		mntmCreateKeystore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
		mntmCreateKeystore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateKeyStoreDialog ckd = new CreateKeyStoreDialog();
				ckd.setModal(true);
				ckd.setLocationRelativeTo(BaseWindow.getInstance());
				ckd.setVisible(true);
			}
		});
		mnFile.add(mntmCreateKeystore);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		mnFile.add(mntmDelete);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
