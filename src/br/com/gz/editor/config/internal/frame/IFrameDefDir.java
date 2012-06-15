package br.com.gz.editor.config.internal.frame;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.gz.editor.dao.InternalDAO;
import br.com.gz.editor.desktop.internal.frame.IFrameEdit;
import br.com.gz.editor.main.FlexEditor;

public class IFrameDefDir extends JInternalFrame implements ActionListener {

	private static IFrameDefDir instance;
	
	private Container window;

	private JPanel panel;

	private JTextField txtDiretorio;

	private JButton btPesq;
	private JButton btSalvar;

	private JMenuBar menuBar;

	private JFileChooser fileChooser;

	private JFrame frameToDispose;

	private boolean isConfig;
	
	private JDesktopPane desktop;

	private IFrameDefDir(JFrame frame, JDesktopPane desktop, boolean config) {

		frameToDispose = frame;
		isConfig = config;
		this.desktop = desktop;

		setSize(400, 150);
		setTitle("Diretório padrão dos sistemas");
		setClosable(true);
		setIconifiable(true);

		int contWidth = desktop.getWidth();
		int contHeidth = desktop.getHeight();

		setLocation((contWidth - 400) / 2, (contHeidth - 150) / 2);

		window = getContentPane();
		window.setLayout(null);

		panel = new JPanel(null);
		panel.setBounds(20, 20, 360, 60);
		panel.setBorder(BorderFactory.createEtchedBorder());

		txtDiretorio = new JTextField();
		txtDiretorio.setBounds(20, 20, 300, 20);

		btPesq = new JButton(new ImageIcon((URL) getClass().getResource(
				"/imagem/procurar.png")));
		btPesq.setBounds(325, 20, 20, 20);

		menuBar = new JMenuBar();
		btSalvar = new JButton(new ImageIcon((URL) getClass().getResource(
				"/imagem/salvar.png")));

		btSalvar.setToolTipText("Salvar");

		menuBar.add(btSalvar);

		setJMenuBar(menuBar);

		panel.add(txtDiretorio);
		panel.add(btPesq);
		window.add(panel);

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		btPesq.addActionListener(this);
		btSalvar.addActionListener(this);

		txtDiretorio.setText(new InternalDAO().getDefaultDirectory());

	}
	
	public static IFrameDefDir getInstance(JFrame frame, JDesktopPane desktop, boolean config){
		
		if(instance==null){
			
			instance = new IFrameDefDir(frame, desktop, config);
			
		}
		
		return instance;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btPesq) {

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

				txtDiretorio.setText(fileChooser.getSelectedFile()
						.getAbsolutePath());

			}

		} else if (e.getSource() == btSalvar) {

			new InternalDAO().setDefaultDirectory(txtDiretorio.getText());
			
			stopThreadLocks();

			if (isConfig) {
				FlexEditor.main(new String[] {"config","reload"});
			} else {
				FlexEditor.main(new String[] {"reload"});
			}

			instance = null;
			
			frameToDispose.dispose();

		}

	}
	
	private void stopThreadLocks(){
		
		if(isConfig){
			
			JInternalFrame[] frames = this.desktop.getAllFrames();
			for (JInternalFrame f : frames) {
				
				if(f instanceof IFrameConfig){
					((IFrameConfig)f).getThread().suspend();
					((IFrameConfig)f).getThread().unlock();
					((IFrameConfig)f).getThread().stop();
				}
				
			}
			
		}else{
			
			JInternalFrame[] frames = this.desktop.getAllFrames();
			for (JInternalFrame f : frames) {
				
				if(f instanceof IFrameEdit){
					((IFrameEdit)f).getThread().suspend();
					((IFrameEdit)f).getThread().unlock();
					((IFrameEdit)f).getThread().stop();
				}
				
			}
			
		}
		
	}

}
