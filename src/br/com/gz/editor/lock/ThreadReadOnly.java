package br.com.gz.editor.lock;

import java.io.File;
import java.nio.file.Files;

public class ThreadReadOnly extends Thread {

	private File file;

	public ThreadReadOnly(File file) {

		this.file = file;
		setName(file.getParent()+"/"+file.getName());

	}

	public void unlock() {

		file.setWritable(true);

	}

	public void run() {

		try {

			while (true) {

				if (file.canWrite()) {

					file.setWritable(false);

				}
				
				Thread.sleep(200);

				System.out.println("thread "+getName());
				
			}

		} catch (Exception e) {

		}

	}

}
