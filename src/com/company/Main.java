package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String komenda = " ";
        File train = new File("train.csv");
        File test = new  File("test.csv");

        Scanner odczyt = new Scanner(System.in);

        ArrayList<double []> lista_setosa = lista(train,"Iris-setosa");
        ArrayList<double []> lista_versicolor = lista(train,"Iris-versicolor");
        ArrayList<String []> lista_testowa = lista_testowa(test);

        Perceptron perceptron = new Perceptron(4,0.25);

        while(!komenda.equals("zakoncz")) {

            System.out.println("\nWybór : ");
            komenda = odczyt.nextLine();

            switch (komenda){

                case "naucz" :
                    System.out.println("Podaj ilosc powtorzen nauki : ");
                    int ilosc_obrotow = Integer.parseInt(odczyt.nextLine());
                    for (int i = 0; i < ilosc_obrotow; i++) {

                        lista_setosa.forEach((dane_train) -> {
                            if (perceptron.klasyfikuj(dane_train) != 1) {                     // SETOSA = 1
                            perceptron.ucz_w(dane_train, 1);
                            }
                        });

                        lista_versicolor.forEach((dane_train) -> {
                            if (perceptron.klasyfikuj(dane_train) != 0) {                     // versicolor = 0
                            perceptron.ucz_w(dane_train, 0);
                            }
                        });
            }
                break;


                case "klasyfikuj plik" :
                    int bledne_wszytkie = 0;
                    double przetestowane_wszystkie = 0;

                    int bledne_SETOSA = 0;
                    double przetestowane_SETOSA = 0;

                    int bledne_VERSICOLOR = 0;
                    double przetestowane_VERSICOLOR = 0;

                    for(String[] do_klasyfikacji : lista_testowa) {

                        przetestowane_wszystkie++;
                        double[] dane = new double[do_klasyfikacji.length - 1];

                        for (int i = 0; i < dane.length; i++) {
                            dane[i] = Double.parseDouble(do_klasyfikacji[i]);
                        }

                        if (perceptron.klasyfikuj(dane) == 1) {
                            przetestowane_SETOSA++;
                            if (!do_klasyfikacji[do_klasyfikacji.length - 1].equals("Iris-setosa")) {
                                bledne_wszytkie++;
                                bledne_SETOSA++;
                            }

                        } else {
                            przetestowane_VERSICOLOR++;
                            if (!do_klasyfikacji[do_klasyfikacji.length - 1].equals("Iris-versicolor")) {
                                bledne_wszytkie++;
                                bledne_VERSICOLOR++;
                            }
                        }

                    }

                    System.out.println("Dokladnosc ogolna       : " + (przetestowane_wszystkie - bledne_wszytkie)/przetestowane_wszystkie);
                    System.out.println("Dokladnosc SETOSA       : " + ((przetestowane_SETOSA - bledne_SETOSA)/przetestowane_SETOSA));
                    System.out.println("Dokladnosc VERSICOLOR   : " + ((przetestowane_VERSICOLOR - bledne_VERSICOLOR)/przetestowane_VERSICOLOR));

                break;

                case "klasyfikuj" :
                    System.out.println("Podaj dane : ");
                    String linia_danych_wprowadzonych = odczyt.nextLine();

                    double[] tablica_danych_wprowadzonych = wczytaj_wektor(linia_danych_wprowadzonych);

                    if(perceptron.klasyfikuj(tablica_danych_wprowadzonych) == 1){
                        System.out.println("Klasyfikacja do SETOSA");
                    }else{
                        System.out.println("KLASYFIKACJA DO VERSICOLOR");
                    }
                break;

        }

        }
    }

    static double[] wczytaj_wektor(String linia_danych_wprowadzonych){

        String[] tab_przejsciowa = linia_danych_wprowadzonych.split(";");
        double[] tab_wynikowa = new double[tab_przejsciowa.length];

        for (int i = 0; i < tab_przejsciowa.length-1; i++) {
            tab_wynikowa[i] = Double.parseDouble(tab_przejsciowa[i]);
        }


        return tab_wynikowa;
    }



    static ArrayList<double []> lista(File plik, String grupa){

        Scanner odczyt = null;
        String[] przejsciowa;

        try {
             odczyt = new Scanner(plik);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<double []> lista = new ArrayList<>();

        while(odczyt.hasNext()){
            przejsciowa = odczyt.nextLine().split(";");
            double[] dane = new double[przejsciowa.length-1];

            if(przejsciowa[przejsciowa.length-1].equals(grupa)) {
                for (int i = 0; i < przejsciowa.length - 1; i++) {
                    dane[i] = Double.parseDouble(przejsciowa[i]);
                }
                lista.add(dane);
            }
        }
        return lista;
    }


    static ArrayList<String []> lista_testowa(File plik){

        Scanner odczyt = null;
        String[] dane;

        ArrayList<String []> lista_testowa = new ArrayList<>();

        try {
            odczyt = new Scanner(plik);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(odczyt.hasNext()){

            dane = odczyt.nextLine().split(";");
                lista_testowa.add(dane);
            }

        return lista_testowa;

    }


}





