package mp3project;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MusicPlayer implements ActionListener{
	//Audio handling
	JFileChooser fileChooser;
	FileInputStream fInputStream;
	BufferedInputStream bInputStream;
	File mFile = null;
	String fileName;
	String filePath;
	long totalLength;
	long pause;
	Player player;
	
	
	//Buttons
	JButton open = new JButton("Open MP3");
	JButton resume = new JButton("Resume");
	JButton play = new JButton("Play");
	JButton b_pause = new JButton("Pause");
	JButton reset = new JButton("Reset");
	
	MusicPlayer()
	{
		GUI();
		addActionEvents();
		
	}
	
	public void GUI()
	{
		//Window setup
		JFrame frame = new JFrame("Music Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		
		//Contents of the window
		frame.getContentPane().add(BorderLayout.PAGE_START, open);
		frame.getContentPane().add(BorderLayout.CENTER, play);
		frame.getContentPane().add(BorderLayout.EAST, b_pause);
		frame.getContentPane().add(BorderLayout.PAGE_END, reset);
		
		frame.getContentPane().add(BorderLayout.WEST, resume);
		frame.setVisible(true);
	}
	
	public void addActionEvents(){
        //registering action listener to buttons
        open.addActionListener(this);
        play.addActionListener(this);
        b_pause.addActionListener(this);
        reset.addActionListener(this);
        resume.addActionListener(this);
    }
	
	Runnable r_play = new Runnable() {
		@Override
		public void run()
		{
			try {
				System.out.println("play button pressed");
				fInputStream = new FileInputStream(mFile);
				bInputStream = new BufferedInputStream(fInputStream);
				player = new Player(bInputStream);
				totalLength = fInputStream.available();
				player.play();
			}
			catch(FileNotFoundException x)
			{
				x.printStackTrace();
			}
			catch (JavaLayerException x)
			{
				x.printStackTrace();
			}
			catch (IOException x)
			{
				x.printStackTrace();
			}
		}
	};
	
	Runnable r_resume = new Runnable() {
		@Override
		public void run() {
			try {
				System.out.println("play button pressed");
				fInputStream = new FileInputStream(mFile);
				bInputStream = new BufferedInputStream(fInputStream);
				player = new Player(bInputStream);
				fInputStream.skip(totalLength-pause);
				player.play();
			}
			catch(FileNotFoundException x)
			{
				x.printStackTrace();
			}
			catch (JavaLayerException x)
			{
				x.printStackTrace();
			}
			catch (IOException x)
			{
				x.printStackTrace();
			}
		}
		
		
	};
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==play)
		{
			System.out.println("Play button pressed");
			new Thread(r_play).start();
		}
			
		
		if(e.getSource()==b_pause)
		{
			System.out.println("Pause button pressed");
			try
			{
				pause = fInputStream.available();
				player.close();
			}
			catch (IOException x)
			{
				x.printStackTrace();
			}
		}
		if(e.getSource()==reset)
		{
			System.out.println("reset button pressed");
			if(player!=null)
			{
				player.close();
				mFile = null;
				fileName = "";
				filePath = "";
			}
			
		}
		
		if(e.getSource()==open)
		{
			System.out.println("open button pressed");
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("/home/anthony"));
			fileChooser.setDialogTitle("Select MP3");
			fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
			if(fileChooser.showOpenDialog(open)==JFileChooser.APPROVE_OPTION)
			{
				mFile=fileChooser.getSelectedFile();
				fileName = fileChooser.getSelectedFile().getName();
				filePath = fileChooser.getSelectedFile().getPath();
				
			}
		}
		if(e.getSource()==resume)
		{
			System.out.println("resume button pressed");
			new Thread(r_resume).start();
		}
		
	}
}
