package Project5;


import java.io.*;
import java.util.ArrayList;

public class LZWCompression {

    private ArrayList compressedFile;
    private int readBytes = 0;
    private int writeBytes = 0;

    /**
     * The program works the same on text file and binary files.
     * First read the file to be compressed in byte. The compression algorithm will maintain a hashtable
     * to keep track of the encountered string. If overflow happens, the table will be dumped and build a new hashtable
     * If the new string is in the table, enter the new string into the table and output previous word. The decompression is similar
     * but in reverse order. There are two variables to keep track of the pre word and current word.
     * And the results are pretty good. All shortwords.txt , words.html and CrimeLatLonXY1990.csv and binary files
     * can work fine.
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        LZWCompression lzw = new LZWCompression();
        int inputLen = args.length;
        String inputFile = args[inputLen - 2];
        String outputFile = args[inputLen - 1];
//        Compression
        if (args[0].equals("-c")) {
            Compression compressor = new Compression();
            lzw.compressedFile = compressor.readFile(inputFile);
            lzw.readBytes = compressor.getReadByte();
            lzw.writeBytes = compressor.getWriteByte();
            compressor.outputFile(lzw.compressedFile, outputFile);
//            Decompression
        } else if (args[0].equals("-d")) {
            Decompression decompressor = new Decompression();
            decompressor.readFile(inputFile,outputFile);
            lzw.readBytes = decompressor.getReadByte();
            lzw.writeBytes = decompressor.getWriteByte();
        }
//        Verbose switch
        if (args.length == 4) {
            System.out.println(String.format("bytes read = %d, bytes written = %d", lzw.readBytes, lzw.writeBytes));
        }
    }
}
