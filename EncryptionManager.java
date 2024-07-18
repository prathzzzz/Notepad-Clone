class EncryptionManager {
    public String encrypt(String content, int shift) {
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base + shift) % 26 + base);
            }
            cipherText.append(ch);
        }
        return cipherText.toString();
    }

    public String decrypt(String cipherText, int shift) {
        return encrypt(cipherText, 26 - shift);
    }
}
