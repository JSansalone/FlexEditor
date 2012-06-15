package br.com.gz.editor.about;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;

import br.com.gz.editor.dao.InternalDAO;

public class VersionNotes extends JDialog {

	public VersionNotes() {

		setSize(500, 500);
		// InternalDAO.revertVersion();
		setTitle("Notas da versão " + InternalDAO.getAppVersion());
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/imagem/info.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int heigth = screenSize.height;
		int contWidth = getWidth();
		int contHeidth = getHeight();

		setLocation((width - contWidth) / 2, (heigth - contHeidth) / 2);
		
		JPanel p = new JPanel(null);
		p.setBorder(BorderFactory.createEtchedBorder());
		p.setBounds(10, 10, getWidth()-27, getHeight()-53);
		JLabel l = new JLabel(new ImageIcon((URL) getClass().getResource(
				"/imagem/java_100.png")));
		l.setBounds(p.getWidth()-100, p.getHeight()-110, 110, 110);
		p.add(l);
		getContentPane().setLayout(null);
		getContentPane().add(p);
		
		JTextArea aVersion = new JTextArea();
		aVersion.setLineWrap(true);
		aVersion.setWrapStyleWord(true);
		aVersion.setEditable(false);
		aVersion.setFont(new Font("Arial", Font.BOLD, 14));
		
		aVersion.setText(
				" - Novo ícone de inicialização.\n\n" +
				" - Aprimoramento dos perfis de configuração.\n\n" +
				" - Nova cor de fundo.");
		
		aVersion.setBorder(BorderFactory.createBevelBorder(1));
		aVersion.setBackground(p.getBackground());
		aVersion.setBounds(10, 10, p.getWidth()-20, p.getHeight()-120);
		p.add(aVersion);
		
		JButton btOk = new JButton("OK");
		btOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				dispose();
				
			}
			
		});
		btOk.setBounds(p.getWidth()/2-35, p.getHeight()-30, 70, 20);
		p.add(btOk);

	}
	
	public void showNotes(){
		
		setModal(true);
		setVisible(true);
		
	}
	
	/*public static void main(String[] args) {
		
		InternalDAO.revertVersion();
		
	}*/

}
