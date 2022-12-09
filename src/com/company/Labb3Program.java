package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//THE PLAN
//: Take one coordinate form one file. Put it against all the other coordinates. If overlap. Get info on coordinate and print atoms info.
//  Take the serial number of the ones that have already been clashed  with, if that one is in the list of already clashed atoms
//  it should not print.

public class Labb3Program {

    double x1;
    double y1;
    double z1;

    double x2;
    double y2;
    double z2;

    public int overlaps;
    int checks;
    int linechecks1;

    private String file1;
    private String file2;

    String serialNumber;
    String atomName;
    String atomType;
    String chainIdentifier;

    public boolean keepGoing;

    public Labb3Program() throws FileNotFoundException {

        serialNumber = " ";
        atomName = " ";
        atomType = " ";
        chainIdentifier = " ";

        checks = 0;
        keepGoing = true;
        overlaps = 0;

        file1 = "E:\\Programming\\Java stuff\\bioinformatics-labb-3\\bioinformatics-labb-3\\src\\files\\1cdh.pdb";
        file2 = "E:\\Programming\\Java stuff\\bioinformatics-labb-3\\bioinformatics-labb-3\\src\\files\\2csn.pdb";

        this.compareFile(file1, file2);

        System.out.println();
        System.out.println(linechecks1);
        System.out.println("Number of clashing atoms: " + overlaps);
        System.out.println("Number of comparisons: " + checks);
        System.out.println();
    }


    public boolean checkIfOverlaps(){

        double delta;
        delta = (Math.sqrt(Math.pow((x2 - x1), 2)) + Math.pow((y2 - y1), 2) + Math.pow((z2 - z1), 2));

        if (delta >= 4){
            return false;
        }else {
            return true;
        }

    }


    public void compareFile(String file1, String file2){

        try {
            System.out.println("Calculating overlap...");
            Scanner scanner;
            File file = new File(file1);
            scanner = new Scanner(file);
            String lineContent = "";

            while (scanner.hasNextLine()) {

                serialNumber = " ";
                atomName = " ";
                atomType = " ";
                chainIdentifier = " ";

                int spaceCount = 0;

                lineContent = scanner.nextLine();

                if ((lineContent.startsWith("ATOM") || lineContent.startsWith("HETATM"))) {

                    String Xcoordinate = "";
                    String Ycoordinate = "";
                    String Zcoordinate = "";

                    for (int i = 0; i < (lineContent.length()-1); i++) {

                        if (spaceCount > 1){
                            if (!(lineContent.charAt(i-1) == ' ') && !(lineContent.charAt(i+1) == ' ') && (lineContent.charAt(i) == ' ')) {
                                spaceCount--;
                            }
                        }

                        //Adds a space for every
                        if((lineContent.charAt(i) == ' ') && (!(lineContent.charAt(i+1) == ' '))){
                            spaceCount++;
                        }

                        //Gets serial number
                        while (((spaceCount == 1) && (lineContent.charAt(i) != ' '))){
                            i++;
                            serialNumber += (lineContent.charAt(i-1));
                        }
                        //Gets atom name
                        while (((spaceCount == 2) && (lineContent.charAt(i) != ' '))){
                            i++;
                            atomName += (lineContent.charAt(i-1));
                        }
                        //Gets atom type
                        while (((spaceCount == 3) && (lineContent.charAt(i) != ' '))){
                            atomType += (lineContent.charAt(i-1));
                        }
                        //gets chain identifier
                        while (((spaceCount == 4) && (lineContent.charAt(i) != ' '))){
                            i++;
                            chainIdentifier += (lineContent.charAt(i-1));
                        }

                        //Gets X coordinate
                        while (((spaceCount == 6) && (lineContent.charAt(i) != ' '))){
                            i++;
                            Xcoordinate += lineContent.charAt(i-1);
                        }
                        //Gets Y coordinate
                        while (((spaceCount == 7) && (lineContent.charAt(i) != ' '))){
                            i++;
                            Ycoordinate += lineContent.charAt(i-1);
                        }
                        //Gets Z coordinate
                        while (((spaceCount == 8 && (lineContent.charAt(i+1) != ' ')))){
                            i++;
                            Zcoordinate += lineContent.charAt(i-1);

                            if (lineContent.charAt(i+1) == ' '){
                                Zcoordinate += lineContent.charAt(i);
                            }
                        }

                        if (spaceCount >= 8){
                            x1 = Double.parseDouble((Xcoordinate));
                            y1 = Double.parseDouble((Ycoordinate));
                            z1 = Double.parseDouble((Zcoordinate));
                            compareToOtherData(file2);
                            keepGoing = true;
                            break;
                        }

                        //End of for loop
                    }
                   //End of if line has ATOM
                }
                //end of while has line loop
            }
            //End of try
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }


    //I believe the  problem with  this  one is that it checks thrugh liswt when it should only check the one thingyu
    public void compareToOtherData(String file2){

        try {
            Scanner scanner2;
            File file = new File(file2);
            scanner2 = new Scanner(file);

            String lineContent = "";

            while (scanner2.hasNextLine() && keepGoing) {

                lineContent = scanner2.nextLine();

                if ((lineContent.startsWith("ATOM") || lineContent.startsWith("HETATM"))) {

                    String Xcoordinate = "";
                    String Ycoordinate = "";
                    String Zcoordinate = "";

                    int spaceCount = 0;

                    for (int i = 0; i < (lineContent.length())-1; i++) {

                        if((lineContent.charAt(i) == ' ') && !(lineContent.charAt(i+1) == ' ')){
                            spaceCount++;
                        }

                        //For coordinates
                        while (((spaceCount == 6) && (lineContent.charAt(i) != ' '))){
                            i++;
                            Xcoordinate += lineContent.charAt(i-1);
                        }
                        while (((spaceCount == 7) && (lineContent.charAt(i) != ' '))){
                            i++;
                            Ycoordinate += lineContent.charAt(i-1);
                        }
                        while (((spaceCount == 8) && (lineContent.charAt(i+1) != ' '))){
                            i++;
                            Zcoordinate += lineContent.charAt(i-1);
                            if (lineContent.charAt(i+1) == ' '){
                                Zcoordinate += lineContent.charAt(i);
                            }
                        }

                        if (spaceCount >= 8){

                            x2 = Double.parseDouble((Xcoordinate));
                            y2 = Double.parseDouble((Ycoordinate));
                            z2 = Double.parseDouble((Zcoordinate));
                            //System.out.println("serial number: (" + serialNumber + ") Atom type: (" + atomType + ") Chain Identifier: (" + chainIdentifier + ") Atom name: ("+ atomName+") ");


                            if (checkIfOverlaps()){
                                overlaps++;
                                keepGoing = false;

                                System.out.println(serialNumber + " " + atomType + " " + chainIdentifier + " " + atomName);
                            }

                            checks++;

                            break;
                        }
                        //End of for loop
                    }
                    //End of if line has ATOM
                }
                //end of while has line loop
            }
            //End of try
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }





























}
