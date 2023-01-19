package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;
import java.lang.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Labb3ProgramV2 {


    public String get_atom_name(String lineContent) {

        int spaceCount = 0;
        String atomName = "";

        for (int i = 0; i < (lineContent.length() - 1); i++) {

            char test = (lineContent.charAt(i));

            if ((lineContent.charAt(i) == ' ') && (!(lineContent.charAt(i - 1) == ' '))) {
                spaceCount++;
            }

            //Gets serial number
            while ((spaceCount == 2) && (lineContent.charAt(i) != ' ')) {

                atomName += String.valueOf(lineContent.charAt(i));

                i++;

                if ((lineContent.charAt(i) == ' ')) {
                    return atomName;
                }
            }
        }
        return "Failed getting atom name";
    }

    public String get_atom_type(String lineContent) {

        int spaceCount = 0;
        String atomType = "";

        for (int i = 0; i < (lineContent.length() - 1); i++) {

            char test = (lineContent.charAt(i));

            if ((lineContent.charAt(i) == ' ') && (!(lineContent.charAt(i - 1) == ' '))) {
                spaceCount++;
            }


            while ((spaceCount == 3) && (lineContent.charAt(i) != ' ')) {

                atomType += String.valueOf(lineContent.charAt(i));

                i++;

                if ((lineContent.charAt(i) == ' ')) {
                    return atomType;
                }
            }
        }
        return "Failed getting atom type";
    }

    public void chain_identifier() {
    }

    public ArrayList get_atom_coordinates(String lineContent) {

        ArrayList<Double> coordinateSubList = new ArrayList<Double>();

        String Xcoordinate = "";
        String Ycoordinate = "";
        String Zcoordinate = "";

        double x;
        double y;
        double z;

        int spaceCount = 0;

        for (int i = 0; i < (lineContent.length() - 1); i++) {


            if ((lineContent.charAt(i) == ' ') && (!(lineContent.charAt(i + 1) == ' '))) {
                spaceCount++;
            }

            //Gets X coordinate
            while (((spaceCount == 6) && (lineContent.charAt(i) != ' '))) {
                i++;
                Xcoordinate += lineContent.charAt(i - 1);
            }
            //Gets Y coordinate
            while (((spaceCount == 7) && (lineContent.charAt(i) != ' '))) {
                i++;
                Ycoordinate += lineContent.charAt(i - 1);
            }
            //Gets Z coordinate
            while (((spaceCount == 8 && (lineContent.charAt(i + 1) != ' ')))) {
                i++;
                Zcoordinate += lineContent.charAt(i - 1);

                if (lineContent.charAt(i + 1) == ' ') {
                    Zcoordinate += lineContent.charAt(i);
                }
            }
        }

        coordinateSubList.add(Double.parseDouble((Xcoordinate)));
        coordinateSubList.add(Double.parseDouble((Ycoordinate)));
        coordinateSubList.add(Double.parseDouble((Zcoordinate)));


        return coordinateSubList;

    }

    public String get_atom_number(String lineContent) {

        int spaceCount = 0;
        String atomNumber = "";

        for (int i = 0; i < (lineContent.length() - 1); i++) {

            char test = (lineContent.charAt(i));

            if ((lineContent.charAt(i) == ' ') && (!(lineContent.charAt(i - 1) == ' '))) {
                spaceCount++;
            }


            while ((spaceCount == 1) && (lineContent.charAt(i) != ' ')) {

                atomNumber += String.valueOf(lineContent.charAt(i));

                i++;

                if ((lineContent.charAt(i) == ' ')) {
                    return atomNumber;
                }
            }
        }
        return "Failed getting atom number";
    }

    public String get_atom_sequenceNumber(String lineContent) {

        int spaceCount = 0;
        String sequenceNumber = "";

        for (int i = 0; i < (lineContent.length() - 1); i++) {

            char test = (lineContent.charAt(i));

            if ((lineContent.charAt(i) == ' ') && (!(lineContent.charAt(i - 1) == ' '))) {
                spaceCount++;
            }


            while ((spaceCount == 5) && (lineContent.charAt(i) != ' ')) {

                sequenceNumber += String.valueOf(lineContent.charAt(i));

                i++;

                if ((lineContent.charAt(i) == ' ')) {
                    return sequenceNumber;
                }
            }
        }
        return "Failed getting atom number";
    }

    public boolean doesAtomOverlap(ArrayList coordinateSubList1, ArrayList coordinateSubList2) {

        double delta = 0;

        //Formula used:
        //delta = (Math.sqrt(Math.pow((x2 - x1), 2)) + Math.pow((y2 - y1), 2) + Math.pow((z2 - z1), 2));

        delta = (
                Math.sqrt(
                        Math.pow(((double) coordinateSubList2.get(0) - (double) coordinateSubList1.get(0)), 2)
                                + Math.pow(((double) coordinateSubList2.get(1) - (double) coordinateSubList1.get(1)), 2)
                                + Math.pow(((double) coordinateSubList2.get(2) - (double) coordinateSubList1.get(2)), 2)
                )
        );

        return !(delta >= 4);

    }

    public void compare_file(File file1, File file2) throws FileNotFoundException {

        int numberOfClashingAtoms = 0;
        int timesInFile1 = 0;
        int timesInFile2 = 0;

        ArrayList alreadyClashedAtoms = new ArrayList();

        try {
            System.out.println("Calculating overlap...");
            Scanner scanner1;
            scanner1 = new Scanner(file1);
            String lineContent1 = "";

            while (scanner1.hasNextLine()) {
                lineContent1 = scanner1.nextLine();

                if ((lineContent1.startsWith("ATOM") || lineContent1.startsWith("HETATM"))) {

                    timesInFile1++;

                    try {
                        Scanner scanner2;
                        scanner2 = new Scanner(file2);
                        String lineContent2 = "";

                        while (scanner2.hasNextLine()) {
                            lineContent2 = scanner2.nextLine();

                            if ((lineContent2.startsWith("ATOM") || lineContent2.startsWith("HETATM"))) {
                                timesInFile2++;
                                if (alreadyClashedAtoms.contains(get_atom_number(lineContent2))) {
                                    //if atom in file2 already printed -> dont check
                                    continue;
                                } else if (doesAtomOverlap(get_atom_coordinates(lineContent1), get_atom_coordinates(lineContent2))) {
                                    numberOfClashingAtoms++;
                                    alreadyClashedAtoms.add(get_atom_number(lineContent2));
                                    System.out.println(get_atom_number(lineContent2)+ " " + get_atom_type(lineContent2) + " " + get_atom_sequenceNumber(lineContent1) + " " + get_atom_name(lineContent2));


                                }
                            }
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } finally {

        }

        int totalComparisons = timesInFile1 + timesInFile2;
        System.out.println("Number of comparisons: " + totalComparisons);
        System.out.println("Number of clashing atoms: " + numberOfClashingAtoms);
    }


}

















