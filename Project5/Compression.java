package Project5;

import java.io.*;
import java.util.ArrayList;

public class Compression {
    private LinkedList[] hashTable = new LinkedList[256];
    private static final int TABLE = 256;
    private int tableLength = 256;
    private static final int LIMITATION = (int) Math.pow(2,12);
    private int readByte = 0;
    private int writeByte = 0;

//    Initialization of the hashtable, will be called at the start and every time when overflow

    private void initialization(){
        for (int i = 0; i < 256; i++) {
            hashTable[i] = new LinkedList();
        }
        for (int i = 0; i < 256; i++) {
            char c = (char) i;
            int y = Math.abs(Character.valueOf(c).hashCode());
            int index = y % TABLE;
            hashTable[index].addAtEndNode(String.valueOf(c), i);
        }
    }

//    Constructor

    public Compression() {
        initialization();
    }

    public int getReadByte(){ return readByte; }

    public int getWriteByte() { return writeByte; }

//    Read the file to be compressed in byte

    public ArrayList readFile(String file) throws Exception {
        DataInputStream in =
                new DataInputStream(
                        new BufferedInputStream(
                                new FileInputStream(file)));


        byte i = in.readByte();
        readByte ++;
        char c = (char) (((char) i) & 0xFF);
        String s = ""+c;
        ArrayList<Byte> r = new ArrayList<>();

        try {
            while (true) {
                i = in.readByte();
                readByte ++;
                c = (char) (((char) i) & 0xFF);
                String conca = s + c;
                int index = Math.abs(conca.hashCode()) % TABLE;
//                If the concatenated string is in the table give s the concatenated string
                if (hashTable[index].getNodeValue(conca) != -1) {
                    s = conca;
//                    If the concatenated string is not in the table, enter the concatenated string and
//                    then output s.
                } else {
                    int t = Math.abs(s.hashCode()) % TABLE;
                    int value = hashTable[t].getNodeValue(s);
//                    Bit manipulation to store 2 bytes
                    value = value << 4;
                    short v = (short) value;
                    byte c1 = (byte) (0xFF & v);
                    byte c2 = (byte) (0xFF & (v >>> 8));
                    r.add(c2);
                    r.add(c1);
                    writeByte = writeByte + 2;
                    hashTable[index].addAtEndNode(conca, tableLength);
                    tableLength++;
                    if (tableLength == LIMITATION) {
                        initialization();
                        tableLength = 256;
                    }
                    s = ""+c;
                }
            }
        } catch (EOFException e) {
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int o = Math.abs(s.hashCode()) % TABLE;
        int value = hashTable[o].getNodeValue(s);
        value = value << 4;
        short v = (short) value;
        byte c1 = (byte) (0xFF & v);
        byte c2 = (byte) (0xFF & (v >>> 8));

        r.add(c2);
        r.add(c1);
        writeByte = writeByte + 2;
        return r;
    }


//    Output compressed content into specified file

    public void outputFile(ArrayList<Byte> compressed, String fileName) {
        File file = new File(fileName);
        byte[] bytes = new byte[compressed.size()];
        for (int i=0;i<compressed.size();i++){
            bytes[i] = compressed.get(i);
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
