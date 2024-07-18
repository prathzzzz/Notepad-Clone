import java.awt.*;
import java.io.*;

class FileManager {
    private EncryptionManager encryptionManager;

    public FileManager() {
        encryptionManager = new EncryptionManager();
    }

    public void saveFile(NotepadUI ui) {
        String currentFilePath = ui.currentFilePath;
        if (currentFilePath != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
                String fileContent = ui.getTextArea().getText();
                String encryptedContent = encryptionManager.encrypt(fileContent, 4);
                writer.write(encryptedContent);
                writer.flush();
            } catch (IOException e) {
                System.out.println("Error while saving the file: " + e.getMessage());
            }
        } else {
            saveAsDialog(ui);
        }
    }

    public void saveAsDialog(NotepadUI ui) {
        FileDialog fd = new FileDialog(ui, "Save As", FileDialog.SAVE);
        fd.setVisible(true);

        String directory = fd.getDirectory();
        String filename = fd.getFile();

        if (directory != null && filename != null) {
            ui.currentFilePath = directory + filename;
            saveFile(ui);
        }
    }

    public void openFile(NotepadUI ui) {
        FileDialog fd = new FileDialog(ui, "Open", FileDialog.LOAD);
        fd.setVisible(true);

        String directory = fd.getDirectory();
        String filename = fd.getFile();

        if (directory != null && filename != null) {
            ui.currentFilePath = directory + filename;
            try (BufferedReader reader = new BufferedReader(new FileReader(ui.currentFilePath))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                String decryptedContent = encryptionManager.decrypt(sb.toString().trim(), 4);
                ui.getTextArea().setText(decryptedContent);
            } catch (IOException e) {
                System.out.println("Error while opening the file: " + e.getMessage());
            }
        }
    }
}
