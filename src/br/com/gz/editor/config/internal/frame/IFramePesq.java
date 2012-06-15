package br.com.gz.editor.config.internal.frame;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.security.acl.Owner;
import java.util.EventObject;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import br.com.gz.editor.dao.PropertiesDAO;
import br.com.gz.editor.lock.ThreadReadOnly;

public class IFramePesq extends JFrame implements ActionListener {

	private Container window;

	private IFrameConfig frameOwner;

	private JTabbedPane tabbedPane;

	private JTable tableBanco;
	private JTable tableArquivo;
	private JScrollPane scrollPaneBanco;
	private JScrollPane scrollPaneArquivo;
	private DefaultTableModel modelBanco;
	private DefaultTableModel modelArquivo;

	private JButton btOk;
	private JButton btCancelar;

	private String system;
	private String fileName;

	private ThreadReadOnly thread;

	public IFramePesq(IFrameConfig owner, String system, String fileName,
			ThreadReadOnly thread) {

		this.system = system;
		this.fileName = fileName;
		this.thread = thread;
		frameOwner = owner;

		setSize(300, 400);
		setLocation(512 - 150, 768 / 2 - 300);
		setResizable(false);
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/imagem/icone.gif")));
		setTitle(system + " - " + fileName + " - " + "Propriedades");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		window = getContentPane();
		window.setLayout(null);

		this.thread.suspend();
		this.thread.unlock();

		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(10, 10, 275, 300);

		String[] columnIdentifiersBanco = { "Nome", "Descrição" };
		String[] columnIdentifiersArquivo = { "Nome" };

		modelBanco = new DefaultTableModel();
		modelBanco.setColumnIdentifiers(columnIdentifiersBanco);
		tableBanco = new JTable(modelBanco);
		scrollPaneBanco = new JScrollPane(tableBanco);

		scrollPaneBanco.setBounds(10, 30, 275, 300);

		modelArquivo = new DefaultTableModel();
		modelArquivo.setColumnIdentifiers(columnIdentifiersArquivo);
		tableArquivo = new JTable(modelArquivo);
		scrollPaneArquivo = new JScrollPane(tableArquivo);

		scrollPaneArquivo.setBounds(10, 30, 275, 300);

		btOk = new JButton("ok");
		btOk.setBounds(10, 340, 50, 20);

		btCancelar = new JButton("cancelar");
		btCancelar.setBounds(65, 340, 70, 20);

		btOk.addActionListener(this);
		btCancelar.addActionListener(this);

		tabbedPane.addTab("No banco", scrollPaneBanco);
		tabbedPane.addTab("No arquivo", scrollPaneArquivo);

		window.add(tabbedPane);
		window.add(btOk);
		window.add(btCancelar);

		PropertiesDAO dao = new PropertiesDAO(system, fileName);

		String[][] propertiesBanco = dao.getProperties();
		String[] propertiesArquivo = dao.getPropertiesFromFile();

		tableBanco.getTableHeader().setReorderingAllowed(false);
		tableArquivo.getTableHeader().setReorderingAllowed(false);

		for (int i = 0; i < propertiesBanco.length; i++) {

			modelBanco.addRow(propertiesBanco[i]);

		}

		for (int i = 0; i < propertiesArquivo.length; i++) {

			String[][] row = { { propertiesArquivo[i] }, {} };
			modelArquivo.addRow(row[0]);

		}

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

					frameOwner.setText(
							(String) tableBanco.getValueAt(
									tableBanco.getSelectedRow(), 0),
							(String) tableBanco.getValueAt(
									tableBanco.getSelectedRow(), 1));

					IFramePesq.this.thread.resume();

					dispose();

				}

			}
		});

		TableColumn columnArquivo = tableArquivo.getColumnModel().getColumn(0);

		tableArquivo.setBackground(this.getBackground());

		columnArquivo.setCellEditor(new TableCellEditor() {

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

		IFramePesq.this.thread.resume();

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == btOk) {

			if (tableBanco.getSelectedRow() > -1) {

				frameOwner.setText(
						(String) tableBanco.getValueAt(
								tableBanco.getSelectedRow(), 0),
						(String) tableBanco.getValueAt(
								tableBanco.getSelectedRow(), 1));

			}

			IFramePesq.this.thread.resume();

			dispose();

		} else if (arg0.getSource() == btCancelar) {

			IFramePesq.this.thread.resume();

			dispose();

		}

	}

}
