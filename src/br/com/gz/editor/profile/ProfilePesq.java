package br.com.gz.editor.profile;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.EventObject;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import br.com.gz.editor.dao.InternalDAO;

public class ProfilePesq extends JFrame implements ActionListener {

	private static ProfilePesq instance;

	private Container window;

	private ProfileFrame frameOwner;

	private JTabbedPane tabbedPane;

	private JTable tableBanco;
	private JTable tableArquivo;
	private JScrollPane scrollPaneBanco;
	private JScrollPane scrollPaneArquivo;
	private DefaultTableModel modelBanco;
	private DefaultTableModel modelArquivo;

	private JButton btOk;
	private JButton btCancelar;

	private ProfilePesq(ProfileFrame owner) {

		frameOwner = owner;

		setSize(300, 400);
		setLocation(512 - 150, 768 / 2 - 300);
		setResizable(false);
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/imagem/icone.gif")));
		setTitle("Perfis de configuração");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {

				instance = null;
				dispose();

			}

		});

		window = getContentPane();
		window.setLayout(null);

		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(10, 10, 275, 300);

		String[] columnIdentifiersBanco = { "Código", "Nome" };

		modelBanco = new DefaultTableModel();
		modelBanco.setColumnIdentifiers(columnIdentifiersBanco);
		tableBanco = new JTable(modelBanco);
		scrollPaneBanco = new JScrollPane(tableBanco);

		scrollPaneBanco.setBounds(10, 30, 275, 300);

		btOk = new JButton("ok");
		btOk.setBounds(10, 340, 50, 20);

		btCancelar = new JButton("cancelar");
		btCancelar.setBounds(65, 340, 70, 20);

		btOk.addActionListener(this);
		btCancelar.addActionListener(this);

		tabbedPane.addTab("Configurações", scrollPaneBanco);

		window.add(tabbedPane);
		window.add(btOk);
		window.add(btCancelar);

		Iterator<Profile> it = new InternalDAO()
				.getProfile().iterator();

		while (it.hasNext()) {

			Profile cf = it.next();

			String[] config = { new Integer(cf.getCodigo()).toString(),
					cf.getNomeConf() };

			modelBanco.addRow(config);

		}

		tableBanco.getTableHeader().setReorderingAllowed(false);

		TableColumn column = tableBanco.getColumnModel().getColumn(0);

		column.setCellEditor(new TableCellEditor() {

			@Override
			public boolean stopCellEditing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean shouldSelectCell(EventObject arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void removeCellEditorListener(CellEditorListener arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isCellEditable(EventObject arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Object getCellEditorValue() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void cancelCellEditing() {
				// TODO Auto-generated method stub

			}

			@Override
			public void addCellEditorListener(CellEditorListener arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public Component getTableCellEditorComponent(JTable arg0,
					Object arg1, boolean arg2, int arg3, int arg4) {
				// TODO Auto-generated method stub
				return null;
			}
		});

		column = tableBanco.getColumnModel().getColumn(1);

		column.setCellEditor(new TableCellEditor() {

			@Override
			public boolean stopCellEditing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean shouldSelectCell(EventObject arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void removeCellEditorListener(CellEditorListener arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isCellEditable(EventObject arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Object getCellEditorValue() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void cancelCellEditing() {
				// TODO Auto-generated method stub

			}

			@Override
			public void addCellEditorListener(CellEditorListener arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public Component getTableCellEditorComponent(JTable arg0,
					Object arg1, boolean arg2, int arg3, int arg4) {
				// TODO Auto-generated method stub
				return null;
			}
		});

		tableBanco.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (arg0.getClickCount() == 2) {

					frameOwner.getCodigo(Integer.parseInt((String) tableBanco
							.getValueAt(tableBanco.getSelectedRow(), 0)));
					instance = null;
					dispose();

				}

			}
		});

	}

	public static ProfilePesq getInstance(ProfileFrame owner) {

		if (instance == null) {

			instance = new ProfilePesq(owner);

		}

		return instance;

	}

	public static void close() {

		instance.dispose();
		instance = null;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == btOk) {

			if (tableBanco.getSelectedRow() > -1) {

				frameOwner.getCodigo(Integer.parseInt((String) tableBanco
						.getValueAt(tableBanco.getSelectedRow(), 0)));

			}
			instance = null;
			dispose();

		} else if (arg0.getSource() == btCancelar) {

			instance = null;
			dispose();

		}

	}

}
