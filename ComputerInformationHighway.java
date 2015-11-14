public class ComputerInformationHighway {

    public static void main(String[] args) {
        System.out.println("Plaintext: " + (int)('a'));
        String text = "aasdf";
        String key = "hi";
        String initialVector = "bcd";
        
        String encryption = encrypt(text, key, initialVector);
        
        System.out.println(encryption);
        
        String decryption = decrypt(encryption, key, initialVector);
        
        System.out.println(decryption);
    }
        
    
    //HELLO HI
    public static String encrypt(String text, String key, String initialVector) {
        char pText[] = text.replace("[^a-zA-Z]", "").toLowerCase().toCharArray();
        char pKey[] = key.replace("[^a-zA-Z]", "").toLowerCase().toCharArray();
        char pIVector[] = initialVector.replace("[^a-zA-Z]", "").toLowerCase().toCharArray();
        
        int bSize = pKey.length < pIVector.length ? pKey.length : pIVector.length;
        int cInts[] = new int[bSize];
        
        initialVector = "";
        text = "";
        
        for(int x = 0; x < bSize; x++) {
            if(x >= pText.length) {
                cInts[x] = ((Character.getNumericValue('x') - 10) + (Character.getNumericValue(pIVector[x])) - 10)%26;
                System.out.printf("%d\t%d\t%d\n", Character.getNumericValue('x') - 10, Character.getNumericValue(pIVector[x]) - 10, cInts[x]);
            }
            else{
                cInts[x] = ((Character.getNumericValue(pText[x]) - 10) + (Character.getNumericValue(pIVector[x])) - 10)%26;
                System.out.printf("%d\t%d\t%d\n", Character.getNumericValue(pText[x]) - 10, Character.getNumericValue(pIVector[x]) - 10, cInts[x]);
            }
            
            initialVector += Character.forDigit((cInts[x] + Character.getNumericValue(pKey[x]) - 10)%26 + 10, Character.MAX_RADIX);
            System.out.printf("%d\t%d\t%d\t%s\n", cInts[x], Character.getNumericValue(pKey[x]) - 10,(cInts[x] + Character.getNumericValue(pKey[x]) - 10)%26, initialVector);
        }
        
        for(char c : pText)
            text += c;
        
        text = text.length() > bSize ? text.substring(bSize) : "";
        
        if(text.length() == 0)
            return initialVector;
        
        return initialVector + encrypt(text, key, initialVector);
    }

    public static int reverseMod1(int div, int a, int r) {
        
        return ((r-a+div)%div);
        
    }
    
    public static int reverseMod(int div, int a, int r) {
        if(a < r)
            return r-a;
        return (div + r - a)%div >= 0 ? (div + r - a)%div : (div + r - a)%26+26;
    }
    
    public static String decrypt(String text, String key, String initialVector) {
        String pText = "";
        
        char cText[] = text.replace("[^a-zA-Z]", "").toLowerCase().toCharArray();
        char pKey[] = key.replace("[^a-zA-Z]", "").toLowerCase().toCharArray();
        char pIVector[] = initialVector.replace("[^a-zA-Z]", "").toLowerCase().toCharArray();
        
        int bSize = pKey.length < pIVector.length ? pKey.length : pIVector.length;
        int ivInts[] = new int[bSize];
        String tempEnc = "";
        
        for(int x = 0; x < bSize; x++) {
            if(x >= cText.length)
                ivInts[x] = reverseMod1(26, Character.getNumericValue(pKey[x]) - 10, Character.getNumericValue('x') - 10);
            else
                ivInts[x] = reverseMod1(26, Character.getNumericValue(pKey[x]) - 10, Character.getNumericValue(cText[x]) - 10);
            
            int digit = reverseMod1(26, Character.getNumericValue(pIVector[x]) - 10, ivInts[x]) + 10;
            pText += Character.forDigit(digit, Character.MAX_RADIX);
            
            tempEnc += cText[x];
        }
        
        text = text.length() > bSize ? text.substring(bSize) : "";
        
        if(text.length() == 0)
            return pText;
        
        return pText + decrypt(text, key, tempEnc);
        
    }
}