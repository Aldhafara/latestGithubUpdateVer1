package main.java.pl.noCompany.main;

import java.io.*;
import java.net.*;

public class Methods {
    public String matterCutterDate(String line) {
        char[] charArray = line.toCharArray();
        char[] outputString = new char[100];
        int i = 0;


        for (int j = 0; charArray[j] != 'Z'; j++) {
            if (j >= 41) {

                outputString[i] = charArray[j];
                i++;
                if (charArray[j] == 'T') {
                    outputString[i - 1] = ' ';
                    i++;
                }
            }
        }

        String output = new String(outputString);
        return output;
    }


    public String matterCutterName(String line) {
        char[] charArray = line.toCharArray();
        char[] outputString = new char[100];
        int i = 0;

        for (int j = 0; charArray[j] != '<'; j++) {


            if (charArray[j] != ' ') {
                outputString[i] = charArray[j];
                i++;
            }
        }

        String output = new String(outputString);
        return output;
    }


    public static void sourceWriter(URL sourceAdres, File fileName) throws Exception {
        PrintWriter writer = new PrintWriter(fileName);

        BufferedReader line = new BufferedReader(
                new InputStreamReader(sourceAdres.openStream()));

        String inputLine;
        while ((inputLine = line.readLine()) != null)
            writer.println(inputLine);
        writer.close();
    }


    public static String[] sourceReader(String fileName) throws IOException {


        /**
         * Ta metoda ma za zadanie przyjąć plik
         * wyszukać w nim wg klucza nazwę i zapisać do pierwszej komórki tablicy
         * wyszukać wg klucza czas i zapisać do drugiej komórki tablicy
         */


        String[] tab = new String[2];


        FileReader fileReader = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fileReader);
        String inputLine = new String();

        int i = 10;


        while (inputLine != null) {
            inputLine = reader.readLine();


            if ((inputLine == null) && (tab[0] == null) && (tab[1] == null))
                break;


            if (inputLine.contains("<li class=\"col-12") == true)
                i = 0;


            i++;
            if (i == 5)
                tab[0] = inputLine;

            if (inputLine.contains("Updated <relative-time datetime=") == true)
            {
                tab[1] = inputLine;
                break;
            }
        }

        reader.close();

        return tab;
    }

}