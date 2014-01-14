package br.com.gz.editor.config;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import br.com.gz.editor.about.AboutFrame;
import br.com.gz.editor.about.HelpFrame;
import br.com.gz.editor.about.VersionNotes;
import br.com.gz.editor.config.internal.frame.IFrameConfig;
import br.com.gz.editor.config.internal.frame.IFrameDefDir;
import br.com.gz.editor.dao.InternalDAO;
import br.com.gz.editor.main.FlexEditor;
import br.com.gz.editor.util.MenuScroller;

public class EditorConfig extends JFrame implements ActionListener {

	// Mudar diretório
	private JMenuItem configMenuItemMudarDiretorio;
	// ---------------------------------

	// Sobre
	private JMenu menuAjuda;
	private JMenuItem menuItemAjuda;
	private JMenuItem menuItemSobre;
	// -------------------------------

	// Sair
	private JMenu menuSair;
	private JMenuItem menuItemSair;
	// *********************************

	private JLabel lblBanner;

	private JDesktopPane desktopPane;

	public EditorConfig(boolean showNotes) {

		setSize(1024, 768);
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/imagem/icone.gif")));
		setTitle("FlexEditor - Versão " + InternalDAO.getAppVersion()
				+ " GZ Sistemas - Iniciado em "
				+ new InternalDAO().getDefaultDirectory());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int heigth = screenSize.height;
		int contWidth = getWidth();
		int contHeidth = getHeight();

		setLocation((width - contWidth) / 2, (heigth - contHeidth) / 2);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {

				if (JOptionPane.showConfirmDialog(null,
						"Deseja sair do aplicativo?", "Confirmação",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					System.exit(0);

				}

			}

		});

		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {

				lblBanner.setBounds(getWidth() - 256, getHeight() - 330, 256,
						256);

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});

		setJMenuBar(setMenuBar());

		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(0, 85, 129));

		lblBanner = new JLabel(new ImageIcon((URL) getClass().getResource(
				"/imagem/java.png")));

		desktopPane.setLayout(null);

		lblBanner.setBounds(getWidth() - 256, getHeight() - 330, 256, 256);

		desktopPane.add(lblBanner);

		add(desktopPane);

		setVisible(true);
		
		if(showNotes){
			
			new VersionNotes().showNotes();
			
		}

	}

	private JMenuBar setMenuBar() {

		String path = new InternalDAO().getDefaultDirectory();

		File pathFile = new File(path);

		JMenuBar menuBar = new JMenuBar();

		JMenu menuEditar = new JMenu("Configurar");

		menuEditar.setMnemonic('C');

		menuBar.add(menuEditar);

		if (pathFile.isDirectory()) {

			File[] systems = pathFile.listFiles();

			LIST_SYSTEMS: for (final File systemFile : systems) {

				if (systemFile.isDirectory()) {

					if (FlexEditor.isValidSystemName(systemFile.getName())) {

						boolean propertiesFounded = false;

						JMenu menu = new JMenu(
								FlexEditor.systemToCamelCase(systemFile
										.getName()));

						if (systemFile.getName().equals("flexpdv")
								|| systemFile.getName().equals("flexpdvnf")) {

							new MenuScroller(menu).setScrollCount(20);

						}

						menu.setRolloverEnabled(true);

						File[] systemContents = systemFile.listFiles();

						LIST_SYSTEM_CONTENTS: for (File systemContent : systemContents) {

							if (systemContent.getName().equals("properties")) {

								propertiesFounded = true;

								File[] propertyFiles = systemContent
										.listFiles();

								LIST_PROPERTY_FILES: for (final File pFile : propertyFiles) {

									if(!pFile.isFile() || !pFile.getName().endsWith("properties"))
										continue;
									
									JMenuItem item = new JMenuItem(
											pFile.getName()
													.substring(
															0,
															pFile.getName()
																	.length() - 11));

									item.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent arg0) {

											if (addIFrame(new IFrameConfig(
													desktopPane,
													FlexEditor
															.systemToCamelCase(systemFile
																	.getName()),
													pFile.getName()
															.substring(
																	0,
																	pFile.getName()
																			.length() - 11))) == null) {

												IFrameConfig iFrame = new IFrameConfig(
														desktopPane,
														FlexEditor
																.systemToCamelCase(systemFile
																		.getName()),
														pFile.getName()
																.substring(
																		0,
																		pFile.getName()
																				.length() - 11));

												iFrame.setFile(pFile);

												desktopPane.add(iFrame);

												try {
													iFrame.setSelected(true);
												} catch (PropertyVetoException e1) {
													// TODO Auto-generated
													// catch block
													e1.printStackTrace();
												}

											} else {

												try {

													IFrameConfig i = addIFrame(new IFrameConfig(
															desktopPane,
															FlexEditor
																	.systemToCamelCase(systemFile
																			.getName()),
															pFile.getName()
																	.substring(
																			0,
																			pFile.getName()
																					.length() - 11)));

													if (i.isIcon()) {
														i.setIcon(false);
													}

													i.setSelected(true);

												} catch (PropertyVetoException e1) {
													// TODO Auto-generated
													// catch block
													e1.printStackTrace();
												}

											}

										}

									});

									menu.add(item);

								}

							}

						}

						if (propertiesFounded) {

							menuEditar.add(menu);

						}

					}

				}

			}

		}

		configMenuItemMudarDiretorio = new JMenuItem("Mudar diretório");

		menuAjuda = new JMenu("Ajuda");
		menuAjuda.setMnemonic('A');
		menuItemAjuda = new JMenuItem("Ajuda");
		menuItemAjuda.setMnemonic('A');
		menuItemSobre = new JMenuItem("Sobre o FlexEditor...");
		menuItemSobre.setMnemonic('o');

		menuSair = new JMenu("Sair");
		menuSair.setMnemonic('S');
		menuItemSair = new JMenuItem("Sair do sistema");
		menuItemSair.setMnemonic('S');

		menuEditar.addSeparator();
		menuEditar.add(configMenuItemMudarDiretorio);

		menuAjuda.add(menuItemAjuda);
		menuAjuda.add(menuItemSobre);

		menuSair.add(menuItemSair);

		configMenuItemMudarDiretorio.addActionListener(this);
		configMenuItemMudarDiretorio.setToolTipText("Atual: "
				+ new InternalDAO().getDefaultDirectory());

		menuItemAjuda.addActionListener(this);
		menuItemSobre.addActionListener(this);

		menuItemSair.addActionListener(this);

		menuBar.add(menuAjuda);
		menuBar.add(menuSair);

		return menuBar;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == configMenuItemMudarDiretorio) {

			if (getIFrameDefDir() == null) {

				IFrameDefDir iDef = IFrameDefDir.getInstance(this, desktopPane,
						false);

				desktopPane.add(iDef);

				iDef.setVisible(true);

				try {

					getIFrameDefDir().setSelected(true);

				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {

				try {

					if (getIFrameDefDir().isIcon()) {

						getIFrameDefDir().setIcon(false);

					}

					getIFrameDefDir().setSelected(true);

				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		} else if (e.getSource() == menuItemAjuda) {

			HelpFrame.getInstance().setVisible(true);

		} else if (e.getSource() == menuItemSobre) {

			AboutFrame.getInstance().setVisible(true);

		} else if (e.getSource() == menuItemSair) {

			if (JOptionPane.showConfirmDialog(null,
					"Deseja sair do aplicativo?", "Confirmação",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				System.exit(0);

			}

		}

	}

	private IFrameDefDir getIFrameDefDir() {

		JInternalFrame[] iFrames = desktopPane.getAllFrames();

		for (int i = 0; i < iFrames.length; i++) {

			if (iFrames[i] instanceof IFrameDefDir) {

				return (IFrameDefDir) iFrames[i];

			}

		}

		return null;

	}

	private IFrameConfig addIFrame(JInternalFrame frame) {

		JInternalFrame[] iFrames = desktopPane.getAllFrames();

		for (int i = 0; i < iFrames.length; i++) {

			if (frame.getTitle().equals(iFrames[i].getTitle())) {

				return (IFrameConfig) iFrames[i];

			}

		}

		return null;

	}

}
