package br.com.gz.editor.about;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.com.gz.editor.dao.InternalDAO;

public class AboutFrame extends JFrame {

	private static AboutFrame instance;
	
	private Container container;
	private JPanel panelAbout;
	private JPanel panelImage;
	private JPanel panelDescription;
	private JLabel lblImage;
	private JTextArea textArea;
	private JScrollPane scrollArea;
	private JButton btnnExit;

	private AboutFrame() {

		setSize(600, 400);
		setTitle("Sobre o FlexEditor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/imagem/help_29x43.png")));

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int heigth = screenSize.height;
		int contWidth = getWidth();
		int contHeidth = getHeight();

		setLocation((width - contWidth) / 2, (heigth - contHeidth) / 2);

		creatingComponents();

	}
	
	public static AboutFrame getInstance(){
		
		if(instance == null){
			
			instance = new AboutFrame();
			
		}
		
		return instance;
		
	}

	private void creatingComponents() {

		container = getContentPane();
		panelAbout = new JPanel(null);
		panelImage = new JPanel();
		panelDescription = new JPanel();

		btnnExit = new JButton("Sair");

		panelImage.setBorder(BorderFactory.createLoweredBevelBorder());
		panelImage.setBounds(10, 10, 200, 350);
		panelDescription.setBorder(BorderFactory.createEtchedBorder());
		panelDescription.setBounds(220, 10, 355, 300);

		btnnExit.setBounds(365, 340, 70, 20);
		btnnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}
		});

		textArea = new JTextArea(10, 10);
		scrollArea = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollArea.setBounds(220, 10, 355, 300);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);

		String title = "                                             GZ FlexEditor";
		String separator = "-----------------------------------------------------------------------------------";
		String description = "        Aplicativo voltado para edição e configuração de arquivos .properties dos sistemas da linha Flex";
		String author = "Desenvolvido por Jonathan Sansalone Pacheco Rolim";
		String prgLanguage = "Desenvolvido inteiramente na plataforma Java(TM) SE 7";
		String date = "\t\tVersão "+InternalDAO.getAppVersion();
		String suport = "Suporte: jonathan.sansalone@gzsistemas.com.br";
		String company = "GZ Sistemas Importação e Comércio LTDA.";

		textArea.setFont(new Font(null, Font.BOLD, 12));

		textArea.setText(title + "\n" + separator + "\n\n" + description
				+ "\n\n\n" + author + "\n\n" + prgLanguage + "\n\n" + suport
				+ "\n\n\n" + company + "\n\n\n" + date);

		panelImage.add(new JLabel(new ImageIcon((URL) getClass().getResource(
				"/imagem/cqgz.png"))));
		panelImage.setBackground(Color.WHITE);

		panelAbout.add(btnnExit);
		panelAbout.add(panelImage);
		panelAbout.add(scrollArea);

		container.add(panelAbout);

	}

}
