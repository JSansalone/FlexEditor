package br.com.gz.editor.profile;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;

import br.com.gz.editor.dao.InternalDAO;
import br.com.gz.editor.desktop.EditorDesktop;

public class ProfileFrame extends JFrame implements ActionListener {

	private static ProfileFrame instance;

	private JMenuBar menuBar;
	private JButton btNovo;
	private JButton btEditar;
	private JButton btProcurar;
	private JButton btSalvar;
	private JButton btCancelar;
	private JButton btRemover;

	private Container window;

	private JPanel pnSistema;
	private JPanel pnFlexBridge;
	private JPanel pnFlexFatur;
	private JPanel pnFlexPDV;
	private JPanel pnMercoBI;

	private JComboBox<String> cmbSistema;
	private JComboBox<String> cmbTipoBanco;

	private JTextField txtCodigo;
	private JTextField txtNomeConf;
	private JTextField txtIPFlexDoor;
	private JTextField txtIPFlexBridge;
	private JTextField txtSistemaNomeDoBanco;
	private JTextField txtSistemaIPBanco;
	private JTextField txtFlexBridgeNomeDoBanco;
	private JTextField txtFlexBridgeIPBanco;
	private JTextField txtFlexFaturNomeDoBanco;
	private JTextField txtFlexFaturIPBanco;
	private JTextField txtFlexPDVNomeDoBanco;
	private JTextField txtFlexPDVIPBanco;
	private JTextField txtMercoBINomeDoBanco;
	private JTextField txtMercoBIIPBanco;

	private JLabel lblSistemaPrincipal;

	private String path;

	private boolean editMode = false;

	private EditorDesktop owner;

	private ProfileFrame(EditorDesktop owner, String path) {

		this.owner = owner;
		this.path = path;

		setSize(785, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("FlexEditor - Perfis de configuração");
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/imagem/icone.gif")));
		setResizable(false);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int heigth = screenSize.height;
		int contWidth = getWidth();
		int contHeidth = getHeight();

		setLocation((width - contWidth) / 2, (heigth - contHeidth) / 2);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {

				ProfilePesq.getInstance(instance).close();
				instance = null;
				dispose();

			}

		});

		setJMenuBar(createMenuBar());

		window = getContentPane();
		window.setLayout(null);

		window.add(createMainPanel());
		window.add(createPanelSistema());
		window.add(createPanelFlexBridge());
		window.add(createPanelFlexFatur());
		window.add(createPanelFlexPDV());
		window.add(createPanelMercoBI());

		lblSistemaPrincipal = new JLabel((String) cmbSistema.getSelectedItem());
		lblSistemaPrincipal.setBounds(395, 0, 70, 20);
		lblSistemaPrincipal.setFont(new Font(null, Font.BOLD, 11));
		JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
		sep1.setBounds(470, 10, 290, 5);

		JLabel lblNomeFlexBridge = new JLabel("Banco de dados do FlexBridge");
		lblNomeFlexBridge.setBounds(10, 220, 190, 20);
		lblNomeFlexBridge.setFont(new Font(null, Font.BOLD, 11));
		JSeparator sep2 = new JSeparator(SwingConstants.HORIZONTAL);
		sep2.setBounds(195, 230, 185, 5);

		JLabel lblNFE = new JLabel("Banco de dados da NF-e");
		lblNFE.setBounds(395, 110, 190, 20);
		lblNFE.setFont(new Font(null, Font.BOLD, 11));
		JSeparator sep3 = new JSeparator(SwingConstants.HORIZONTAL);
		sep3.setBounds(530, 120, 230, 5);

		JLabel lblPDV = new JLabel("Banco de dados do FlexPDV");
		lblPDV.setBounds(10, 330, 190, 20);
		lblPDV.setFont(new Font(null, Font.BOLD, 11));
		JSeparator sep4 = new JSeparator(SwingConstants.HORIZONTAL);
		sep4.setBounds(175, 340, 585, 5);

		JLabel lblMercoBI = new JLabel("Banco de dados do MercoBI");
		lblMercoBI.setBounds(395, 220, 190, 20);
		lblMercoBI.setFont(new Font(null, Font.BOLD, 11));
		JSeparator sep5 = new JSeparator(SwingConstants.HORIZONTAL);
		sep5.setBounds(570, 230, 190, 5);

		window.add(lblSistemaPrincipal);
		window.add(sep1);
		window.add(lblNomeFlexBridge);
		window.add(sep2);
		window.add(lblNFE);
		window.add(sep3);
		window.add(lblPDV);
		window.add(sep4);
		window.add(lblMercoBI);
		window.add(sep5);

		cmbSistema.addActionListener(this);

		lockComponents(false, false);

		btEditar.setEnabled(false);
		btRemover.setEnabled(false);

	}

	public static ProfileFrame getInstance(EditorDesktop owner, String path) {

		if (instance == null) {

			instance = new ProfileFrame(owner, path);

		}

		return instance;

	}

	private JMenuBar createMenuBar() {

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

		btSalvar.setEnabled(false);
		btCancelar.setEnabled(false);

		btNovo.setToolTipText("Novo");
		btEditar.setToolTipText("Editar");
		btProcurar.setToolTipText("Pesquisar");
		btCancelar.setToolTipText("Cancelar");
		btSalvar.setToolTipText("Salvar");
		btRemover.setToolTipText("Remover");

		btNovo.addActionListener(this);
		btEditar.addActionListener(this);
		btProcurar.addActionListener(this);
		btCancelar.addActionListener(this);
		btSalvar.addActionListener(this);
		btRemover.addActionListener(this);

		return this.menuBar;

	}

	private void lockComponents(boolean lock, boolean clear) {

		btNovo.setEnabled(!lock);
		btEditar.setEnabled(!lock);
		btProcurar.setEnabled(!lock);
		btSalvar.setEnabled(lock);
		btCancelar.setEnabled(lock);
		btRemover.setEnabled(!lock);

		txtCodigo.setEditable(!lock);
		txtNomeConf.setEditable(lock);
		cmbSistema.setEnabled(lock);
		cmbTipoBanco.setEnabled(lock);
		txtSistemaNomeDoBanco.setEditable(lock);
		txtSistemaIPBanco.setEditable(lock);
		txtFlexBridgeIPBanco.setEditable(lock);
		txtFlexBridgeNomeDoBanco.setEditable(lock);
		txtFlexFaturIPBanco.setEditable(lock);
		txtFlexFaturNomeDoBanco.setEditable(lock);
		txtFlexPDVIPBanco.setEditable(lock);
		txtFlexPDVNomeDoBanco.setEditable(lock);
		txtIPFlexDoor.setEditable(lock);
		txtIPFlexBridge.setEditable(lock);
		txtMercoBINomeDoBanco.setEditable(lock);
		txtMercoBIIPBanco.setEditable(lock);

		if (clear) {

			txtNomeConf.setText("");
			txtSistemaNomeDoBanco.setText("");
			txtSistemaIPBanco.setText("");
			txtFlexBridgeIPBanco.setText("");
			txtFlexBridgeNomeDoBanco.setText("");
			txtFlexFaturIPBanco.setText("");
			txtFlexFaturNomeDoBanco.setText("");
			txtFlexPDVIPBanco.setText("");
			txtFlexPDVNomeDoBanco.setText("");
			txtIPFlexDoor.setText("");
			txtIPFlexBridge.setText("");
			txtMercoBIIPBanco.setText("");
			txtMercoBINomeDoBanco.setText("");

		}

	}

	private JPanel createMainPanel() {

		JPanel pnPrincipal = new JPanel(null);
		pnPrincipal.setBounds(10, 20, 375, 190);
		pnPrincipal.setBorder(BorderFactory.createEtchedBorder());

		JLabel lblCodigo = new JLabel("Código");
		JLabel lblNomeConf = new JLabel("Nome da configuração");
		JLabel lblSistema = new JLabel("Sistema principal");
		JLabel lblTipoBanco = new JLabel("Tipo de banco de dados");
		JLabel lblIPDoor = new JLabel("IP do FlexDoor");
		JLabel lblIPBridge = new JLabel("IP do FlexBridge");
		txtCodigo = new JTextField();
		txtNomeConf = new JTextField();
		cmbSistema = new JComboBox<String>();
		cmbTipoBanco = new JComboBox<String>();
		txtIPFlexDoor = new JTextField();
		txtIPFlexBridge = new JTextField();

		lblCodigo.setBounds(10, 10, 160, 20);
		lblNomeConf.setBounds(10, 40, 160, 20);
		lblSistema.setBounds(10, 70, 160, 20);
		lblTipoBanco.setBounds(10, 100, 160, 20);
		lblIPDoor.setBounds(10, 130, 160, 20);
		lblIPBridge.setBounds(10, 160, 160, 20);
		txtCodigo.setBounds(170, 10, 25, 20);
		txtNomeConf.setBounds(170, 40, 190, 20);
		cmbSistema.setBounds(170, 70, 140, 20);
		cmbTipoBanco.setBounds(170, 100, 140, 20);
		txtIPFlexDoor.setBounds(170, 130, 140, 20);
		txtIPFlexBridge.setBounds(170, 160, 140, 20);

		pnPrincipal.add(lblCodigo);
		pnPrincipal.add(lblNomeConf);
		pnPrincipal.add(lblSistema);
		pnPrincipal.add(lblTipoBanco);
		pnPrincipal.add(lblIPDoor);
		pnPrincipal.add(lblIPBridge);
		pnPrincipal.add(txtCodigo);
		pnPrincipal.add(txtNomeConf);
		pnPrincipal.add(cmbSistema);
		pnPrincipal.add(cmbTipoBanco);
		pnPrincipal.add(txtIPFlexDoor);
		pnPrincipal.add(txtIPFlexBridge);

		cmbSistema.addItem("MercoFlex");
		cmbSistema.addItem("FlexConcent");

		cmbTipoBanco.addItem("MySQL");
		cmbTipoBanco.addItem("SQL Server");
		cmbTipoBanco.addItem("Oracle");

		txtCodigo.addActionListener(this);

		txtCodigo.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {

				btEditar.setEnabled(!txtCodigo.getText().equals(""));
				btRemover.setEnabled(!txtCodigo.getText().equals(""));

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		return pnPrincipal;

	}

	private JPanel createPanelSistema() {

		pnSistema = new JPanel(null);
		pnSistema.setBounds(395, 20, 375, 80);
		pnSistema.setBorder(BorderFactory.createEtchedBorder());

		JLabel lblSistemaNomeDoBanco = new JLabel("Nome do banco de dados");
		JLabel lblSistemaIPBanco = new JLabel("IP do banco de dados");
		txtSistemaNomeDoBanco = new JTextField();
		txtSistemaIPBanco = new JTextField();

		lblSistemaNomeDoBanco.setBounds(10, 20, 160, 20);
		lblSistemaIPBanco.setBounds(10, 50, 160, 20);
		txtSistemaNomeDoBanco.setBounds(170, 20, 140, 20);
		txtSistemaIPBanco.setBounds(170, 50, 140, 20);

		pnSistema.add(lblSistemaNomeDoBanco);
		pnSistema.add(lblSistemaIPBanco);
		pnSistema.add(txtSistemaNomeDoBanco);
		pnSistema.add(txtSistemaIPBanco);

		return this.pnSistema;

	}

	private JPanel createPanelFlexBridge() {

		pnFlexBridge = new JPanel(null);
		pnFlexBridge.setBounds(10, 240, 375, 80);
		pnFlexBridge.setBorder(BorderFactory.createEtchedBorder());

		JLabel lblFlexBridgeNomeDoBanco = new JLabel("Nome do banco de dados");
		JLabel lblFlexBridgeIPBanco = new JLabel("IP do banco de dados");
		txtFlexBridgeNomeDoBanco = new JTextField();
		txtFlexBridgeIPBanco = new JTextField();

		lblFlexBridgeNomeDoBanco.setBounds(10, 20, 160, 20);
		lblFlexBridgeIPBanco.setBounds(10, 50, 160, 20);
		txtFlexBridgeNomeDoBanco.setBounds(170, 20, 140, 20);
		txtFlexBridgeIPBanco.setBounds(170, 50, 140, 20);

		pnFlexBridge.add(lblFlexBridgeNomeDoBanco);
		pnFlexBridge.add(lblFlexBridgeIPBanco);
		pnFlexBridge.add(txtFlexBridgeNomeDoBanco);
		pnFlexBridge.add(txtFlexBridgeIPBanco);

		return this.pnFlexBridge;

	}

	private JPanel createPanelFlexFatur() {

		pnFlexFatur = new JPanel(null);
		pnFlexFatur.setBounds(395, 130, 375, 80);
		pnFlexFatur.setBorder(BorderFactory.createEtchedBorder());

		JLabel lblFlexFaturNomeDoBanco = new JLabel("Nome do banco de dados");
		JLabel lblFlexFaturIPBanco = new JLabel("IP do banco de dados");
		txtFlexFaturNomeDoBanco = new JTextField();
		txtFlexFaturIPBanco = new JTextField();

		lblFlexFaturNomeDoBanco.setBounds(10, 20, 160, 20);
		lblFlexFaturIPBanco.setBounds(10, 50, 160, 20);
		txtFlexFaturNomeDoBanco.setBounds(170, 20, 140, 20);
		txtFlexFaturIPBanco.setBounds(170, 50, 140, 20);

		pnFlexFatur.add(lblFlexFaturNomeDoBanco);
		pnFlexFatur.add(lblFlexFaturIPBanco);
		pnFlexFatur.add(txtFlexFaturNomeDoBanco);
		pnFlexFatur.add(txtFlexFaturIPBanco);

		return this.pnFlexFatur;

	}

	private JPanel createPanelFlexPDV() {

		pnFlexPDV = new JPanel(null);
		pnFlexPDV.setBounds(10, 350, 760, 80);
		pnFlexPDV.setBorder(BorderFactory.createEtchedBorder());

		JLabel lblFlexPDVNomeDoBanco = new JLabel("Nome do banco de dados");
		JLabel lblFlexPDVIPBanco = new JLabel("IP do banco de dados");
		txtFlexPDVNomeDoBanco = new JTextField();
		txtFlexPDVIPBanco = new JTextField();

		lblFlexPDVNomeDoBanco.setBounds(10, 20, 160, 20);
		lblFlexPDVIPBanco.setBounds(10, 50, 160, 20);
		txtFlexPDVNomeDoBanco.setBounds(170, 20, 140, 20);
		txtFlexPDVIPBanco.setBounds(170, 50, 140, 20);

		pnFlexPDV.add(lblFlexPDVNomeDoBanco);
		pnFlexPDV.add(lblFlexPDVIPBanco);
		pnFlexPDV.add(txtFlexPDVNomeDoBanco);
		pnFlexPDV.add(txtFlexPDVIPBanco);

		return this.pnFlexPDV;

	}

	private JPanel createPanelMercoBI() {

		pnMercoBI = new JPanel(null);
		pnMercoBI.setBounds(395, 240, 375, 80);
		pnMercoBI.setBorder(BorderFactory.createEtchedBorder());

		JLabel lblMercoBINomeDoBanco = new JLabel("Nome do banco de dados");
		JLabel lblMercoBIIPBanco = new JLabel("IP do banco de dados");
		txtMercoBINomeDoBanco = new JTextField();
		txtMercoBIIPBanco = new JTextField();

		lblMercoBINomeDoBanco.setBounds(10, 20, 160, 20);
		lblMercoBIIPBanco.setBounds(10, 50, 160, 20);
		txtMercoBINomeDoBanco.setBounds(170, 20, 140, 20);
		txtMercoBIIPBanco.setBounds(170, 50, 140, 20);

		pnMercoBI.add(lblMercoBINomeDoBanco);
		pnMercoBI.add(lblMercoBIIPBanco);
		pnMercoBI.add(txtMercoBINomeDoBanco);
		pnMercoBI.add(txtMercoBIIPBanco);

		return this.pnMercoBI;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btNovo) {

			int nCod = new InternalDAO().getMaxCodeProfile();

			if (nCod >= 0) {

				nCod++;

				editMode = false;

				lockComponents(true, true);

				txtCodigo.setText(new Integer(nCod).toString());

			}

		} else if (e.getSource() == btEditar) {

			if (loadFields()) {

				editMode = true;

				lockComponents(true, false);

			} else {

				JOptionPane.showMessageDialog(null, "Perfil não cadastrado!",
						"Aviso", JOptionPane.WARNING_MESSAGE);

				btEditar.setEnabled(false);
				btRemover.setEnabled(false);

				txtCodigo.setText("");

			}

		} else if (e.getSource() == btProcurar) {

			ProfilePesq.getInstance(this).setVisible(true);

		} else if (e.getSource() == btSalvar) {

			save();

			lockComponents(false, true);

			editMode = false;

			owner.updateCustomMenu();

		} else if (e.getSource() == btCancelar) {

			lockComponents(false, true);

			editMode = false;

			btEditar.setEnabled(false);
			btRemover.setEnabled(false);

			txtCodigo.setText("");

			owner.updateCustomMenu();

		} else if (e.getSource() == btRemover) {

			try {

				int n = Integer.parseInt(txtCodigo.getText());

				new InternalDAO().removeProfile(n);

				lockComponents(false, true);

				editMode = false;

			} catch (Exception e2) {

			}

			owner.updateCustomMenu();

		} else if (e.getSource() == cmbSistema) {

			lblSistemaPrincipal.setText((String) cmbSistema.getSelectedItem());

		} else if (e.getSource() == txtCodigo) {

			try {

				if (!loadFields()) {

					JOptionPane.showMessageDialog(null,
							"Perfil não cadastrado!", "Aviso",
							JOptionPane.WARNING_MESSAGE);

					btEditar.setEnabled(false);
					btRemover.setEnabled(false);

					txtCodigo.setText("");

				} else {

					lockComponents(false, false);

					btEditar.setEnabled(true);
					btRemover.setEnabled(true);

				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	private boolean loadFields() {

		int n = Integer.parseInt(txtCodigo.getText());

		Profile config = new InternalDAO().getProfile(n);

		if (config != null) {

			txtNomeConf.setText(config.getNomeConf());
			String aux = config.getSistema();
			if (aux.equals("mercoflex")) {
				cmbSistema.setSelectedItem("MercoFlex");
			} else {
				cmbSistema.setSelectedItem("FlexConcent");
			}
			aux = config.getTipoBanco();
			if (aux.equals("mysql")) {
				cmbTipoBanco.setSelectedItem("MySQL");
			} else if (aux.equals("sql server")) {
				cmbTipoBanco.setSelectedItem("SQL Server");
			} else {
				cmbTipoBanco.setSelectedItem("Oracle");
			}
			txtIPFlexDoor.setText(config.getIpDoor());
			txtIPFlexBridge.setText(config.getIpBridge());
			txtSistemaNomeDoBanco.setText(config.getNomeBancoSistema());
			txtSistemaIPBanco.setText(config.getIpBancoSistema());
			txtFlexBridgeNomeDoBanco.setText(config.getNomeBancoBridge());
			txtFlexBridgeIPBanco.setText(config.getIpBancoBridge());
			txtFlexFaturNomeDoBanco.setText(config.getNomeBancoNFE());
			txtFlexFaturIPBanco.setText(config.getIpBancoNFE());
			txtFlexPDVNomeDoBanco.setText(config.getNomeBancoPDV());
			txtFlexPDVIPBanco.setText(config.getIpBancoPDV());
			txtMercoBINomeDoBanco.setText(config.getNomeBancoMercoBI());
			txtMercoBIIPBanco.setText(config.getIpBancoMercoBI());

			return true;

		} else {

			// lockComponents(false, true);

			return false;

		}

	}

	private void save() {

		Profile config = new Profile();

		config.setCodigo(Integer.parseInt(txtCodigo.getText()));
		config.setNomeConf(txtNomeConf.getText());
		config.setSistema((String) cmbSistema.getSelectedItem());
		config.setSistema(config.getSistema().toLowerCase());
		config.setTipoBanco((String) cmbTipoBanco.getSelectedItem());
		config.setTipoBanco(config.getTipoBanco().toLowerCase());
		config.setIpDoor(txtIPFlexDoor.getText());
		config.setIpBridge(txtIPFlexBridge.getText());
		config.setNomeBancoSistema(txtSistemaNomeDoBanco.getText());
		config.setIpBancoSistema(txtSistemaIPBanco.getText());
		config.setNomeBancoBridge(txtFlexBridgeNomeDoBanco.getText());
		config.setIpBancoBridge(txtFlexBridgeIPBanco.getText());
		config.setNomeBancoNFE(txtFlexFaturNomeDoBanco.getText());
		config.setIpBancoNFE(txtFlexFaturIPBanco.getText());
		config.setNomeBancoPDV(txtFlexPDVNomeDoBanco.getText());
		config.setIpBancoPDV(txtFlexPDVIPBanco.getText());
		config.setNomeBancoMercoBI(txtMercoBINomeDoBanco.getText());
		config.setIpBancoMercoBI(txtMercoBIIPBanco.getText());

		if (!editMode) {

			InternalDAO iDao = new InternalDAO();
			iDao.addProfile(config, false);

		} else {

			InternalDAO iDao = new InternalDAO();
			iDao.addProfile(config, true);

		}

	}

	public void getCodigo(int codigo) {

		txtCodigo.setText(new Integer(codigo).toString());

		loadFields();

	}

}
