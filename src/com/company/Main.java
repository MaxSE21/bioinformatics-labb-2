package com.company;
import java.io.File;
import java.io.FileNotFoundException;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {



       String path1;
        path1 = "E:\\Programming\\Java stuff\\bioinformatics-labb-3\\bioinformatics-labb-3\\src\\files\\1cdh.pdb";
        File file1 = new File(path1);

        String path2;
        path2 = "E:\\Programming\\Java stuff\\bioinformatics-labb-3\\bioinformatics-labb-3\\src\\files\\2csn.pdb";
        File file2 = new File(path2);

        Labb3ProgramV2 Labb3ProgramV2 = new Labb3ProgramV2();
        Labb3ProgramV2.compare_file(file1, file2);

        
    }



}




