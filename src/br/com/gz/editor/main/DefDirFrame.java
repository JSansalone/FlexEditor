package br.com.gz.editor.main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.gz.editor.config.EditorConfig;
import br.com.gz.editor.dao.InternalDAO;
import br.com.gz.editor.desktop.EditorDesktop;

public class DefDirFrame extends JFrame implements ActionListener {

	private Container window;
	private JPanel panel;
	private JLabel label;
	private JTextField txtDirectory;
	private JButton btOk;
	private JButton btPesq;
	private JFileChooser fileChooser;
	private JCheckBox chkNotAskAgain;

	private boolean isConfig;

	public DefDirFrame(boolean isConfig) {

		this.isConfig = isConfig;

		setSize(400, 200);
		setResizable(false);
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/imagem/icone.gif")));
		setTitle("Definir diretório padrão dos sistemas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int heigth = screenSize.height;
		int contWidth = getWidth();
		int contHeidth = getHeight();

		setLocation((width - contWidth) / 2, (heigth - contHeidth) / 2);

		window = getContentPane();

		panel = new JPanel(null);

		label = new JLabel("Defina o diretório dos sistemas");
		label.setBounds(10, 40, 200, 20);

		txtDirectory = new JTextField();
		txtDirectory.setBounds(10, 70, 350, 20);

		btPesq = new JButton(new ImageIcon((URL) getClass().getResource(
				"/imagem/procurar.png")));
		btPesq.setBounds(365, 70, 20, 20);

		chkNotAskAgain = new JCheckBox("Não me pergunte novamente");
		chkNotAskAgain.setBounds(10, 100, 200, 20);

		btOk = new JButton("Ok");
		btOk.setBounds(getWidth() - 65, 140, 50, 20);

		panel.add(label);
		panel.add(txtDirectory);
		panel.add(btPesq);
		panel.add(chkNotAskAgain);
		panel.add(btOk);

		window.add(panel);

		txtDirectory.addActionListener(this);
		btOk.addActionListener(this);
		btPesq.addActionListener(this);

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		txtDirectory.setText(new InternalDAO().getDefaultDirectory());

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btPesq) {

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

				txtDirectory.setText(fileChooser.getSelectedFile()
						.getAbsolutePath());

			}

		} else if (e.getSource() == btOk) {

			new InternalDAO().setDefaultDirectory(txtDirectory.getText());
			new InternalDAO().setDoNotAskAgain(chkNotAskAgain.isSelected());

			if (isConfig) {
				new EditorConfig(CreateDB.showNotes());
			} else {
				new EditorDesktop(CreateDB.showNotes());
			}

			dispose();

		} else if (e.getSource() == txtDirectory) {

			btOk.doClick();

		}

	}

}
