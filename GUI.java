import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.io.*;

public class GUI extends JFrame{
	// GUI components
	private JTabbedPane pane; 
	
	private Container cont; 
	private JPanel wholePanel; 
	private JPanel secondWholePanel;
	private JPanel topPanel; 
	private JPanel bottomPanel; 
	
	private JPanel leftPanel; 
	private JPanel rightPanel; 
	
	private JLabel messageLabel; 
	private JLabel resultLabel; 
	
	private JTextArea leftArea; 
	private JTextArea rightArea;
	
	private ButtonGroup butnGroup; 
	private JRadioButton encryptButton; 
	private JRadioButton decryptButton; 
	
	private JLabel passwordLabel; 
	private JTextField passwordField; 
	private JButton goButton; 
	
	// other components that were added in an wrtie as you go style lol
	private JTextField filePasswordField = new JTextField(10);
	private JLabel selectedFileLabel = new JLabel();	
	private JButton findButton = new JButton("Find"); 
	private final JFileChooser fileChooser = new JFileChooser(); 
	private File fileToTranslate = null; 
	private JButton sButton = new JButton("Export");
	
	private JTextField fileNameField = new JTextField(10); 
	private ButtonGroup fileButtonGroup = new ButtonGroup(); 
	private JRadioButton fileEncryptButton = new JRadioButton("Encrypt", true); 
	private JRadioButton fileDecryptButton = new JRadioButton("Decrypt"); 
	private JLabel filePasswordLabel = new JLabel("Passoword "); 
	private JLabel fileNameFileLabel = new JLabel("File Name "); 
	
	
	GUI(){
		setUp(); // method was convinent for initalizing all of my components before use 
		
		wholePanel.setLayout(new BorderLayout());
		
		topPanel.setLayout(new FlowLayout());
		topPanel.add(leftPanel);
		
		// poor use of the GridBagLayout to setup the main tab of my app
		leftPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); 
		c.gridx = 0;
		c.gridy = 0; 
		
		leftPanel.add(messageLabel, c);
		c.gridy++; 
		leftPanel.add(leftArea, c); 
		c.gridy++;
		
		c.anchor = GridBagConstraints.LINE_START; 
		leftPanel.add(encryptButton, c);
		c.gridy++; 
		leftPanel.add(decryptButton, c);
		
		
		rightPanel.setLayout(new GridBagLayout()); 
		GridBagConstraints d = new GridBagConstraints(); 
		d.gridx = d.gridy = 0; 
		
		rightPanel.add(resultLabel, d);
		d.gridy++; 
		rightPanel.add(rightArea, d);
		
		topPanel.add(rightPanel);

		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints e = new GridBagConstraints(); 
		e.gridx = e.gridy = 0; 
		
		bottomPanel.add(passwordLabel, e);
		e.gridx++; 
		bottomPanel.add(passwordField, e);
		e.gridx++; 
		bottomPanel.add(goButton, e); 
		
		
		wholePanel.add(topPanel, BorderLayout.CENTER);
		wholePanel.add(bottomPanel, BorderLayout.SOUTH);
		
		secondWholePanel.setLayout(new FlowLayout()); 
		
		
		// second panel code starts here 
		// just used the simple FlowLayout

		Run2 run2 = new Run2(); 
		Run3 run3 = new Run3(); 
		
		findButton.addActionListener(run2);
		sButton.addActionListener(run3);
		
		secondWholePanel.add(findButton);
		secondWholePanel.add(selectedFileLabel);
		secondWholePanel.add(filePasswordLabel);
		secondWholePanel.add(filePasswordField);
		
		fileButtonGroup.add(fileEncryptButton);
		fileButtonGroup.add(fileDecryptButton);
		
		secondWholePanel.add(fileEncryptButton);
		secondWholePanel.add(fileDecryptButton);
		
		secondWholePanel.add(fileNameFileLabel);
		secondWholePanel.add(fileNameField);
		secondWholePanel.add(sButton); 
		
		pane.add("Single", wholePanel); 
		pane.add("File", secondWholePanel);
		
		cont.add(pane);
		

		setTitle("Message Encryption"); 
		setSize(850, 600); 
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setVisible(true);
	}
	

	// Method to initalize all components
	private void setUp(){
		pane = new JTabbedPane();
		cont = getContentPane();
		wholePanel = new JPanel();
		secondWholePanel = new JPanel();
		topPanel = new JPanel(); 
		bottomPanel = new JPanel(); 
		leftPanel = new JPanel(); 
		rightPanel = new JPanel(); 
		
		messageLabel = new JLabel("Message"); 
		resultLabel = new JLabel("Result"); 
		
		leftArea = new JTextArea(30, 30); 
		leftArea.setLineWrap(true);
		
		rightArea = new JTextArea(30, 30); 
		rightArea.setEditable(false);
		rightArea.setLineWrap(true);
		
		encryptButton = new JRadioButton("Encrypt", true); 
		decryptButton = new JRadioButton("Decrypt"); 

		butnGroup = new ButtonGroup(); 
		butnGroup.add(encryptButton);
		butnGroup.add(decryptButton);
		
		passwordLabel = new JLabel("Password "); 
		passwordField = new JTextField(12); 
		goButton = new JButton("Go!");
		
		Run listener = new Run(); 
		goButton.addActionListener(listener);

	}
	// inner class runs when the Go button on the first tab is pressed
	class Run implements ActionListener{
		public void actionPerformed(ActionEvent e){
			rightArea.setText("");
			String password = passwordField.getText(); 
			
			String textToEncrypt = leftArea.getText();
			
			if(encryptButton.isSelected()){
				rightArea.setText(Encryption.encrypt(textToEncrypt, password));
			}
			
			else{
				rightArea.setText(Encryption.decrypt(textToEncrypt, password));
			}
		}
	}
	// Run2 pulls up a JFileChooser to get text file user wants to edit
	class Run2 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int returnVal = fileChooser.showOpenDialog(secondWholePanel);
			
			if(returnVal == JFileChooser.APPROVE_OPTION){
				fileToTranslate = fileChooser.getSelectedFile();
				selectedFileLabel.setText(fileToTranslate.getName());
				
			}
			else{
				System.out.println("No file choosen");
			}
			
		}
	}
	
	// run 3 actually creates a new encrypted file based on the password the user enters and allows the user to name the file
	class Run3 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			FileReader reader = null; 
			BufferedReader theReader = null; 
			
			FileWriter writer = null; 
			BufferedWriter theWriter = null; 
			
			String fileName = fileNameField.getText() + ".txt";  
			try{
				reader = new FileReader(fileToTranslate); 
				theReader = new BufferedReader(reader); 
				
				writer = new FileWriter(fileName); 
				theWriter = new BufferedWriter(writer); 
				
				String password = filePasswordField.getText();
				
				String content = ""; 
				
				if(fileEncryptButton.isSelected()){
					while(theReader.ready()){
					content = theReader.readLine(); 
					theWriter.write(Encryption.encrypt(content, password));
					theWriter.newLine();
					}
				}

				else{
					while(theReader.ready()){
					content = theReader.readLine(); 
					theWriter.write(Encryption.decrypt(content, password));
					theWriter.newLine();
					}
				}


				/*
				while(theReader.ready()){
					content = theReader.readLine(); 
					
					theWriter.write(Encryption.encrypt(content, password));
					theWriter.newLine();
					
				}
					
					String finalStuff = "";
					
					if(fileEncryptButton.isSelected()){
						finalStuff = Encryption.encrypt(content, password);
					}
					else{
						finalStuff = Encryption.decrypt(content, password);
					}
					theWriter.write(finalStuff);
					*/
				
			} 
			catch(Exception exc){
				
			}
			finally{
				try{
					theWriter.close();
					theReader.close();
				}
				catch(Exception exc){
					
				}
			}
		}
	}
}
