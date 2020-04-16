package Project5;

import java.io.*;
import java.util.ArrayList;


public class Decompression {
    private ArrayList<String> hashTable = new ArrayList<>();
    private static final int LIMITATION = (int) Math.pow(2, 12);
    private int tableLength = 256;
    private int readByte = 0;
    private int writeByte = 0;

    //    Initialization of the hashtable, called when at the start and every time with overflow
    private void initialization() {
        hashTable = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            this.hashTable.add(String.valueOf((char) i));
        }
    }

    public Decompression() {
        initialization();
    }

    public int getReadByte() {
        return readByte;
    }

    public int getWriteByte() {
        return writeByte;
    }

    //    Read the compressed file in byte
    public void readFile(String fileName, String outputFile) throws IOException {
        DataInputStream in =
                new DataInputStream(
                        new BufferedInputStream(
                                new FileInputStream(fileName)));

        DataOutputStream out =
                new DataOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(outputFile)));



//        Each time read two bytes and conduct bit manipulation and write output into specified file
        byte b1 = in.readByte();
        byte b2 = in.readByte();
        readByte = readByte + 2;
        int c1 = (((int) b1) & 0xFF);
        int c2 = (((int) b2) & 0xFF);

        c1 = c1 << 8;
        int f = ((c1 | c2) >>> 4) & 0xFFF;
        String priorcodeword = hashTable.get(f);
        out.writeBytes(priorcodeword);

        String codeword = "";
        try {
            while (true) {
                b1 = (in.readByte());
                b2 = (in.readByte());
                readByte = readByte + 2;
                c1 = ((int) b1) & 0xFF;
                c2 = ((int) b2) & 0xFF;
                c1 = c1 << 8;
                f = (c1 | c2);
                f = (f >>> 4) & 0xFFF;
//                If the new string is not in the table
                if (f >= tableLength) {
//                  Enter the new string into table
                    codeword = priorcodeword + priorcodeword.charAt(0);
                    tableLength++;
                    hashTable.add(codeword);
//                    Detect overflow
                    if (tableLength == LIMITATION) {
                        initialization();
                        tableLength = 256;
                    }
//                    Output the string
                    out.writeBytes(codeword);
                    writeByte = writeByte+codeword.getBytes().length;

//              If the string is in the table
                } else {
                    codeword = hashTable.get(f);
                    String enter = priorcodeword + codeword.charAt(0);
//                    Enter string into table
                    tableLength++;
                    hashTable.add(enter);
//                    Detect overflow
                    if (tableLength == LIMITATION) {
                        initialization();
                        tableLength = 256;
                    }
                    out.writeBytes(codeword);
                    writeByte = writeByte+codeword.getBytes().length;
                }
                priorcodeword = codeword;
            }
        } catch (EOFException e) {
            in.close();
        }
        out.flush();

    }

}
