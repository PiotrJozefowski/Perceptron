package com.company;

public class Perceptron {

    double w1[];

    double t;
    double stala_uczenia;


    public Perceptron(int ilosc_wymiarow, double stala_uczenia) {

        this.w1 = new double[ilosc_wymiarow];
        this.t = Math.random()*2-1;
        this.stala_uczenia = stala_uczenia;

        for (int i = 0; i < ilosc_wymiarow; i++) {
            w1[i] = Math.random()*2-1;
        }

    }


    void ucz_w(double dane[], int wartosc_oczekiwana){
        int wartosc_wyjsciowa = 0;

        if(wartosc_oczekiwana == 0){
            wartosc_wyjsciowa = 1;
        }

        for (int i = 0; i < w1.length; i++) {
            w1[i] = w1[i] + ((wartosc_oczekiwana - wartosc_wyjsciowa)*stala_uczenia*dane[i]);
        }
        t = t + (wartosc_oczekiwana - wartosc_wyjsciowa)*stala_uczenia*(-1);

    }


    int klasyfikuj(double[] wektor){
        int y = 0;
        double net = 0;

        for (int i = 0; i < w1.length; i++) {
            net = net + (w1[i]*wektor[i]);
        }

        net = net - t;

        if(net >= 0){
            y = 1;
        }

        return y;
    }



}
