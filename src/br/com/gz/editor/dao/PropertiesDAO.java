package br.com.gz.editor.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import br.com.gz.editor.profile.Profile;
import br.com.gz.editor.profile.CurrentProfile;

public class PropertiesDAO {

	private String system;
	private String fileName;
	private Connection conn;
	private String path;

	public PropertiesDAO(String system, String fileName) {

		this.system = system;
		this.fileName = fileName;
		path = new InternalDAO().getDefaultDirectory();

		try {

			conn = DriverManager.getConnection("jdbc:derby:editordb");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public PropertiesDAO() {

		path = new InternalDAO().getDefaultDirectory();

		try {

			conn = DriverManager.getConnection("jdbc:derby:editordb");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int addProperty(String name, String description) {

		if (propertyExists(name) && !propertyExistsInDB(name)) {

			try {

				PreparedStatement stmt = conn
						.prepareStatement("insert into propriedades values(?,?,?,?)");

				stmt.setString(1, system.toLowerCase());
				stmt.setString(2, fileName + ".properties");
				stmt.setString(3, name);
				stmt.setString(4, description);

				stmt.execute();
				stmt.close();

				return 0;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}

		} else {

			return -2;

		}

	}

	public String getPropertyValue(String name) {

		Properties property = new Properties();

		File file = new File(path + "/" + system.toLowerCase() + "/properties/"
				+ fileName + ".properties");

		try {

			FileInputStream fis = new FileInputStream(file);
			property.load(fis);

			String value = property.getProperty(name);

			fis.close();

			return value;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public boolean setPropertyValue(String name, String value) {

		Properties property = new Properties();

		try {

			File file = new File(path + "/" + system.toLowerCase()
					+ "/properties/" + fileName + ".properties");

			FileInputStream fis = new FileInputStream(file);
			property.load(fis);

			property.setProperty(name, value);

			FileOutputStream fos = new FileOutputStream(file);
			property.store(fos, "");
			fos.close();
			fis.close();

			return true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean setPropertyValue(String path, String pFile, String name,
			String value) {

		Properties property = new Properties();

		try {

			File file = new File(path + "/" + pFile + ".properties");

			if (file.exists()) {

				file.setWritable(true);

				FileInputStream fis = new FileInputStream(file);
				property.load(fis);

				property.setProperty(name, value);

				FileOutputStream fos = new FileOutputStream(file);
				property.store(fos, "");
				fos.close();
				fis.close();

			}

			return true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public int removeProperty(String name) {

		if (propertyExists(name)) {

			try {

				PreparedStatement stmt = conn
						.prepareStatement("delete from propriedades where sistema = ? and arquivo = ? and nome = ?");

				stmt.setString(1, system.toLowerCase());
				stmt.setString(2, fileName + ".properties");
				stmt.setString(3, name);

				stmt.execute();
				stmt.close();

				return 0;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}

		} else {

			return -2;

		}

	}

	public boolean haveProperty() {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("select * from propriedades where sistema = ? and arquivo = ?");

			stmt.setString(1, system.toLowerCase());
			stmt.setString(2, fileName + ".properties");

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				rs.close();
				return true;

			}

			return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean propertyExists(String name) {

		Properties property = new Properties();

		try {

			File file = new File(path + "/" + system.toLowerCase()
					+ "/properties/" + fileName + ".properties");

			FileInputStream fis = new FileInputStream(file);
			property.load(fis);

			return property.containsKey(name);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean updateDescription(String name, String description) {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("update propriedades set descricao = ? where sistema = ? and arquivo = ? and nome = ?");

			stmt.setString(1, description);
			stmt.setString(2, system.toLowerCase());
			stmt.setString(3, fileName + ".properties");
			stmt.setString(4, name);

			stmt.execute();
			stmt.close();

			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean propertyExistsInDB(String name) {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("select * from propriedades where sistema = ? and arquivo = ? and nome = ?");

			stmt.setString(1, system.toLowerCase());
			stmt.setString(2, fileName + ".properties");
			stmt.setString(3, name);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				rs.close();
				return true;

			}

			return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean fileExists() {

		File file = new File(path + "/" + system.toLowerCase() + "/properties/"
				+ fileName + ".properties");

		return file.exists();

	}

	public String[] getProperty(String name, int mode) {

		if (mode == 1) {

			try {

				PreparedStatement stmt = conn
						.prepareStatement("select * from propriedades where sistema = ? and arquivo = ? and nome = ?");

				stmt.setString(1, system.toLowerCase());
				stmt.setString(2, fileName + ".properties");
				stmt.setString(3, name);

				ResultSet rs = stmt.executeQuery();

				String[] property = new String[2];

				if (rs.next()) {

					property[0] = rs.getString("nome");
					property[1] = rs.getString("descricao");
					return property;

				}

				return null;

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		} else {

			try {

				PreparedStatement stmt = conn
						.prepareStatement("select * from propriedades where sistema = ? and arquivo = ? and descricao = ?");

				stmt.setString(1, system.toLowerCase());
				stmt.setString(2, fileName + ".properties");
				stmt.setString(3, name);

				ResultSet rs = stmt.executeQuery();

				String[] property = new String[2];

				if (rs.next()) {

					property[0] = rs.getString("nome");
					property[1] = rs.getString("descricao");
					return property;

				}

				return null;

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		}

	}

	public String[][] getProperties() {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("select * from propriedades where sistema = ? and arquivo = ?");

			stmt.setString(1, system.toLowerCase());
			stmt.setString(2, fileName + ".properties");

			ResultSet rs = stmt.executeQuery();

			int count = 0;

			while (rs.next()) {

				count++;

			}

			String[][] properties = new String[count][2];

			count = 0;

			rs = stmt.executeQuery();

			while (rs.next()) {

				properties[count][0] = rs.getString("nome");
				properties[count][1] = rs.getString("descricao");
				count++;

			}

			return properties;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String[] getPropertiesFromFile() {

		Properties property = new Properties();

		try {

			File file = new File(path + "/" + system.toLowerCase()
					+ "/properties/" + fileName + ".properties");

			FileInputStream fis = new FileInputStream(file);
			property.load(fis);

			Enumeration en = property.propertyNames();

			int i = 0;

			ArrayList<String> array = new ArrayList<>();

			while (en.hasMoreElements()) {
				array.add((String) en.nextElement());
			}

			String[] props = new String[array.size()];

			for (i = 0; i < props.length; i++) {

				props[i] = array.get(i).intern();

			}

			return props;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public boolean setCustomConfig(Profile config) {

		CurrentProfile cConfig = getCurrentConfig();

		File file;
		String sys = "";

		if (config.getSistema().equals("mercoflex")) {

			file = new File(path + "/mercoflex/properties");
			sys = path + "/mercoflex/properties";
			if (file.exists()) {
				// Propriedade <tipodb>
				setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
						.replaceAll(" ", ""));
				setPropertyValue(sys, "dbBridge", "tipodb", config
						.getTipoBanco().replaceAll(" ", ""));
				if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
					setPropertyValue(sys, "db", "port", "3306");
					setPropertyValue(sys, "dbBridge", "port", "3306");
					setPropertyValue(sys, "db", "userName", "root");
					setPropertyValue(sys, "dbBridge", "userName", "root");
				} else if (config.getTipoBanco().replaceAll(" ", "")
						.equals("sqlserver")) {
					setPropertyValue(sys, "db", "port", "1433");
					setPropertyValue(sys, "dbBridge", "port", "1433");
					setPropertyValue(sys, "db", "userName", "sa");
					setPropertyValue(sys, "dbBridge", "userName", "sa");
				} else if (config.getTipoBanco().replaceAll(" ", "")
						.equals("oracle")) {
					setPropertyValue(sys, "db", "port", "1521");
					setPropertyValue(sys, "dbBridge", "port", "1521");
					setPropertyValue(sys, "db", "userName", "");
					setPropertyValue(sys, "dbBridge", "userName", "");
				}
				// --------------------

				// Propriedade <ipDoor>
				setPropertyValue(sys, "config", "ipDoor", config.getIpDoor());
				// ----------------------

				// Propriedade <ipBridge>
				setPropertyValue(sys, "config", "ipBridge",
						config.getIpBridge());
				// ----------------------

				// Propriedade do Sistema principal <dbName>
				setPropertyValue(sys, "db", "dbName",
						config.getNomeBancoSistema());
				// -----------------------------------------

				// Propriedade do Sistema principal <hostName>
				setPropertyValue(sys, "db", "hostName",
						config.getIpBancoSistema());
				// -------------------------------------------

				// Propriedade do FlexBridge <dbName>
				setPropertyValue(sys, "dbBridge", "dbName",
						config.getNomeBancoBridge());
				// ----------------------------------

				// Propriedade do FlexBridge <hostName>
				setPropertyValue(sys, "dbBridge", "hostName",
						config.getIpBancoBridge());
				// ------------------------------------
			}

		} else {

			file = new File(path + "/flexconcent/properties");
			sys = path + "/flexconcent/properties";
			if (file.exists()) {
				// Propriedade <tipodb>
				setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
						.replaceAll(" ", ""));
				setPropertyValue(sys, "dbBridge", "tipodb", config
						.getTipoBanco().replaceAll(" ", ""));
				if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
					setPropertyValue(sys, "db", "port", "3306");
					setPropertyValue(sys, "dbBridge", "port", "3306");
					setPropertyValue(sys, "db", "userName", "root");
					setPropertyValue(sys, "dbBridge", "userName", "root");
				} else if (config.getTipoBanco().replaceAll(" ", "")
						.equals("sqlserver")) {
					setPropertyValue(sys, "db", "port", "1433");
					setPropertyValue(sys, "dbBridge", "port", "1433");
					setPropertyValue(sys, "db", "userName", "sa");
					setPropertyValue(sys, "dbBridge", "userName", "sa");
				} else if (config.getTipoBanco().replaceAll(" ", "")
						.equals("oracle")) {
					setPropertyValue(sys, "db", "port", "1521");
					setPropertyValue(sys, "dbBridge", "port", "1521");
					setPropertyValue(sys, "db", "userName", "");
					setPropertyValue(sys, "dbBridge", "userName", "");
				}
				// --------------------

				// Propriedade <ipDoor>
				setPropertyValue(sys, "config", "ipDoor", config.getIpDoor());
				// ----------------------

				// Propriedade <ipBridge>
				setPropertyValue(sys, "config", "ipBridge",
						config.getIpBridge());
				// ----------------------

				// Propriedade do Sistema principal <dbName>
				setPropertyValue(sys, "db", "dbName",
						config.getNomeBancoSistema());
				// -----------------------------------------

				// Propriedade do Sistema principal <hostName>
				setPropertyValue(sys, "db", "hostName",
						config.getIpBancoSistema());
				// -------------------------------------------

				// Propriedade do FlexBridge <dbName>
				setPropertyValue(sys, "dbBridge", "dbName",
						config.getNomeBancoBridge());
				// ----------------------------------

				// Propriedade do FlexBridge <hostName>
				setPropertyValue(sys, "dbBridge", "hostName",
						config.getIpBancoBridge());
				// ------------------------------------
			}

		}

		file = new File(path + "/flexdoor/properties");
		sys = path + "/flexdoor/properties";
		if (file.exists()) {
			// Propriedade do Sistema principal <sistema>
			setPropertyValue(sys, "config", "sistema", config.getSistema());
			// ------------------------------------------

			// Propriedade <tipodb>
			setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
				setPropertyValue(sys, "db", "port", "3306");
				setPropertyValue(sys, "db", "userName", "root");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("sqlserver")) {
				setPropertyValue(sys, "db", "port", "1433");
				setPropertyValue(sys, "db", "userName", "sa");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("oracle")) {
				setPropertyValue(sys, "db", "port", "1521");
				setPropertyValue(sys, "db", "userName", "");
			}
			// --------------------

			// Propriedade do Sistema principal <dbName>
			setPropertyValue(sys, "db", "dbName", config.getNomeBancoSistema());
			// -----------------------------------------

			// Propriedade do Sistema principal <hostName>
			setPropertyValue(sys, "db", "hostName", config.getIpBancoSistema());
			// -------------------------------------------
		}

		file = new File(path + "/flexbridge/properties");
		sys = path + "/flexbridge/properties";
		if (file.exists()) {
			// Propriedade do Sistema principal <sistema>
			setPropertyValue(sys, "config", "sistema", config.getSistema());
			// ------------------------------------------

			// Propriedade <tipodb>
			setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			setPropertyValue(sys, "dbMercoBI", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			setPropertyValue(sys, "dbRetaguarda", "tipodb", config
					.getTipoBanco().replaceAll(" ", ""));
			if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
				setPropertyValue(sys, "db", "port", "3306");
				setPropertyValue(sys, "dbMercoBI", "port", "3306");
				setPropertyValue(sys, "dbRetaguarda", "port", "3306");
				setPropertyValue(sys, "db", "userName", "root");
				setPropertyValue(sys, "dbMercoBI", "userName", "root");
				setPropertyValue(sys, "dbRetaguarda", "userName", "root");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("sqlserver")) {
				setPropertyValue(sys, "db", "port", "1433");
				setPropertyValue(sys, "dbMercoBI", "port", "1433");
				setPropertyValue(sys, "dbRetaguarda", "port", "1433");
				setPropertyValue(sys, "db", "userName", "sa");
				setPropertyValue(sys, "dbMercoBI", "userName", "sa");
				setPropertyValue(sys, "dbRetaguarda", "userName", "sa");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("oracle")) {
				setPropertyValue(sys, "db", "port", "1521");
				setPropertyValue(sys, "dbMercoBI", "port", "1521");
				setPropertyValue(sys, "dbRetaguarda", "port", "1521");
				setPropertyValue(sys, "db", "userName", "");
				setPropertyValue(sys, "dbMercoBI", "userName", "");
				setPropertyValue(sys, "dbRetaguarda", "userName", "");
			}
			// --------------------

			// Propriedade <ipDoor>
			setPropertyValue(sys, "config", "ipDoor", config.getIpDoor());
			// ----------------------

			// Propriedade do Banco do FlexBridge <dbName>
			setPropertyValue(sys, "db", "dbName", config.getNomeBancoBridge());
			// ---------------------------------------------

			// Propriedade do Banco do FlexBridge <hostName>
			setPropertyValue(sys, "db", "hostName", config.getIpBancoBridge());
			// ---------------------------------------------

			// Propriedade do Banco do MercoBI <dbName>
			setPropertyValue(sys, "dbMercoBI", "dbName",
					config.getNomeBancoMercoBI());
			// ---------------------------------------------

			// Propriedade do Banco do MercoBI <hostName>
			setPropertyValue(sys, "dbMercoBI", "hostName",
					config.getIpBancoMercoBI());
			// ---------------------------------------------

			// Propriedade do Sistema principal <dbName>
			setPropertyValue(sys, "dbRetaguarda", "dbName",
					config.getNomeBancoSistema());
			// -----------------------------------------

			// Propriedade do Sistema principal <hostName>
			setPropertyValue(sys, "dbRetaguarda", "hostName",
					config.getIpBancoSistema());
			// -------------------------------------------
		}

		file = new File(path + "/flexfatur/properties");
		sys = path + "/flexfatur/properties";
		if (file.exists()) {
			// Propriedade do Sistema principal <sistema>
			setPropertyValue(sys, "config", "sistema", config.getSistema());
			// ------------------------------------------

			// Propriedade <tipodb>
			setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			setPropertyValue(sys, "dbNfe", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
				setPropertyValue(sys, "db", "port", "3306");
				setPropertyValue(sys, "dbNfe", "port", "3306");
				setPropertyValue(sys, "db", "userName", "root");
				setPropertyValue(sys, "dbNfe", "userName", "root");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("sqlserver")) {
				setPropertyValue(sys, "db", "port", "1433");
				setPropertyValue(sys, "dbNfe", "port", "1433");
				setPropertyValue(sys, "db", "userName", "sa");
				setPropertyValue(sys, "dbNfe", "userName", "sa");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("oracle")) {
				setPropertyValue(sys, "db", "port", "1521");
				setPropertyValue(sys, "dbNfe", "port", "1521");
				setPropertyValue(sys, "db", "userName", "");
				setPropertyValue(sys, "dbNfe", "userName", "");
			}
			// --------------------

			// Propriedade <ipDoor>
			setPropertyValue(sys, "config", "ipDoor", config.getIpDoor());
			// ----------------------

			// Propriedade do Sistema principal <dbName>
			setPropertyValue(sys, "db", "dbName", config.getNomeBancoSistema());
			// -----------------------------------------

			// Propriedade do Sistema principal <hostName>
			setPropertyValue(sys, "db", "hostName", config.getIpBancoSistema());
			// -------------------------------------------

			// Propriedade da NF-e <dbName>
			setPropertyValue(sys, "dbNfe", "dbName", config.getNomeBancoNFE());
			// ----------------------------

			// Propriedade da NF-e <hostName>
			setPropertyValue(sys, "dbNfe", "hostName", config.getIpBancoNFE());
			// ------------------------------
		}

		file = new File(path + "/flexbalcao/properties");
		sys = path + "/flexbalcao/properties";
		if (file.exists()) {
			// Propriedade do Sistema principal <sistema>
			setPropertyValue(sys, "config", "sistema", config.getSistema());
			// ------------------------------------------

			// Propriedade <tipodb>
			setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
				setPropertyValue(sys, "db", "port", "3306");
				setPropertyValue(sys, "db", "userName", "root");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("sqlserver")) {
				setPropertyValue(sys, "db", "port", "1433");
				setPropertyValue(sys, "db", "userName", "sa");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("oracle")) {
				setPropertyValue(sys, "db", "port", "1521");
				setPropertyValue(sys, "db", "userName", "");
			}
			// --------------------

			// Propriedade <ipDoor>
			setPropertyValue(sys, "config", "ipDoor", config.getIpDoor());
			// ----------------------

			// Propriedade do Sistema principal <dbName>
			setPropertyValue(sys, "db", "dbName", config.getNomeBancoSistema());
			// -----------------------------------------

			// Propriedade do Sistema principal <hostName>
			setPropertyValue(sys, "db", "hostName", config.getIpBancoSistema());
			// -------------------------------------------
		}

		file = new File(path + "/flexpdv/properties");
		sys = path + "/flexpdv/properties";
		if (file.exists()) {
			// Propriedade <tipodb>
			// setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
			// .replaceAll(" ", ""));
			setPropertyValue(sys, "dbBridge", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			setPropertyValue(sys, "dbRetaguarda", "tipodb", config
					.getTipoBanco().replaceAll(" ", ""));
			if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
				// setPropertyValue(sys, "db", "port", "3306");
				setPropertyValue(sys, "dbBridge", "port", "3306");
				setPropertyValue(sys, "dbRetaguarda", "port", "3306");
				// setPropertyValue(sys, "db", "userName", "root");
				setPropertyValue(sys, "dbBridge", "userName", "root");
				setPropertyValue(sys, "dbRetaguarda", "userName", "root");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("sqlserver")) {
				// setPropertyValue(sys, "db", "port", "1433");
				setPropertyValue(sys, "dbBridge", "port", "1433");
				setPropertyValue(sys, "dbRetaguarda", "port", "1433");
				// setPropertyValue(sys, "db", "userName", "sa");
				setPropertyValue(sys, "dbBridge", "userName", "sa");
				setPropertyValue(sys, "dbRetaguarda", "userName", "sa");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("oracle")) {
				// setPropertyValue(sys, "db", "port", "1521");
				setPropertyValue(sys, "dbBridge", "port", "1521");
				setPropertyValue(sys, "dbRetaguarda", "port", "1521");
				// setPropertyValue(sys, "db", "userName", "");
				setPropertyValue(sys, "dbBridge", "userName", "");
				setPropertyValue(sys, "dbRetaguarda", "userName", "");
			}
			// --------------------

			// Propriedade do Sistema principal <dbName>
			setPropertyValue(sys, "dbRetaguarda", "dbName",
					config.getNomeBancoSistema());
			// -----------------------------------------

			// Propriedade do Sistema principal <hostName>
			setPropertyValue(sys, "dbRetaguarda", "hostName",
					config.getIpBancoSistema());
			// -------------------------------------------

			// Propriedade do FlexBridge <dbName>
			setPropertyValue(sys, "dbBridge", "dbName",
					config.getNomeBancoBridge());
			// ----------------------------------

			// Propriedade do FlexBridge <hostName>
			setPropertyValue(sys, "dbBridge", "hostName",
					config.getIpBancoBridge());
			// ------------------------------------

			// Propriedade do FlexPDV e NF <dbName>
			setPropertyValue(sys, "db", "dbName", config.getNomeBancoPDV());
			// ------------------------------------

			// Propriedade do FlexPDV e NF <hostName>
			setPropertyValue(sys, "db", "hostName", config.getIpBancoPDV());
			// --------------------------------------

		}

		file = new File(path + "/flexpdvnf/properties");
		sys = path + "/flexpdvnf/properties";
		if (file.exists()) {
			// Propriedade <tipodb>
			// setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
			// .replaceAll(" ", ""));
			setPropertyValue(sys, "dbBridge", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			setPropertyValue(sys, "dbRetaguarda", "tipodb", config
					.getTipoBanco().replaceAll(" ", ""));
			if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
				// setPropertyValue(sys, "db", "port", "3306");
				setPropertyValue(sys, "dbBridge", "port", "3306");
				setPropertyValue(sys, "dbRetaguarda", "port", "3306");
				// setPropertyValue(sys, "db", "userName", "root");
				setPropertyValue(sys, "dbBridge", "userName", "root");
				setPropertyValue(sys, "dbRetaguarda", "userName", "root");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("sqlserver")) {
				// setPropertyValue(sys, "db", "port", "1433");
				setPropertyValue(sys, "dbBridge", "port", "1433");
				setPropertyValue(sys, "dbRetaguarda", "port", "1433");
				// setPropertyValue(sys, "db", "userName", "sa");
				setPropertyValue(sys, "dbBridge", "userName", "sa");
				setPropertyValue(sys, "dbRetaguarda", "userName", "sa");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("oracle")) {
				// setPropertyValue(sys, "db", "port", "1521");
				setPropertyValue(sys, "dbBridge", "port", "1521");
				setPropertyValue(sys, "dbRetaguarda", "port", "1521");
				// setPropertyValue(sys, "db", "userName", "");
				setPropertyValue(sys, "dbBridge", "userName", "");
				setPropertyValue(sys, "dbRetaguarda", "userName", "");
			}
			// --------------------

			// Propriedade do Sistema principal <dbName>
			setPropertyValue(sys, "dbRetaguarda", "dbName",
					config.getNomeBancoSistema());
			// -----------------------------------------

			// Propriedade do Sistema principal <hostName>
			setPropertyValue(sys, "dbRetaguarda", "hostName",
					config.getIpBancoSistema());
			// -------------------------------------------

			// Propriedade do FlexBridge <dbName>
			setPropertyValue(sys, "dbBridge", "dbName",
					config.getNomeBancoBridge());
			// ----------------------------------

			// Propriedade do FlexBridge <hostName>
			setPropertyValue(sys, "dbBridge", "hostName",
					config.getIpBancoBridge());
			// ------------------------------------

			// Propriedade do FlexPDV e NF <dbName>
			setPropertyValue(sys, "db", "dbName", config.getNomeBancoPDV());
			// ------------------------------------

			// Propriedade do FlexPDV e NF <hostName>
			setPropertyValue(sys, "db", "hostName", config.getIpBancoPDV());
			// --------------------------------------

		}

		file = new File(path + "/flexreport/properties");
		sys = path + "/flexreport/properties";
		if (file.exists()) {
			// Propriedade do Sistema principal <sistema>
			setPropertyValue(sys, "config", "sistema", config.getSistema());
			// ------------------------------------------

			// Propriedade <tipodb>
			setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
				setPropertyValue(sys, "db", "port", "3306");
				setPropertyValue(sys, "db", "userName", "root");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("sqlserver")) {
				setPropertyValue(sys, "db", "port", "1433");
				setPropertyValue(sys, "db", "userName", "sa");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("oracle")) {
				setPropertyValue(sys, "db", "port", "1521");
				setPropertyValue(sys, "db", "userName", "");
			}
			// --------------------

			// Propriedade <ipDoor>
			setPropertyValue(sys, "config", "ipDoor", config.getIpDoor());
			// ----------------------

			// Propriedade do Sistema principal <dbName>
			setPropertyValue(sys, "db", "dbName", config.getNomeBancoSistema());
			// -----------------------------------------

			// Propriedade do Sistema principal <hostName>
			setPropertyValue(sys, "db", "hostName", config.getIpBancoSistema());
			// -------------------------------------------
		}

		file = new File(path + "/fleximport/properties");
		sys = path + "/fleximport/properties";
		if (file.exists()) {
			// Propriedade <tipodb>
			setPropertyValue(sys, "db", "tipodb", config.getTipoBanco()
					.replaceAll(" ", ""));
			if (config.getTipoBanco().replaceAll(" ", "").equals("mysql")) {
				setPropertyValue(sys, "db", "port", "3306");
				setPropertyValue(sys, "db", "userName", "root");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("sqlserver")) {
				setPropertyValue(sys, "db", "port", "1433");
				setPropertyValue(sys, "db", "userName", "sa");
			} else if (config.getTipoBanco().replaceAll(" ", "")
					.equals("oracle")) {
				setPropertyValue(sys, "db", "port", "1521");
				setPropertyValue(sys, "db", "userName", "");
			}
			// --------------------

			// Propriedade do FlexPDV e NF <dbName>
			setPropertyValue(sys, "db", "dbName", config.getNomeBancoPDV());
			// ------------------------------------

			// Propriedade do FlexPDV e NF <hostName>
			setPropertyValue(sys, "db", "hostName", config.getIpBancoPDV());
			// --------------------------------------
		}

		file = new File(path + "/flextotem/properties");
		sys = path + "/flextotem/properties";
		if (file.exists()) {
			// Propriedade <ipDoor>
			setPropertyValue(sys, "config", "ipDoor", config.getIpDoor());
			// ----------------------
		}

		return true;

	}

	private CurrentProfile getCurrentConfig() {

		return null;

	}

	private boolean reverseConfiguration(CurrentProfile config) {

		return true;

	}

}
