package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


//THE PLAN
//: Take one coordinate form one file. Put it against all the other coordinates. If overlap. Get info on coordinate and print atoms info.
//  Take the serial number of the one that clashed I

public class Labb3Program {

    private static Scanner scanner;


    public Labb3Program() throws FileNotFoundException {






        this.getLineCoordinates("src/files/1cdh.pdb");


    }


    public double[] getLineCoordinates(String filePath) throws FileNotFoundException {

        double[] coordinates_array = {0.0, 0.0, 0.0};

        String Xcoordinate = "";
        String Ycoordinate = "";
        String Zcoordinate = "";

        try {
            File file = new File(filePath);
            scanner = new Scanner(file);
            String lineContent = "";

            System.out.println("Running from filepath: " + file);

            int linecount = 0;

            while (scanner.hasNextLine()) {

                linecount++;

                lineContent = scanner.nextLine();
                String tempCoordinate = "x";

                //Iterates through the line string.
                for (int i = 7; i < (lineContent.length()); i++) {


                    //This will be hit when a space is found,
                    // it will now write to Z coordinate instead of previous temp.
                    if (lineContent.charAt(i) == ' ' && tempCoordinate == "y"){
                        tempCoordinate = "z";
                    }
                    //This will be hit when a space is found,
                    // it will now write to Y coordinate instead of previous temp.
                    if (lineContent.charAt(i) == ' ' && tempCoordinate == "x") {
                        tempCoordinate = "y";
                    }


                    switch (tempCoordinate) {
                        case "x" -> Xcoordinate += lineContent.charAt(i);
                        case "y" -> Ycoordinate += lineContent.charAt(i);
                        case "z" -> Zcoordinate += lineContent.charAt(i);
                        default -> {
                            System.out.println("Invalid token");
                            break;
                        }
                    }

                    //end of for loop

                }

                //resets starting coordinate
                tempCoordinate = "x";

                //Sets x, y ,z to list that can be put  as value for hashmap
                coordinates_array[0] = Double.parseDouble((Xcoordinate));
                coordinates_array[1] = Double.parseDouble((Ycoordinate));
                coordinates_array[2] = Double.parseDouble((Zcoordinate));


            }


        } catch (Exception e) {throw new FileNotFoundException("File not found");}



        return coordinates_array;
    }













/*

            int var = 0;

            while(true) {
                int tempCoordinate = "x";


                do {
                    if (!scanner.hasNextLine()) {
                        break;
                    }

                    ++var;
                    lineContent = scanner.nextLine();
                    tempCoordinate = "x";
                } while(!scanner.hasNext("ATOM "));

                System.out.println("Atom found");
*/






}
