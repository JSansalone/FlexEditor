package br.com.gz.editor.main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import br.com.gz.editor.config.EditorConfig;
import br.com.gz.editor.desktop.EditorDesktop;

class CreateDB extends Thread {

	private static Connection conn;
	private JFrame frame;
	private Container window;
	private JPanel panel;
	private JLabel label;

	private Timer timer;

	private JProgressBar progressBar;

	private int i = 0;

	private boolean isConfig = false;
	
	private static boolean showNotes = false;

	public CreateDB() {

	}

	public CreateDB(boolean isConfig) {

		this.isConfig = isConfig;

		frame = new JFrame();

		frame.setSize(400, 100);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int heigth = screenSize.height;
		int contWidth = frame.getWidth();
		int contHeidth = frame.getHeight();

		frame.setLocation((width - contWidth) / 2, (heigth - contHeidth) / 2);

		window = frame.getContentPane();

		panel = new JPanel(null);
		panel.setBorder(BorderFactory.createEtchedBorder());

		label = new JLabel("Aguarde...instalando banco de dados...");
		label.setBounds(95, 40, 300, 20);

		progressBar = new JProgressBar(0, 215);
		progressBar.setBounds(10, 70, 380, 20);
		progressBar.setVisible(false);

		window.add(panel);

		panel.add(label);
		panel.add(progressBar);

		frame.setVisible(true);

		createTables();

	}

	public static void createTables() {

		try {

			conn = DriverManager
					.getConnection("jdbc:derby:editordb;create=true");
			PreparedStatement stmt;
			PreparedStatement stmt2 = null;

			stmt = conn
					.prepareStatement("select * from sys.systables where tablename=?");

			stmt.setString(1, "APP_VERSION");
			ResultSet rs = stmt.executeQuery();
			if (!rs.next()) {

				stmt2 = conn
						.prepareStatement("create table app_version(version varchar(20))");
				stmt2.execute();
				stmt2 = conn
						.prepareStatement("insert into app_version values(?)");
				stmt2.setString(1, "1.0.0 - 2012.04.20");
				stmt2.execute();

			} else {

				stmt2 = conn
						.prepareStatement("select version from app_version");
				rs = stmt2.executeQuery();

				rs.next();

				String aux = rs.getString("VERSION");

				if (!aux.equals("1.0.0 - 2012.04.20")) {

					stmt2 = conn
							.prepareStatement("update app_version set version = ?");
					stmt2.setString(1, "1.0.0 - 2012.04.20");
					stmt2.execute();
					
					showNotes = true;

				}

			}

			stmt.setString(1, "PROPRIEDADES");
			rs = stmt.executeQuery();
			if (!rs.next()) {

				stmt2 = conn
						.prepareStatement("create table propriedades(sistema varchar(100), arquivo varchar(500), nome varchar(200), descricao varchar(200))");
				stmt2.execute();

			}

			stmt.setString(1, "DIRETORIO");
			rs = stmt.executeQuery();
			if (!rs.next()) {

				stmt2 = conn
						.prepareStatement("create table diretorio(caminho varchar(2048), pergunte_novamente int)");
				stmt2.execute();

			}

			stmt.setString(1, "PERFIL");
			rs = stmt.executeQuery();
			if (!rs.next()) {

				stmt2 = conn
						.prepareStatement("create table perfil(codigo int, nome varchar(1000), sistema varchar(12), tipo_db varchar(15), ip_door varchar(20), ip_bridge varchar(20), sistema_db varchar(100), ip_db_sistema varchar(20), bridge_db varchar(100), ip_db_bridge varchar(20), nfe_db varchar(100), ip_db_nfe varchar(20), pdv_db varchar(100), ip_db_pdv varchar(20), mercobi_db varchar(100), ip_db_mercobi varchar(20), ftp_movto_ip varchar(256), ftp_movto_diretorio varchar(256), ftp_movto_usuario varchar(256), ftp_movto_senha varchar(256), ftp_carga_ip varchar(256), ftp_carga_diretorio varchar(256), ftp_carga_usuario varchar(256), ftp_carga_senha varchar(256))");
				stmt2.execute();

			}

			stmt.close();
			try {
				stmt2.close();
			} catch (Exception e) {

			}

		} catch (SQLException e) {

			e.printStackTrace();

			JOptionPane.showMessageDialog(null,
					"Erro ao criar o banco de dados!", "Erro",
					JOptionPane.ERROR_MESSAGE);

			System.exit(0);

		}

	}

	private void create() {

		progressBar.setVisible(true);

		try {

			PreparedStatement stmt;

			stmt = conn
					.prepareStatement("insert into propriedades values(?,?,?,?)");

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipDoor");
			stmt.setString(4, "IP do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipBridge");
			stmt.setString(4, "IP do FlexBridge");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "portaDoor");
			stmt.setString(4, "Porta do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "portaBridge");
			stmt.setString(4, "Porta do FlexBridge");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "mercoflex");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdoor");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdoor");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdoor");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdoor");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdoor");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdoor");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdoor");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "sistema");
			stmt.setString(4, "Sistema");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdoor");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipPlug");
			stmt.setString(4, "IP do FlexPlug");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "sistema");
			stmt.setString(4, "Sistema");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipDoor");
			stmt.setString(4, "IP do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbridge");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "sistema");
			stmt.setString(4, "Sistema");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipDoor");
			stmt.setString(4, "IP do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "portaDoor");
			stmt.setString(4, "Porta do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "dbNfe.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "dbNfe.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "dbNfe.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "dbNfe.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "dbNfe.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexfatur");
			stmt.setString(2, "dbNfe.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipDoor");
			stmt.setString(4, "IP do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipBridge");
			stmt.setString(4, "IP do FlexBridge");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "portaDoor");
			stmt.setString(4, "Porta do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "portaBridge");
			stmt.setString(4, "Porta do FlexBridge");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexconcent");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbalcao");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbalcao");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbalcao");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbalcao");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbalcao");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbalcao");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbalcao");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "sistema");
			stmt.setString(4, "Sistema");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbalcao");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipDoor");
			stmt.setString(4, "IP do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexbalcao");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "portaDoor");
			stmt.setString(4, "Porta do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdv");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbMySQL.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbMySQL.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbMySQL.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbMySQL.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbMySQL.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbMySQL.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbOracle.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbOracle.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbOracle.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbOracle.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbOracle.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbOracle.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbSqlServer.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbSqlServer.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbSqlServer.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbSqlServer.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbSqlServer.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexdump");
			stmt.setString(2, "dbSqlServer.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "fleximport");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "pathImportacao");
			stmt.setString(4, "Diretório de importação");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "fleximport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "fleximport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "fleximport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "fleximport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "fleximport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "fleximport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbBridge.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexpdvnf");
			stmt.setString(2, "dbRetaguarda.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexreport");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "sistema");
			stmt.setString(4, "Sistema");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexreport");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipDoor");
			stmt.setString(4, "IP do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexreport");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "portaDoor");
			stmt.setString(4, "Porta do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexreport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "port");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexreport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "tipodb");
			stmt.setString(4, "Tipo de banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexreport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "password");
			stmt.setString(4, "Senha");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexreport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "dbName");
			stmt.setString(4, "Nome do banco");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexreport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "userName");
			stmt.setString(4, "Usuário");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexreport");
			stmt.setString(2, "db.properties");
			stmt.setString(3, "hostName");
			stmt.setString(4, "IP do host");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "automatico");
			stmt.setString(4, "Automatico");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "ipDoor");
			stmt.setString(4, "IP do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "porta");
			stmt.setString(4, "Porta");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "portaDoor");
			stmt.setString(4, "Porta do FlexDoor");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "frame.properties");
			stmt.setString(3, "AtivaESCSaidaForcada");
			stmt.setString(4, "Saída forçada com ESC");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "frame.properties");
			stmt.setString(3, "ForcaFoco");
			stmt.setString(4, "Forçar o foco da janela");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "frame.properties");
			stmt.setString(3, "ForcaModoJanela");
			stmt.setString(4, "Forçar o modo janela");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "frame.properties");
			stmt.setString(3, "ForcaMouseLigado");
			stmt.setString(4, "Forçar o mouse ligado");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "frame.properties");
			stmt.setString(3, "JanelaEm_0_0");
			stmt.setString(4, "Janela no canto superior esquerdo");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "sitef.properties");
			stmt.setString(3, "codigoEmpresaSitef");
			stmt.setString(4, "Codigo da empresa SiTef");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "sitef.properties");
			stmt.setString(3, "ipServidorSitef");
			stmt.setString(4, "IP do servidor SiTef");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "sitef.properties");
			stmt.setString(3, "numeroTerminal");
			stmt.setString(4, "Número do terminal");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "sitef.properties");
			stmt.setString(3, "produtos");
			stmt.setString(4, "Produtos");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "sitef.properties");
			stmt.setString(3, "restricoes");
			stmt.setString(4, "Restricoes");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "arquivoLayout");
			stmt.setString(4, "Arquivo de layout");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "bauds");
			stmt.setString(4, "Bounds");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "bits");
			stmt.setString(4, "Bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "cortaPapel");
			stmt.setString(4, "Corta papel");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "parity");
			stmt.setString(4, "Paridade");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "reset");
			stmt.setString(4, "Reset");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "saida");
			stmt.setString(4, "Saída");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "saltoFinal");
			stmt.setString(4, "Salto final");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "saltoInicial");
			stmt.setString(4, "Salto inicial");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora.properties");
			stmt.setString(3, "stopbits");
			stmt.setString(4, "Stop bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "arquivoLayout");
			stmt.setString(4, "Arquivo de layout");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "bauds");
			stmt.setString(4, "Bounds");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "bits");
			stmt.setString(4, "Bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "cortaPapel");
			stmt.setString(4, "Corta papel");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "parity");
			stmt.setString(4, "Paridade");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "reset");
			stmt.setString(4, "Reset");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "saida");
			stmt.setString(4, "Saída");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "saltoFinal");
			stmt.setString(4, "Salto final");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "saltoInicial");
			stmt.setString(4, "Salto inicial");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_bobina.properties");
			stmt.setString(3, "stopbits");
			stmt.setString(4, "Stop bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "arquivoLayout");
			stmt.setString(4, "Arquivo de layout");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "bauds");
			stmt.setString(4, "Bounds");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "bits");
			stmt.setString(4, "Bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "cortaPapel");
			stmt.setString(4, "Corta papel");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "parity");
			stmt.setString(4, "Paridade");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "reset");
			stmt.setString(4, "Reset");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "saida");
			stmt.setString(4, "Saída");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "saltoFinal");
			stmt.setString(4, "Salto final");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "saltoInicial");
			stmt.setString(4, "Salto inicial");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_custom.properties");
			stmt.setString(3, "stopbits");
			stmt.setString(4, "Stop bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "arquivoLayout");
			stmt.setString(4, "Arquivo de layout");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "bauds");
			stmt.setString(4, "Bounds");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "bits");
			stmt.setString(4, "Bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "cortaPapel");
			stmt.setString(4, "Corta papel");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "parity");
			stmt.setString(4, "Paridade");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "reset");
			stmt.setString(4, "Reset");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "saida");
			stmt.setString(4, "Saída");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "saltoFinal");
			stmt.setString(4, "Salto final");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "saltoInicial");
			stmt.setString(4, "Salto inicial");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_dr_usb.properties");
			stmt.setString(3, "stopbits");
			stmt.setString(4, "Stop bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "arquivoLayout");
			stmt.setString(4, "Arquivo de layout");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "bauds");
			stmt.setString(4, "Bounds");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "bits");
			stmt.setString(4, "Bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "cortaPapel");
			stmt.setString(4, "Corta papel");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "parity");
			stmt.setString(4, "Paridade");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "reset");
			stmt.setString(4, "Reset");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "saida");
			stmt.setString(4, "Saída");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "saltoFinal");
			stmt.setString(4, "Salto final");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "saltoInicial");
			stmt.setString(4, "Salto inicial");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextotem");
			stmt.setString(2, "impressora_padrao.properties");
			stmt.setString(3, "stopbits");
			stmt.setString(4, "Stop bits");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flextransport");
			stmt.setString(2, "config.properties");
			stmt.setString(3, "gravaLog");
			stmt.setString(4, "Grava log");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexmenu");
			stmt.setString(2, "language.properties");
			stmt.setString(3, "charset");
			stmt.setString(4, "Charset");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexmenu");
			stmt.setString(2, "language.properties");
			stmt.setString(3, "country");
			stmt.setString(4, "País");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.setString(1, "flexmenu");
			stmt.setString(2, "language.properties");
			stmt.setString(3, "language");
			stmt.setString(4, "Idioma");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt = conn
					.prepareStatement("insert into diretorio values('c:/GZ',1)");
			stmt.execute();
			i++;
			progressBar.setValue(i);

			stmt.close();
			conn.close();

			frame.dispose();

			JOptionPane.showMessageDialog(null, "Banco de dados instalado!",
					"Concluído", JOptionPane.INFORMATION_MESSAGE);

			if (isConfig) {

				new EditorConfig(showNotes());

			} else {

				new EditorDesktop(showNotes());

			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "i=" + i,
					"Erro", JOptionPane.ERROR_MESSAGE);

			frame.dispose();
			System.exit(0);

		}

	}

	public void run() {

		create();

	}
	
	public static boolean showNotes(){
		return showNotes;
	}

}