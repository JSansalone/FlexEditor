/**
 * 
 * @project_name 	FlexEditor
 * @author 			Jonathan Sansalone
 * @initial_date 	12 de Janeiro de 2012
 * @final_date 		25 de Janeiro de 2012
 * 
 * @last release        15 de Fevereiro de 2012
 */

package br.com.gz.editor.main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.gz.editor.config.EditorConfig;
import br.com.gz.editor.dao.InternalDAO;
import br.com.gz.editor.desktop.EditorDesktop;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;

public class FlexEditor {

	public static void main(String[] args) {

		PlasticXPLookAndFeel laf = new PlasticXPLookAndFeel();
		PlasticXPLookAndFeel.setCurrentTheme(new SkyBluer());

		try {
			UIManager.setLookAndFeel(laf);
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		File file = new File("editordb");

		if (file.exists()) {

			CreateDB.createTables();

			if (args.length > 1) {

				if (args[0].equals("config") && !args[1].equals("reload")) {

					if (new InternalDAO().getDoNotAskAgain()) {
						new EditorConfig(CreateDB.showNotes());
					} else {
						new DefDirFrame(true);
					}

				} else if (args[0].equals("config") && args[1].equals("reload")) {

					new EditorConfig(CreateDB.showNotes());

				} else {

					JOptionPane.showMessageDialog(null, "Argumento inválido",
							"Erro", JOptionPane.ERROR_MESSAGE);

				}

			} else if (args.length == 1) {

				if (args[0].equals("config")) {

					if (new InternalDAO().getDoNotAskAgain()) {
						new EditorConfig(CreateDB.showNotes());
					} else {
						new DefDirFrame(true);
					}

				} else if (args[0].equals("reload")) {

					new EditorDesktop(CreateDB.showNotes());

				} else {

					JOptionPane.showMessageDialog(null, "Argumento inválido",
							"Erro", JOptionPane.ERROR_MESSAGE);

				}

			} else {

				if (new InternalDAO().getDoNotAskAgain()) {
					new EditorDesktop(CreateDB.showNotes());
				} else {
					new DefDirFrame(false);
				}

			}

		} else {

			if (args.length == 1) {

				if (args[0].equals("config")) {

					new CreateDB(true).start();

				} else {

					JOptionPane.showMessageDialog(null, "Argumento inválido",
							"Erro", JOptionPane.ERROR_MESSAGE);

				}

			} else if (args.length == 0) {

				new CreateDB(false).start();

			}

		}

	}

	public static boolean isValidSystemName(String fileName) {

		switch (fileName.toLowerCase()) {

		case "mercoflex":

			return true;

		case "flexdoor":

			return true;

		case "flexbridge":

			return true;

		case "flexfatur":

			return true;

		case "flexconcent":

			return true;

		case "flexbalcao":

			return true;

		case "flexpdv":

			return true;

		case "flexpdvnf":

			return true;

		case "flexmenu":

			return true;

		case "flextotem":

			return true;

		case "flexreport":

			return true;

		case "fleximport":

			return true;

		case "flextransport":

			return true;

		case "flexplug":

			return true;

		case "flexdump":

			return true;

		default:

			return false;

		}

	}

	public static String systemToCamelCase(String system) {

		switch (system.toLowerCase()) {

		case "mercoflex":

			return "MercoFlex";

		case "flexdoor":

			return "FlexDoor";

		case "flexbridge":

			return "FlexBridge";

		case "flexfatur":

			return "FlexFatur";

		case "flexconcent":

			return "FlexConcent";

		case "flexbalcao":

			return "FlexBalcao";

		case "flexpdv":

			return "FlexPDV";

		case "flexpdvnf":

			return "FlexPDVNF";

		case "flexmenu":

			return "FlexMenu";

		case "flextotem":

			return "FlexTotem";

		case "flexreport":

			return "FlexReport";

		case "fleximport":

			return "FlexImport";

		case "flextransport":

			return "FlexTransport";

		case "flexplug":

			return "FlexPlug";

		case "flexdump":

			return "FlexDump";

		default:

			return null;

		}

	}

}
