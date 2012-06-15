package br.com.gz.editor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.gz.editor.profile.Profile;

public class InternalDAO {

	private Connection conn;

	public InternalDAO() {

		try {

			conn = DriverManager.getConnection("jdbc:derby:editordb");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String getDefaultDirectory() {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("select * from diretorio");
			ResultSet rs = stmt.executeQuery();

			String caminho = "";

			if (rs.next()) {

				caminho = rs.getString("caminho");

			}

			rs.close();
			stmt.close();

			return caminho;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public boolean setDefaultDirectory(String directory) {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("update diretorio set caminho = ?, pergunte_novamente = ?");
			stmt.setString(1, directory);
			stmt.setInt(2, 1);

			stmt.execute();
			stmt.close();

			return true;

		} catch (SQLException e) {

			e.printStackTrace();

			return false;

		}

	}

	public boolean setDoNotAskAgain(boolean doNotAskAgain) {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("update diretorio set pergunte_novamente = ?");
			if (doNotAskAgain) {
				stmt.setInt(1, 0);
			} else {
				stmt.setInt(1, 1);
			}

			stmt.execute();
			stmt.close();

			return true;

		} catch (SQLException e) {

			e.printStackTrace();

			return false;

		}

	}

	public boolean getDoNotAskAgain() {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("select * from diretorio");

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int aux = rs.getInt("pergunte_novamente");
				rs.close();
				stmt.close();

				if (aux == 1) {

					return false;

				} else {

					return true;

				}
			} else {

				JOptionPane.showMessageDialog(null, "Erro", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return true;

			}

		} catch (SQLException e) {

			e.printStackTrace();

			return false;

		}

	}

	public void removeProfile(int codigo) {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("delete from perfil where codigo = ?");
			stmt.setInt(1, codigo);
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Profile> getProfile() {

		ArrayList<Profile> array = new ArrayList<>();

		try {

			PreparedStatement stmt = conn
					.prepareStatement("select * from perfil");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Profile config = new Profile();
				config.setCodigo(rs.getInt(1));
				config.setNomeConf(rs.getString(2));
				config.setSistema(rs.getString(3));
				config.setTipoBanco(rs.getString(4));
				config.setIpDoor(rs.getString(5));
				config.setIpBridge(rs.getString(6));
				config.setNomeBancoSistema(rs.getString(7));
				config.setIpBancoSistema(rs.getString(8));
				config.setNomeBancoBridge(rs.getString(9));
				config.setIpBancoBridge(rs.getString(10));
				config.setNomeBancoNFE(rs.getString(11));
				config.setIpBancoNFE(rs.getString(12));
				config.setNomeBancoPDV(rs.getString(13));
				config.setIpBancoPDV(rs.getString(14));
				config.setNomeBancoMercoBI(rs.getString(15));
				config.setIpBancoMercoBI(rs.getString(16));
				config.setFtpMovtoIP(rs.getString(17));
				config.setFtpMovtoDiretorio(rs.getString(18));
				config.setFtpMovtoUsuario(rs.getString(19));
				config.setFtpMovtoSenha(rs.getString(20));
				config.setFtpCargaIP(rs.getString(21));
				config.setFtpCargaDiretorio(rs.getString(22));
				config.setFtpCargaUsuario(rs.getString(23));
				config.setFtpCargaSenha(rs.getString(24));

				array.add(config);

			}

			return array;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public int getMaxCodeProfile() {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("select max(codigo) as codigo from perfil");

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				int n = rs.getInt("codigo");

				if (n <= 0) {
					return 0;
				}

				return n;

			}

			rs.close();
			stmt.close();

			return -1;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

	public void addProfile(Profile profile, boolean editMode) {

		try {

			if (!editMode) {

				PreparedStatement stmt = conn
						.prepareStatement("insert into perfil values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				stmt.setInt(1, profile.getCodigo());
				stmt.setString(2, profile.getNomeConf());
				stmt.setString(3, profile.getSistema());
				stmt.setString(4, profile.getTipoBanco());
				stmt.setString(5, profile.getIpDoor());
				stmt.setString(6, profile.getIpBridge());
				stmt.setString(7, profile.getNomeBancoSistema());
				stmt.setString(8, profile.getIpBancoSistema());
				stmt.setString(9, profile.getNomeBancoBridge());
				stmt.setString(10, profile.getIpBancoBridge());
				stmt.setString(11, profile.getNomeBancoNFE());
				stmt.setString(12, profile.getIpBancoNFE());
				stmt.setString(13, profile.getNomeBancoPDV());
				stmt.setString(14, profile.getIpBancoPDV());
				stmt.setString(15, profile.getNomeBancoMercoBI());
				stmt.setString(16, profile.getIpBancoMercoBI());
				stmt.setString(17, profile.getFtpMovtoIP());
				stmt.setString(18, profile.getFtpMovtoDiretorio());
				stmt.setString(19, profile.getFtpMovtoUsuario());
				stmt.setString(20, profile.getFtpMovtoSenha());
				stmt.setString(21, profile.getFtpCargaIP());
				stmt.setString(22, profile.getFtpCargaDiretorio());
				stmt.setString(23, profile.getFtpCargaUsuario());
				stmt.setString(24, profile.getFtpCargaSenha());

				stmt.execute();
				stmt.close();

			} else {

				PreparedStatement stmt = conn
						.prepareStatement("update perfil set nome = ?, sistema = ?, tipo_db = ?, ip_door = ?, ip_bridge = ?, sistema_db = ?, ip_db_sistema = ?, bridge_db = ?, ip_db_bridge = ?, nfe_db = ?, ip_db_nfe = ?, pdv_db = ?, ip_db_pdv = ?, mercobi_db = ?, ip_db_mercobi = ?, ftp_movto_ip = ?, ftp_movto_diretorio = ?, ftp_movto_usuario = ?, ftp_movto_senha = ?, ftp_carga_ip = ?, ftp_carga_diretorio = ?, ftp_carga_usuario = ?, ftp_carga_senha = ? where codigo = ?");
				stmt.setInt(24, profile.getCodigo());
				stmt.setString(1, profile.getNomeConf());
				stmt.setString(2, profile.getSistema());
				stmt.setString(3, profile.getTipoBanco());
				stmt.setString(4, profile.getIpDoor());
				stmt.setString(5, profile.getIpBridge());
				stmt.setString(6, profile.getNomeBancoSistema());
				stmt.setString(7, profile.getIpBancoSistema());
				stmt.setString(8, profile.getNomeBancoBridge());
				stmt.setString(9, profile.getIpBancoBridge());
				stmt.setString(10, profile.getNomeBancoNFE());
				stmt.setString(11, profile.getIpBancoNFE());
				stmt.setString(12, profile.getNomeBancoPDV());
				stmt.setString(13, profile.getIpBancoPDV());
				stmt.setString(14, profile.getNomeBancoMercoBI());
				stmt.setString(15, profile.getIpBancoMercoBI());
				stmt.setString(16, profile.getFtpMovtoIP());
				stmt.setString(17, profile.getFtpMovtoDiretorio());
				stmt.setString(18, profile.getFtpMovtoUsuario());
				stmt.setString(19, profile.getFtpMovtoSenha());
				stmt.setString(20, profile.getFtpCargaIP());
				stmt.setString(21, profile.getFtpCargaDiretorio());
				stmt.setString(22, profile.getFtpCargaUsuario());
				stmt.setString(23, profile.getFtpCargaSenha());

				stmt.execute();
				stmt.close();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Profile getProfile(int codigo) {

		try {

			PreparedStatement stmt = conn
					.prepareStatement("select * from perfil where codigo = ?");
			stmt.setInt(1, codigo);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				Profile config = new Profile();
				config.setCodigo(rs.getInt(1));
				config.setNomeConf(rs.getString(2));
				config.setSistema(rs.getString(3));
				config.setTipoBanco(rs.getString(4));
				config.setIpDoor(rs.getString(5));
				config.setIpBridge(rs.getString(6));
				config.setNomeBancoSistema(rs.getString(7));
				config.setIpBancoSistema(rs.getString(8));
				config.setNomeBancoBridge(rs.getString(9));
				config.setIpBancoBridge(rs.getString(10));
				config.setNomeBancoNFE(rs.getString(11));
				config.setIpBancoNFE(rs.getString(12));
				config.setNomeBancoPDV(rs.getString(13));
				config.setIpBancoPDV(rs.getString(14));
				config.setNomeBancoMercoBI(rs.getString(15));
				config.setIpBancoMercoBI(rs.getString(16));
				config.setFtpMovtoIP(rs.getString(17));
				config.setFtpMovtoDiretorio(rs.getString(18));
				config.setFtpMovtoUsuario(rs.getString(19));
				config.setFtpMovtoSenha(rs.getString(20));
				config.setFtpCargaIP(rs.getString(21));
				config.setFtpCargaDiretorio(rs.getString(22));
				config.setFtpCargaUsuario(rs.getString(23));
				config.setFtpCargaSenha(rs.getString(24));

				return config;

			}

			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getAppVersion() {

		try {

			Connection c = DriverManager.getConnection("jdbc:derby:editordb");

			PreparedStatement st = c
					.prepareStatement("select version from app_version");

			ResultSet rs = st.executeQuery();

			rs.next();

			String aux = rs.getString("version");

			rs.close();
			st.close();
			c.close();

			return aux;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void revertVersion() {

		try {

			Connection c = DriverManager.getConnection("jdbc:derby:editordb");

			PreparedStatement st = c
					.prepareStatement("update app_version set version = ?");
			st.setString(1, "1.0.0 - 2012.03.01");
			st.execute();
			st.close();
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
