package br.com.gz.editor.config.internal.frame;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import br.com.gz.editor.config.EditorConfig;
import br.com.gz.editor.dao.PropertiesDAO;
import br.com.gz.editor.lock.ThreadReadOnly;

public class IFrameConfig extends JInternalFrame implements ActionListener {

	private JMenuBar menuBar;
	private JButton btNovo;
	private JButton btEditar;
	private JButton btProcurar;
	private JButton btSalvar;
	private JButton btCancelar;
	private JButton btRemover;

	private Container window;
	private JPanel panel;
	private JLabel lblNome;
	private JLabel lblDescricao;
	private JTextField txtNome;
	private JTextField txtDescricao;

	private String system;
	private String fileName;

	private boolean editMode = false;

	private File file;

	private ThreadReadOnly threadLock;

	public IFrameConfig(JDesktopPane desktop, String system, String fileName) {

		this.system = system;
		this.fileName = fileName;

		setSize(400, 200);

		int contWidth = desktop.getWidth();
		int contHeidth = desktop.getHeight();

		setLocation((contWidth - 400) / 2, (contHeidth - 200) / 2);

		setTitle(system + " - " + fileName);
		setClosable(true);
		setIconifiable(true);
		setResizable(false);

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

		menuBar = new JMenuBar();
		btNovo = new JButton(new ImageIcon((URL) getClass().getResource(
				"/imagem/novo.png")));
		btEditar = new JButton(new ImageIcon((URL) getClass().getResource(
				"/imagem/editar.gif")));
		btProcurar = new JButton(new ImageIcon((URL) getClass().getResource(
				"/imagem/procurar.png")));
		btSalvar = new JButton(new ImageIcon((URL) getClass().getResource(
				"/imagem/salvar.png")));
		btCancelar = new JButton(new ImageIcon((URL) getClass().getResource(
				"/imagem/cancelar.png")));
		btRemover = new JButton(new ImageIcon((URL) getClass().getResource(
				"/imagem/remover.png")));

		menuBar.add(btNovo);
		menuBar.add(btEditar);
		menuBar.add(btProcurar);
		menuBar.add(btSalvar);
		menuBar.add(btCancelar);
		menuBar.add(btRemover);

		setJMenuBar(menuBar);

		btSalvar.setEnabled(false);
		btCancelar.setEnabled(false);

		btNovo.setToolTipText("Novo");
		btEditar.setToolTipText("Editar");
		btProcurar.setToolTipText("Pesquisar");
		btCancelar.setToolTipText("Cancelar");
		btSalvar.setToolTipText("Salvar");
		btRemover.setToolTipText("Remover");

		panel = new JPanel(null);

		lblNome = new JLabel("Nome");
		lblDescricao = new JLabel("Descrição");
		txtNome = new JTextField();
		txtDescricao = new JTextField();

		lblNome.setBounds(15, 25, 50, 20);
		lblDescricao.setBounds(15, 55, 60, 20);
		txtNome.setBounds(80, 25, 240, 20);
		txtDescricao.setBounds(80, 55, 240, 20);

		txtDescricao.setEditable(false);

		panel.add(lblNome);
		panel.add(lblDescricao);
		panel.add(txtNome);
		panel.add(txtDescricao);

		window = getContentPane();

		window.setLayout(null);

		panel.setBounds(20, 20, 355, 105);
		panel.setBorder(BorderFactory.createEtchedBorder());

		window.add(panel);

		btNovo.addActionListener(this);
		btEditar.addActionListener(this);
		btProcurar.addActionListener(this);
		btCancelar.addActionListener(this);
		btSalvar.addActionListener(this);
		btRemover.addActionListener(this);
		txtNome.addActionListener(this);
		txtDescricao.addActionListener(this);

		setVisible(true);

	}

	private void lockComponents(boolean lock, boolean clear) {

		btNovo.setEnabled(!lock);
		btEditar.setEnabled(!lock);
		btProcurar.setEnabled(!lock);
		btSalvar.setEnabled(lock);
		btCancelar.setEnabled(lock);
		btRemover.setEnabled(!lock);

		txtDescricao.setEditable(lock);

		if (clear) {

			txtNome.setEditable(!lock);
			txtNome.setText("");
			txtDescricao.setText("");

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btNovo) {

			lockComponents(true, false);
			txtDescricao.setText("");

		} else if (e.getSource() == btEditar) {

			PropertiesDAO dao = new PropertiesDAO(system, fileName);

			if (!txtNome.getText().trim().equals("")) {

				editMode = true;

				lockComponents(true, false);
				txtNome.setEditable(false);

			}

		} else if (e.getSource() == btProcurar) {

			new IFramePesq(this, system, fileName, threadLock);

		} else if (e.getSource() == btSalvar) {

			threadLock.suspend();
			threadLock.unlock();

			PropertiesDAO dao = new PropertiesDAO(system, fileName);

			if (!editMode) {

				if (txtNome.getText().trim().equals("")
						|| txtDescricao.getText().trim().equals("")) {

					threadLock.resume();
					
					JOptionPane.showMessageDialog(null,
							"Digite as informações!", "Atenção",
							JOptionPane.WARNING_MESSAGE);

				} else {

					if (dao.propertyExists(txtNome.getText().trim())) {

						int result = dao.addProperty(txtNome.getText().trim(),
								txtDescricao.getText().trim());

						if (result == 0) {

							lockComponents(false, true);

						} else if (result == -1) {

							threadLock.resume();
							
							JOptionPane.showMessageDialog(null,
									"Erro ao inserir registro!", "Erro",
									JOptionPane.ERROR_MESSAGE);

						} else {

							threadLock.resume();
							
							JOptionPane
									.showMessageDialog(
											null,
											"Propriedade já existente no banco de dados!",
											"Atenção",
											JOptionPane.WARNING_MESSAGE);

						}

					} else {

						threadLock.resume();
						
						JOptionPane.showMessageDialog(null,
								"Propriedade inexistente no arquivo!",
								"Atenção", JOptionPane.WARNING_MESSAGE);

					}

				}

			} else {

				editMode = false;

				if (dao.propertyExists(txtNome.getText().trim())
						&& dao.propertyExistsInDB(txtNome.getText().trim())) {

					if (txtDescricao.getText().trim().equals("")) {

						threadLock.resume();
						
						JOptionPane.showMessageDialog(null,
								"Digite as informações!", "Atenção",
								JOptionPane.WARNING_MESSAGE);

					} else {

						if (dao.updateDescription(txtNome.getText().trim(),
								txtDescricao.getText())) {

							lockComponents(false, true);

						} else {

							threadLock.resume();
							
							JOptionPane.showMessageDialog(null,
									"Erro ao inserir registro!", "Erro",
									JOptionPane.ERROR_MESSAGE);

						}

					}

				} else {

					threadLock.resume();
					
					JOptionPane
							.showMessageDialog(
									null,
									"Propriedade inexistente no arquivo ou no banco de dados!",
									"Atenção", JOptionPane.WARNING_MESSAGE);

				}

			}

			threadLock.resume();

		} else if (e.getSource() == btCancelar) {

			lockComponents(false, true);

		} else if (e.getSource() == btRemover) {

			threadLock.suspend();
			threadLock.unlock();

			PropertiesDAO dao = new PropertiesDAO(system, fileName);

			if (txtNome.getText().trim().equals("")) {

				JOptionPane.showMessageDialog(null,
						"Digite o nome da propriedade!", "Atenção",
						JOptionPane.WARNING_MESSAGE);

			} else {

				if (dao.propertyExists(txtNome.getText().trim())
						&& dao.propertyExistsInDB(txtNome.getText().trim())) {

					int result = dao.removeProperty(txtNome.getText().trim());

					if (result == 0) {

						lockComponents(false, true);

					} else if (result == -1) {

						JOptionPane.showMessageDialog(null,
								"Erro ao remover registro!", "Erro",
								JOptionPane.ERROR_MESSAGE);

					} else {

						JOptionPane.showMessageDialog(null,
								"Propriedade inexistente no banco de dados!",
								"Atenção", JOptionPane.WARNING_MESSAGE);

					}

				} else {

					JOptionPane
							.showMessageDialog(
									null,
									"Propriedade inexistente no arquivo ou no banco de dados!",
									"Atenção", JOptionPane.WARNING_MESSAGE);

				}

			}

			threadLock.resume();

		} else if (e.getSource() == txtNome) {

			if (!txtNome.getText().trim().equals("")) {

				threadLock.suspend();
				threadLock.unlock();

				PropertiesDAO dao = new PropertiesDAO(system, fileName);

				String[] aux = dao.getProperty(txtNome.getText().trim(), 1);

				if (aux != null) {

					txtDescricao.setText(aux[1]);
					threadLock.resume();

				} else {

					threadLock.resume();

					JOptionPane.showMessageDialog(null,
							"Propriedade inexistente no banco de dados!",
							"Atenção", JOptionPane.WARNING_MESSAGE);

					txtDescricao.setText("");

				}

			}

		} else if (e.getSource() == txtDescricao) {

		}

	}

	public void setText(String name, String description) {

		txtNome.setText(name);
		txtDescricao.setText(description);

	}

	public void setFile(File file) {

		this.file = file;
		threadLock = new ThreadReadOnly(this.file);
		threadLock.start();

	}

	public File getFile() {

		return this.file;

	}

	public ThreadReadOnly getThread() {

		return this.threadLock;

	}

}
