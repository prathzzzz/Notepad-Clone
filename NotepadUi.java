import java.awt.*;
import java.awt.event.*;

class NotepadUI extends Frame implements ActionListener {
    private TextArea ta;
    public String currentFilePath, clipboard;
    private MenuItem cut, copy, delete;
    private FileManager fileManager;
    private TextManager textManager;

    public NotepadUI() {
        super("Notepad");
        fileManager = new FileManager();
        textManager = new TextManager(this);
        initUI();
    }

    private void initUI() {
        setSize(300, 300);
        setVisible(true);
        ta = new TextArea(40, 40);
        add(ta);

        MenuBar mbar = new MenuBar();
        setMenuBar(mbar);

        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu viewMenu = new Menu("View");
        Menu zoom = new Menu("Zoom");

        mbar.add(fileMenu);
        mbar.add(editMenu);
        mbar.add(viewMenu);

        fileMenu.add(createMenuItem("New window", KeyEvent.VK_N, true));
        fileMenu.add(createMenuItem("Open", KeyEvent.VK_O));
        fileMenu.add(createMenuItem("Save", KeyEvent.VK_S));
        fileMenu.add(createMenuItem("Save as", KeyEvent.VK_S, true));
        fileMenu.add(new MenuItem("-"));
        fileMenu.add(createMenuItem("Close window", KeyEvent.VK_W, true));
        fileMenu.add(createMenuItem("Exit"));

        editMenu.add(createMenuItem("Undo", KeyEvent.VK_Z));
        cut = createMenuItem("Cut", KeyEvent.VK_X);
        copy = createMenuItem("Copy", KeyEvent.VK_C);
        MenuItem paste = createMenuItem("Paste", KeyEvent.VK_V);
        delete = new MenuItem("Delete");
        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(delete);
        editMenu.add(new MenuItem("-"));
        editMenu.add(createMenuItem("Find", KeyEvent.VK_F));
        editMenu.add(new MenuItem("Find previous"));
        editMenu.add(createMenuItem("Replace", KeyEvent.VK_H));
        editMenu.add(new MenuItem("Go to"));
        editMenu.add(new MenuItem("-"));
        editMenu.add(createMenuItem("Select all", KeyEvent.VK_A));
        editMenu.add(new MenuItem("Time/Date"));
        editMenu.add(new MenuItem("Font"));

        CheckboxMenuItem wordWrap = new CheckboxMenuItem("Word wrap", true);
        viewMenu.add(zoom);
        viewMenu.add(wordWrap);

        zoom.add(new MenuItem("Zoom in"));
        zoom.add(new MenuItem("Zoom out"));
        zoom.add(new MenuItem("Restore default zoom"));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        cut.setEnabled(false);
        copy.setEnabled(false);
    }

    private MenuItem createMenuItem(String title, int key, boolean ctrl) {
        MenuItem menuItem = new MenuItem(title);
        menuItem.setShortcut(new MenuShortcut(key, ctrl));
        menuItem.addActionListener(this);
        return menuItem;
    }

    private MenuItem createMenuItem(String title, int key) {
        return createMenuItem(title, key, false);
    }

    private MenuItem createMenuItem(String title) {
        MenuItem menuItem = new MenuItem(title);
        menuItem.addActionListener(this);
        return menuItem;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Open":
                fileManager.openFile(this);
                break;
            case "Exit":
                System.exit(0);
                break;
            case "New window":
                new NotepadUI().launch();
                break;
            case "Close window":
                dispose();
                break;
            case "Find":
                textManager.findDialog();
                break;
            case "Save":
                fileManager.saveFile(this);
                break;
            case "Save as":
                fileManager.saveAsDialog(this);
                break;
            case "Select all":
                textManager.selectAllText();
                break;
            case "Replace":
                textManager.replaceDialog();
                break;
            case "Cut":
                textManager.cutText();
                break;
            case "Copy":
                textManager.copyText();
                break;
            case "Paste":
                textManager.pasteText();
                break;
        }
        disableMenuItems();
    }

    private void disableMenuItems() {
        String selectedText = ta.getSelectedText();
        cut.setEnabled(selectedText != null);
        copy.setEnabled(selectedText != null);
    }

    public TextArea getTextArea() {
        return ta;
    }

    public void launch() {
        setVisible(true);
    }
}
