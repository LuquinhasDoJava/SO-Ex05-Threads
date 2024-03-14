package view;

import controller.ThreadLinuxPing;

public class Main {
    public static void main(String[] args) {
        Thread[] pings = new ThreadLinuxPing[3];

        pings[0] = new ThreadLinuxPing("www.google.com.br");
        pings[1] = new ThreadLinuxPing("www.uol.com.br");
        pings[2] = new ThreadLinuxPing("www.terra.com.br");

        for (Thread ping : pings) {
            ping.start();
        }
    }
}