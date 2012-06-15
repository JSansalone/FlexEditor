package br.com.gz.editor.desktop.internal.frame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import br.com.gz.editor.dao.PropertiesDAO;
import br.com.gz.editor.desktop.EditorDesktop;
import br.com.gz.editor.lock.ThreadReadOnly;

public class IFrameEdit extends JInternalFrame implements ActionListener {

	private Container window;
	private JScrollPane scrollPane;
	private JPanel panel;

	private JLabel[] propertyLabels;
	private JTextField[] propertyTextFields;
	private JButton[][] propertyButtons;

	private String system;
	private String fileName;

	private String[] valueBackup;

	private File file;

	private ThreadReadOnly threadLock;
	
	private JDesktopPane desktopParent;

	public IFrameEdit(JDesktopPane desktop, String system, String fileName) {

		this.system = system;
		this.fileName = fileName;
		this.desktopParent = desktop;

		int contWidth = desktop.getWidth();
		int contHeidth = desktop.getHeight();

		setLocation((contWidth - 500) / 2, (contHeidth - 300) / 2);

		setTitle(system + " - " + fileName);
		setClosable(true);
		setResizable(false);
		setIconifiable(true);
		setSize(500, 300);

		addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameIconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {

				threadLock.suspend();
				threadLock.unlock();
				threadLock.stop();

			}

			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub

			}
		});

		window = getContentPane();
		window.setLayout(null);

		panel = new JPanel(null);

		load();

		scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10, 10, 478, 255);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());

		window.add(scrollPane);

		setVisible(true);

	}

	private void load() {

		PropertiesDAO dao = new PropertiesDAO(system, fileName);

		String[][] properties = dao.getProperties();

		propertyLabels = new JLabel[properties.length];
		propertyButtons = new JButton[properties.length][3];
		propertyTextFields = new JTextField[properties.length];

		valueBackup = new String[properties.length];

		int i = 0;

		for (i = 0; i < properties.length; i++) {

			propertyLabels[i] = new JLabel(properties[i][1]);
			propertyTextFields[i] = new JTextField(
					dao.getPropertyValue(properties[i][0]));

			propertyButtons[i][0] = new JButton(new ImageIcon((URL) getClass()
					.getResource("/imagem/editar_edit.gif")));
			propertyButtons[i][1] = new JButton(new ImageIcon((URL) getClass()
					.getResource("/imagem/salvar_edit.png")));
			propertyButtons[i][2] = new JButton(new ImageIcon((URL) getClass()
					.getResource("/imagem/cancelar_edit.png")));

			propertyLabels[i].setBounds(10, 10 + i * 30, 150, 20);
			propertyTextFields[i].setBounds(170, 10 + i * 30, 200, 20);
			propertyButtons[i][0].setBounds(380, 10 + i * 30, 20, 20);
			propertyButtons[i][1].setBounds(405, 10 + i * 30, 20, 20);
			propertyButtons[i][2].setBounds(430, 10 + i * 30, 20, 20);

			propertyTextFields[i].setEditable(false);

			propertyTextFields[i].addActionListener(this);

			propertyButtons[i][0].addActionListener(this);
			propertyButtons[i][1].addActionListener(this);
			propertyButtons[i][2].addActionListener(this);

			propertyButtons[i][0].setToolTipText("editar");
			propertyButtons[i][1].setToolTipText("salvar");
			propertyButtons[i][2].setToolTipText("cancelar");

			propertyButtons[i][1].setEnabled(false);
			propertyButtons[i][2].setEnabled(false);

			panel.add(propertyLabels[i]);
			panel.add(propertyTextFields[i]);
			panel.add(propertyButtons[i][0]);
			panel.add(propertyButtons[i][1]);
			panel.add(propertyButtons[i][2]);

		}

		panel.setPreferredSize(new Dimension(470, 10 + i * 30));

	}

	private JTextField getTextField(JButton button) {

		for (int i = 0; i < propertyButtons.length; i++) {

			if (button.getToolTipText().equals("editar")) {

				if (button.equals(propertyButtons[i][0])) {

					return propertyTextFields[i];

				}

			} else if (button.getToolTipText().equals("salvar")) {

				if (button.equals(propertyButtons[i][1])) {

					return propertyTextFields[i];

				}

			} else if (button.getToolTipText().equals("cancelar")) {

				if (button.equals(propertyButtons[i][2])) {

					return propertyTextFields[i];

				}

			}

		}

		return null;

	}

	private JButton getCancelButton(JButton button) {

		for (int i = 0; i < propertyButtons.length; i++) {

			if (button.getToolTipText().equals("editar")) {

				if (button.equals(propertyButtons[i][0])) {

					return propertyButtons[i][2];

				}

			} else if (button.getToolTipText().equals("salvar")) {

				if (button.equals(propertyButtons[i][1])) {

					return propertyButtons[i][2];

				}

			}

		}

		return null;

	}

	private JButton getEditButton(JButton button) {

		for (int i = 0; i < propertyButtons.length; i++) {

			if (button.getToolTipText().equals("salvar")) {

				if (button.equals(propertyButtons[i][1])) {

					return propertyButtons[i][0];

				}

			} else if (button.getToolTipText().equals("cancelar")) {

				if (button.equals(propertyButtons[i][2])) {

					return propertyButtons[i][0];

				}

			}

		}

		return null;

	}

	private JButton getSaveButton(JButton button) {

		for (int i = 0; i < propertyButtons.length; i++) {

			if (button.getToolTipText().equals("editar")) {

				if (button.equals(propertyButtons[i][0])) {

					return propertyButtons[i][1];

				}

			} else if (button.getToolTipText().equals("cancelar")) {

				if (button.equals(propertyButtons[i][2])) {

					return propertyButtons[i][1];

				}

			}

		}

		return null;

	}

	private JButton getSaveButton(JTextField textField) {

		for (int i = 0; i < propertyTextFields.length; i++) {

			if (textField.equals(propertyTextFields[i])) {

				return propertyButtons[i][1];

			}

		}

		return null;

	}

	private String getLabel(JButton button) {

		for (int i = 0; i < propertyButtons.length; i++) {

			if (button.getToolTipText().equals("editar")) {

				if (button.equals(propertyButtons[i][0])) {

					return propertyLabels[i].getText();

				}

			} else if (button.getToolTipText().equals("salvar")) {

				if (button.equals(propertyButtons[i][1])) {

					return propertyLabels[i].getText();

				}

			} else if (button.getToolTipText().equals("cancelar")) {

				if (button.equals(propertyButtons[i][2])) {

					return propertyLabels[i].getText();

				}

			}

		}

		return null;

	}

	private void saveBackup(JButton button) {

		for (int i = 0; i < propertyButtons.length; i++) {

			if (button.getToolTipText().equals("editar")) {

				if (button.equals(propertyButtons[i][0])) {

					valueBackup[i] = propertyTextFields[i].getText();

				}

			}

		}

	}

	private String getBackup(JButton button) {

		for (int i = 0; i < propertyButtons.length; i++) {

			if (button.getToolTipText().equals("cancelar")) {

				if (button.equals(propertyButtons[i][2])) {

					return valueBackup[i];

				}

			}

		}

		return null;

	}

	private String strAux = null;

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() instanceof JButton) {

			JButton aux = (JButton) arg0.getSource();

			if (aux.getToolTipText().equals("editar")) {

				getTextField(aux).setEditable(true);
				getCancelButton(aux).setEnabled(true);
				getSaveButton(aux).setEnabled(true);
				aux.setEnabled(false);

				saveBackup(aux);

			} else if (aux.getToolTipText().equals("cancelar")) {

				getTextField(aux).setEditable(false);
				getEditButton(aux).setEnabled(true);
				getSaveButton(aux).setEnabled(false);
				aux.setEnabled(false);

				getTextField(aux).setText(getBackup(aux));

			} else if (aux.getToolTipText().equals("salvar")) {

				threadLock.suspend();
				threadLock.unlock();

				getTextField(aux).setEditable(false);
				getEditButton(aux).setEnabled(true);
				getCancelButton(aux).setEnabled(false);
				aux.setEnabled(false);

				PropertiesDAO dao = new PropertiesDAO(system, fileName);

				String property[] = dao.getProperty(getLabel(aux), 2);

				if (!dao.setPropertyValue(property[0], getTextField(aux)
						.getText())) {

					JOptionPane.showMessageDialog(null,
							"Erro ao editar a propriedade!", "Erro",
							JOptionPane.ERROR_MESSAGE);

					getTextField(aux).setText(strAux);

				}

				((EditorDesktop)this.desktopParent.getTopLevelAncestor()).clearSelection();
				
				threadLock.resume();

			}

		} else {

			getSaveButton((JTextField) arg0.getSource()).doClick();
			
			((EditorDesktop)this.desktopParent.getTopLevelAncestor()).clearSelection();

		}

	}

	public void setFile(File file) {

		this.file = file;
		threadLock = new ThreadReadOnly(this.file);
		threadLock.start();

	}

	public File getFile() {

		return this.file;

	}
	
	public ThreadReadOnly getThread(){
		
		return this.threadLock;
		
	}

}
