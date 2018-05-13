package main.java.pl.noCompany.main;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class AppBootstrapper {
    public static void start() throws Exception {




        System.out.println("Podaj login Uzytkownika, ktorego repozytorium chcesz sprawdzic: ");
        Scanner reader = new Scanner(System.in);
        String login = reader.nextLine();
        System.out.println("Podales login: " + login);
        URL github = new URL("https://github.com/" + login + "?tab=repositories");


        /**
         * PONIZSZY BLOK SPRAWDZA POLACZENIE Z ADRESEM URL
         */
        HttpURLConnection huc =  (HttpURLConnection)  github.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();

        if (huc.getResponseCode()==404)
        {
            System.out.println("Wszystko wskazuje na to, że Uzytkownik " + login + " nie istnieje.");
            System.exit(0);
        }
        if ((huc.getResponseCode()>=400) && (huc.getResponseCode()<600))
        {
            System.out.println("ERROR " + huc.getResponseCode());
            System.exit(0);
        }


        /**
         * PONIZSZY BLOK TWORZY TYMCZASOWY PLIK TEKSTOWY
         * I WYPEŁNIA GO KODEM ZRODLOWYM STRONY
         */
        Methods repositoryName = new Methods();
        File file = new File("source.txt");

        System.out.println("Tworze plik tymczasowy: " + file.getName());

        repositoryName.sourceWriter(github,file);

        System.out.println("Przegladam pliki uzytkownika...");


        /**
         * PONIZSZY BLOK DEDYKOWANY JEST ZACHOWANIU PROGRAMU
         * W SYTUACJI, GDY PODANY UZYTKOWNIK MA PUSTE REPOZYTORIUM.
         *
         * PROGRAM KASUJE PLIK TYMCZASOWY I KONCZY DZIALANIE
         */
        String[] repos = repositoryName.sourceReader("source.txt");
        if ((repos[0] == null)&&(repos[1] == null))
        {
            System.out.println("Wszystko wskazuje na to, że Uzytkownik " + login + " na puste repozytorium.");
            if(file.delete()){
                System.out.println("Skasowano plik tymczasowy: " + file.getName());
            }else{
                System.out.println("Operacja kasowania pliku tymczasowego nie powiodla sie.");
            }
            System.exit(0);
        }


        /**
         * WYSWIETLANIE INFORMACJI O OSTATNIO ZAUKTUALIZOWANYM REPOZYTORIUM
         */
        System.out.println("Ostatnio zaktualizowanym repozytorium Uzytkownika "
                + login + " jest: << " + repositoryName.matterCutterName(repos[0])
                + " >>; zaktualizowano: " + repositoryName.matterCutterDate(repos[1]) + " UTC");


        /**
         * KASOWANIE PLIKU TYMCZASOWEGO
         */
        if(file.delete()){
            System.out.println("Skasowano plik tymczasowy: " + file.getName());
        }else{
            System.out.println("Operacja kasowania pliku tymczasowego nie powiodla sie.");
        }


    }
}
