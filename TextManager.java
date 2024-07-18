import java.awt.*;
import java.awt.event.*;
class TextManager {
    private NotepadUI ui;

    public TextManager(NotepadUI ui) {
        this.ui = ui;
    }

    public void findDialog() {
        Dialog findDialog = new Dialog(ui, "Find");
        findDialog.setSize(500, 150);
        findDialog.setLayout(new FlowLayout());
        findDialog.setResizable(false);

        TextField findTextField = new TextField(20);
        Button findButton = new Button("Find");
        Button findNextButton = new Button("Find Next");

        findButton.addActionListener(e -> findText(findTextField.getText()));
        findNextButton.addActionListener(e -> findNextText(findTextField.getText()));

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

    public void findText(String key) {
        String documentText = ui.getTextArea().getText();
        int startIndex = documentText.indexOf(key);

        if (startIndex != -1) {
            ui.getTextArea().setCaretPosition(startIndex);
            ui.getTextArea().select(startIndex, startIndex + key.length());
        } else {
            textNotFoundDialog();
        }
    }

    public void findNextText(String key) {
        String documentText = ui.getTextArea().getText();
        int startIndex = documentText.indexOf(key, ui.getTextArea().getCaretPosition());

        if (startIndex != -1) {
            ui.getTextArea().setCaretPosition(startIndex);
            ui.getTextArea().select(startIndex, startIndex + key.length());
        } else {
            textNotFoundDialog();
        }
    }

    public void textNotFoundDialog() {
        Dialog textNotFoundDialog = new Dialog(ui, "Error");
        textNotFoundDialog.setSize(300, 150);
        textNotFoundDialog.setLayout(new FlowLayout());
        textNotFoundDialog.setResizable(false);

        Label errorLabel = new Label("The text was not found.");
        Button okButton = new Button("OK");

        okButton.addActionListener(e -> textNotFoundDialog.dispose());

        textNotFoundDialog.add(errorLabel);
        textNotFoundDialog.add(okButton);

        textNotFoundDialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                textNotFoundDialog.dispose();
            }
        });

        textNotFoundDialog.setVisible(true);
    }

    public void replaceDialog() {
        Dialog replaceDialog = new Dialog(ui, "Replace");
        replaceDialog.setSize(500, 150);
        replaceDialog.setLayout(new FlowLayout());
        replaceDialog.setResizable(false);

        TextField findTextField = new TextField(20);
        TextField replaceTextField = new TextField(20);
        Button replaceButton = new Button("Replace");
        Button replaceAllButton = new Button("Replace All");

        replaceButton.addActionListener(e -> replaceText(findTextField.getText(), replaceTextField.getText()));
        replaceAllButton.addActionListener(e -> replaceAllText(findTextField.getText(), replaceTextField.getText()));

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

    public void replaceText(String findText, String replaceText) {
        String documentText = ui.getTextArea().getText();
        int startIndex = documentText.indexOf(findText);

        if (startIndex != -1) {
            ui.getTextArea().replaceRange(replaceText, startIndex, startIndex + findText.length());
        } else {
            textNotFoundDialog();
        }
    }

    public void replaceAllText(String findText, String replaceText) {
        String documentText = ui.getTextArea().getText();
        documentText = documentText.replace(findText, replaceText);
        ui.getTextArea().setText(documentText);
    }

    public void selectAllText() {
        ui.getTextArea().selectAll();
    }

    public void cutText() {
        ui.clipboard = ui.getTextArea().getSelectedText();
        ui.getTextArea().replaceRange("", ui.getTextArea().getSelectionStart(), ui.getTextArea().getSelectionEnd());
    }

    public void copyText() {
        ui.clipboard = ui.getTextArea().getSelectedText();
    }

    public void pasteText() {
        if (ui.clipboard != null) {
            ui.getTextArea().insert(ui.clipboard, ui.getTextArea().getCaretPosition());
        }
    }
}
