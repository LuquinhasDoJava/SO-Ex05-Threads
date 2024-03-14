package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ThreadLinuxPing extends Thread {
    private String servidorURL;
    private String servidorNome;

    public ThreadLinuxPing(String servidorURL) {
        this.servidorURL = servidorURL;
        this.servidorNome = this.formatarNomeServidor(servidorURL);
    }

    @Override
    public void run() {
        StringBuilder comando = new StringBuilder("ping -4 -c 10 ");
        comando.append(servidorURL);

        try {
            Process processo = Runtime.getRuntime().exec(comando.toString());
            InputStream fluxo = processo.getInputStream();
            InputStreamReader leitor = new InputStreamReader(fluxo);
            BufferedReader buffer = new BufferedReader(leitor);
            buffer.readLine();
            String linha, ultima = "";
            int acumulador = 0, QUANTIDADE_PING = 10;;
            while ((linha = buffer.readLine()) != null) {
                if (acumulador++ < QUANTIDADE_PING) {
                    exibirNomeEPing(linha);
                }
                ultima = linha;
            }

            buffer.close();
            leitor.close();
            fluxo.close();

            exibirMedia(ultima);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String formatarNomeServidor(String servidorURL) {
        String[] servidorDividido = servidorURL.split("\\.");
        return servidorDividido[1].toUpperCase();
    }

    private void exibirNomeEPing(String linha) {
        String[] linhaDividida = linha.split(" ");
        String tempo = linhaDividida[linhaDividida.length - 2];

        System.out.println("> Ping [" + servidorNome + "] " + tempo);
    }

    public void exibirMedia(String linha) {
        String[] linhaDividida = linha.split("/");
        String media = linhaDividida[linhaDividida.length - 3];

        System.out.println(">>> M�dia [" + servidorNome + "] = " + media);
    }
}