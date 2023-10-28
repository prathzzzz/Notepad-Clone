import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class UI extends Frame implements ActionListener {

    TextArea ta;
    Dialog findDialog;
    TextField findTextField;
    Button findButton, findNextButton;
    Button replaceButton, replaceAllButton;
	TextField replaceTextField;
    String key;
    int startIndex, findNextStartIndex,replaceIndex;
    String currentFilePath,clipboard;
    MenuItem cut,copy,delete;

    UI() {
        super("Notepad");
        setSize(300, 300);
        setVisible(true);

        MenuBar mbar = new MenuBar();
        setMenuBar(mbar);

        Menu FileMenu = new Menu("File");
        Menu EditMenu = new Menu("Edit");
        Menu ViewMenu = new Menu("View");
        Menu zoom = new Menu("Zoom");

        mbar.add(FileMenu);
        mbar.add(EditMenu);
        mbar.add(ViewMenu); 

        MenuItem newWindow = new MenuItem("New window");
        newWindow.setShortcut(new MenuShortcut(KeyEvent.VK_N, true));
        MenuItem open = new MenuItem("Open");
        open.setShortcut(new MenuShortcut(KeyEvent.VK_O));
        MenuItem save = new MenuItem("Save");
        save.setShortcut(new MenuShortcut(KeyEvent.VK_S));
        MenuItem saveAs = new MenuItem("Save as");
        saveAs.setShortcut(new MenuShortcut(KeyEvent.VK_S, true));

        MenuItem dash1 = new MenuItem("-");

        MenuItem closeWindow = new MenuItem("Close window");
        closeWindow.setShortcut(new MenuShortcut(KeyEvent.VK_W, true));
        MenuItem exit = new MenuItem("Exit");

        FileMenu.add(newWindow);
        FileMenu.add(open);
        FileMenu.add(save);
        FileMenu.add(saveAs);
        FileMenu.add(dash1);
        FileMenu.add(closeWindow);
        FileMenu.add(exit);

        MenuItem undo = new MenuItem("Undo");
        undo.setShortcut(new MenuShortcut(KeyEvent.VK_Z));
         cut = new MenuItem("Cut");
        cut.setShortcut(new MenuShortcut(KeyEvent.VK_X));
        copy = new MenuItem("Copy");
        copy.setShortcut(new MenuShortcut(KeyEvent.VK_C));
        MenuItem paste = new MenuItem("Paste");
        paste.setShortcut(new MenuShortcut(KeyEvent.VK_V));
        delete = new MenuItem("Delete");
        MenuItem dash3 = new MenuItem("-");
        MenuItem find = new MenuItem("Find");
        find.setShortcut(new MenuShortcut(KeyEvent.VK_F));
        MenuItem findPrevious = new MenuItem("Find previous");
        MenuItem replace = new MenuItem("Replace");
        replace.setShortcut(new MenuShortcut(KeyEvent.VK_H));
        MenuItem goTo = new MenuItem("Go to");
        MenuItem dash4 = new MenuItem("-");
        MenuItem selectAll = new MenuItem("Select all");
        selectAll.setShortcut(new MenuShortcut(KeyEvent.VK_A));
        MenuItem timeDate = new MenuItem("Time/Date");
        MenuItem font = new MenuItem("Font");

        EditMenu.add(undo);
        EditMenu.add(cut);
        EditMenu.add(copy);
        EditMenu.add(paste);
        EditMenu.add(delete);
        EditMenu.add(dash3);
        EditMenu.add(find);
        EditMenu.add(findPrevious);
        EditMenu.add(replace);
        EditMenu.add(goTo);
        EditMenu.add(dash4);
        EditMenu.add(selectAll);
        EditMenu.add(timeDate);
        EditMenu.add(font);

        CheckboxMenuItem wordWrap = new CheckboxMenuItem("Word wrap", true);

        ViewMenu.add(zoom);
        ViewMenu.add(wordWrap);

        MenuItem zoomIn = new MenuItem("Zoom in");
        MenuItem zoomOut = new MenuItem("Zoom out");
        MenuItem restoreZoom = new MenuItem("Restore default zoom");

        zoom.add(zoomIn);
        zoom.add(zoomOut);
        zoom.add(restoreZoom);

        ta = new TextArea(40, 40);
        
        add(ta);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        FileMenu.addActionListener(this);
        EditMenu.addActionListener(this);
        ViewMenu.addActionListener(this);

        cut.setEnabled(false);
        copy.setEnabled(false);
    }

   

    public void actionPerformed(ActionEvent e) {
        String currentTask = e.getActionCommand();
        if (currentTask.equals("Open")) {
            openDialog();
        } else if (currentTask.equals("Exit")) {
            System.exit(0);
        } else if (currentTask.equals("New window")) {
            new UI();
        } else if (currentTask.equals("Close window")) {
            dispose();
        } else if (currentTask.equals("Find")) {
            findDialog();
        } else if (currentTask.equals("Findd")) {
            findText();
        } else if (currentTask.equals("Find Next")) {
            findNextText();
        } else if (currentTask.equals("Save")) {
            saveFile();
        } else if (currentTask.equals("Save as")) {
            saveAsDialog();
        } else if (currentTask.equals("Select all")) {
            selectAllText();
        } else if(currentTask.equals("Replace")){
        	replaceDialog();
        } else if(currentTask.equals("Replacee")){
        	replaceText();
        } else if (currentTask.equals("Replace All")) {
		    replaceAll();
		} else if(currentTask.equals("Cut")){
            cutText();
        } else if(currentTask.equals("Paste")){
            pasteText();
        } else if(currentTask.equals("Copy")){
            copyText();
        }
        disableMenuItems();
    }

    private void disableMenuItems(){
        String selectedText = ta.getSelectedText();
        if(selectedText!=null){
            cut.setEnabled(true);
            copy.setEnabled(true);
        }
    }
    private void cutText() {
        String selectedText = ta.getSelectedText();
        if (selectedText != null) {
            clipboard = selectedText;
            ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());
        }
    }

     private void copyText() {
        String selectedText = ta.getSelectedText();
        if (selectedText != null) {
            clipboard = selectedText;
        }
    }
   
    private void pasteText() {
        if (clipboard != null) {
            int caretPosition = ta.getCaretPosition();
            ta.insert(clipboard, caretPosition);
        }
    }

    private void selectAllText() {
        ta.requestFocus();
        ta.selectAll();
    }



    private void replaceDialog() {
	    Dialog replaceDialog = new Dialog(this, "Replace");
	    replaceDialog.setSize(500, 150);
	    replaceDialog.setLayout(new FlowLayout());
	    replaceDialog.setResizable(false);

	    replaceTextField = new TextField(20);
	    findTextField = new TextField(20);
	    Button replaceButton = new Button("Replacee");
	    Button replaceAllButton = new Button("Replace All");
	    replaceButton.addActionListener(this);
	    replaceAllButton.addActionListener(this);

	    replaceDialog.add(new Label("Find:"));
	    replaceDialog.add(findTextField);
	    replaceDialog.add(new Label("Replace with:"));
	    replaceDialog.add(replaceTextField);
	    replaceDialog.add(replaceButton);
	    replaceDialog.add(replaceAllButton);

	    replaceDialog.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
	            replaceDialog.dispose();
	        }
	    });

    replaceDialog.setVisible(true);
}

    private void replaceText() {
        String key = findTextField.getText();
        int selectedTextIndex = ta.getText().indexOf(key);
        if (selectedTextIndex != -1) {
            ta.replaceRange(replaceTextField.getText(), selectedTextIndex, selectedTextIndex + key.length());
        }
        else{
            textNotFoundDialog();
        }
    }

    private void replaceAll() {
        String key = findTextField.getText();
        String replacement = replaceTextField.getText();

      
        String documentText = ta.getText();
        String modifiedText = documentText.replace(key, replacement);


        ta.setText(modifiedText);
    }


   private void saveFile() {
    if (currentFilePath != null) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
            String fileContent = ta.getText();
            writer.write(fileContent);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error while saving the file: " + e.getMessage());
        }
    } else {	
        saveAsDialog();
    }
}

    private void saveAsDialog() {
        FileDialog fd = new FileDialog(this, "Save As", FileDialog.SAVE);
        fd.setVisible(true);

        if (fd.getDirectory() != null && fd.getFile() != null) {
            String filePath = fd.getDirectory() + fd.getFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                String fileContent = ta.getText();
                writer.write(fileContent);
                writer.flush();
            } catch (IOException e) {
                System.out.println("Error while saving the file: " + e.getMessage());
            }
           	currentFilePath = filePath; 
        }
    }
  private void findNextText() {
        String key = findTextField.getText();
        int caretPosition = ta.getCaretPosition();

        String text = ta.getText();
        int index = text.indexOf(key, caretPosition + 1); 

        if (index != -1) {
            ta.select(index, index + key.length());
        } else {
            
            index = text.indexOf(key);

            if (index != -1) {
                ta.select(index, index + key.length());
            }
        }

        findNextStartIndex = index; 
        ta.requestFocus();
}


    private void findText() {
        key = findTextField.getText();
        startIndex = ta.getText().indexOf(key);
        ta.requestFocus();
        if (startIndex != -1) {
            ta.select(startIndex, startIndex + key.length());
        } else {
            textNotFoundDialog();
        }
    }

    private void textNotFoundDialog() {
        Dialog textDialog = new Dialog(this, "Notepad");
        textDialog.setSize(200, 100);
        textDialog.setLayout(new FlowLayout());
        textDialog.setResizable(false);

        Label label = new Label("Text Not Found");
        Button okButton = new Button("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textDialog.dispose();
            }
        });

        textDialog.add(label);
        textDialog.add(okButton);
        textDialog.setVisible(true);
    }


    private void showFileNotFoundErrorDialog() {
        Dialog errorDialog = new Dialog(this, "Error");
        errorDialog.setSize(400, 300);
        errorDialog.setLayout(new FlowLayout());
        errorDialog.setResizable(false);

        Label label = new Label("File Not found, check file name and try again!");
        Button okButton = new Button("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                errorDialog.dispose();
                openDialog();
            }
        });

        errorDialog.add(label);
        errorDialog.add(okButton);
        errorDialog.setVisible(true);
    }


	private void openDialog() {
        FileDialog fd = new FileDialog(this, "Open", FileDialog.LOAD);
        fd.setVisible(true);

        if (fd.getDirectory() != null && fd.getFile() != null) {
            String filePath = fd.getDirectory() + fd.getFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                String fileContent = "";
                while ((line = reader.readLine()) != null) {
                    fileContent += line + "\n";
                }

                ta.setText(fileContent);
                currentFilePath = filePath;

            } catch (FileNotFoundException e) {
                showFileNotFoundErrorDialog();
             
            } catch (IOException e) {
                System.out.println("Error while reading the file: " + e.getMessage());
            }
        }
    }



    private void findDialog() {
        findDialog = new Dialog(this, "Find");
        findDialog.setSize(500, 130);
        findDialog.setLayout(new FlowLayout());
        findDialog.setResizable(false);

        findTextField = new TextField(20);
        findButton = new Button("Findd");
        findNextButton = new Button("Find Next");
        findButton.addActionListener(this);
        findNextButton.addActionListener(this);

        findDialog.add(new Label("Find:"));
        findDialog.add(findTextField);
        findDialog.add(findButton);
        findDialog.add(findNextButton);

        findDialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                findDialog.dispose();
            }
        });

        findDialog.setVisible(true);
    }
}

