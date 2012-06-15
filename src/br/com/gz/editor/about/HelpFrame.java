package br.com.gz.editor.about;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpFrame extends JFrame implements ActionListener {

	private static HelpFrame instance;

	private Container window;
	private JPanel panelWindow;
	private JScrollPane scrollTextArea;
	private JTextArea areaHelp;

	private JLabel[] labels = new JLabel[4];
	private String[] lblTexts = { "In�cio", "Navega��o", "M�dulo de Edi��o",
			"M�dulo de Configura��es" };
	private String[] lblContents = new String[4];

	private JButton btOk;

	private HelpFrame() {

		setSize(600, 365);
		setTitle("Ajuda");
		setResizable(false);

		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/imagem/help_29x43.png")));

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int heigth = screenSize.height;
		int contWidth = getWidth();
		int contHeidth = getHeight();

		setLocation((width - contWidth) / 2, (heigth - contHeidth) / 2);

		contents();
		instantiatingComponents();
		instantiatingLabels();
		addMouseListeners();

	}

	public static HelpFrame getInstance() {

		if (instance == null) {

			instance = new HelpFrame();

		}

		return instance;

	}

	private void contents() {

		lblContents[0] = "        O GZ FlexEditor foi projetado para auxiliar na configura��o dos arquivos .properties dos sistemas da linha Flex.\n        Ele permite que o usu�rio possa alterar os valores da propriedades dos arquivos de maneira f�cil, r�pida e segura de modo a proteger o arquivo e as propriedades que ele cont�m.\n        Uma das vantagens do GZ FlexEditor � que ele n�o permite que o usu�rio adicione ou remova fisicamente propriedades no arquivo e sim, cadastrar em seu banco de dados interno as propriedades que o usu�rio deseja usar.\n        Isto facilita a r�pida mudan�a de configura��es de banco de dados dos sistemas al�m de proteger e manter a integridade do arquivo.\n        Ao iniciar o aplicativo, o usu�rio visualiza a janela de escolha do diret�rio raiz dos diret�rios dos sistemas da linha Flex, janela muito �til que permite a utiliza��o do aplicativo em diferentes padr�es de diret�rio.\n        O aplicativo possui dois m�dulos, o m�dulo de configura��o e o m�dulo de edi��o.\n        O que os dois m�dulos t�m em comum, � que ao entrar no aplicativo, realiza-se uma busca no diret�rio raiz, verificando a exist�ncia de cada diret�rio properties de cada sistema.";
		lblContents[1] = "        A navega��o em ambos os m�dulos � basicamente igual. O menu apenas ser� habilitado se o diret�rio properties do sistema existir.";
		lblContents[2] = "        O m�dulo de edi��o possibilita a altera��o dos valores das propriedades dos arquivo desde que estejam cadastradas no arquivo.\n        Caso n�o haja nenhuma propriedade cadastrada para o arquivo, um aviso ser� exibido solicitando ao usu�rio que realize o cadastro no m�dulo de configura��es.\n        Para editar um valor de uma propriedade, selecione o sistema e o arquivo desejado e clique no bot�o \"editar\" referente � propriedade desejada, ap�s alterar tecle enter ou clique em salvar.";
		lblContents[3] = "        O m�dulo de configura��es permite que o usu�rio cadastre as propriedades que ele deseja permitir que sejam alteradas.\n        Para cadastrar, selecione o sistema e o arquivo desejado e insira o nome da propriedade e a sua descri��o.\n        N�o � poss�vel cadastrar nem remover uma propriedade inexistente no arquivo. Ao cadastrar uma propriedade, esta n�o ser� adicionada ao arquivo e sim, ser� registrada no banco de dados como uma propriedade edit�vel e ao remover, apenas ser� removida do banco de dados e n�o do arquivo.";

	}

	private void instantiatingLabels() {

		for (int i = 0; i <= 3; i++) {

			labels[i] = new JLabel(lblTexts[i]);

			labels[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

			panelWindow.add(labels[i]);

		}

		labels[0].setBounds(15, 15, 35, 20);
		labels[1].setBounds(15, 55, 63, 20);
		labels[2].setBounds(15, 100, 100, 20);
		labels[3].setBounds(15, 145, 150, 20);

		btOk = new JButton("Sair");

		btOk.setBounds(15, 295, 70, 20);
		btOk.addActionListener(this);

		panelWindow.add(btOk);

	}

	private static int indexLabelClicked = -1;

	private void setLabelClicked(int index) {

		indexLabelClicked = index;

		for (int i = 0; i <= 3; i++) {

			if (i == index) {

				labels[i].setText("<html><u><b>" + lblTexts[i]
						+ "</html></b></u>");

			} else {

				labels[i].setText(lblTexts[i]);

			}

		}

	}

	private void addMouseListeners() {

		labels[0].addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (indexLabelClicked != 0) {

					labels[0].setText(lblTexts[0]);

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				if (indexLabelClicked != 0) {

					labels[0]
							.setText("<html><u>" + lblTexts[0] + "</u></html>");

				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				setLabelClicked(0);
				setContents();

			}
		});

		labels[1].addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (indexLabelClicked != 1) {

					labels[1].setText(lblTexts[1]);

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				if (indexLabelClicked != 1) {

					labels[1]
							.setText("<html><u>" + lblTexts[1] + "</u></html>");

				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setLabelClicked(1);
				setContents();

			}
		});

		labels[2].addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (indexLabelClicked != 2) {

					labels[2].setText(lblTexts[2]);

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				if (indexLabelClicked != 2) {

					labels[2]
							.setText("<html><u>" + lblTexts[2] + "</u></html>");

				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setLabelClicked(2);
				setContents();

			}
		});

		labels[3].addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (indexLabelClicked != 3) {

					labels[3].setText(lblTexts[3]);

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				if (indexLabelClicked != 3) {

					labels[3]
							.setText("<html><u>" + lblTexts[3] + "</u></html>");

				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setLabelClicked(3);
				setContents();

			}
		});

	}

	private void instantiatingComponents() {

		window = getContentPane();
		panelWindow = new JPanel(null);
		areaHelp = new JTextArea();
		scrollTextArea = new JScrollPane(areaHelp);

		scrollTextArea.setBounds(165, 15, 410, 300);

		areaHelp.setEditable(false);
		areaHelp.setText(lblContents[0]);
		areaHelp.setLineWrap(true);
		areaHelp.setWrapStyleWord(true);

		panelWindow.add(scrollTextArea);
		window.add(panelWindow);

	}

	private void setContents() {

		areaHelp.setText(lblContents[indexLabelClicked]);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btOk) {

			dispose();

		}

	}

}
