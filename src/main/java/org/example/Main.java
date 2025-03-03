package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jcraft.jsch.*;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, JSchException {

        Random rm = new Random();
        int pageNum = rm.nextInt(0, 35);
        final String urlBase = "https://picsum.photos/v2/list?page=";
        final File localFolder = new File("src/main/resources/");
        URL url = new URI(urlBase+pageNum).toURL();

        List<String> listaDescargas = obtenerLista(url);

        ExecutorService pool = Executors.newFixedThreadPool(5);

        for (String enlace: listaDescargas){
            pool.execute(new ImageDownloader(enlace));
        }

        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.MILLISECONDS);

        File [] archivosparaSubir = localFolder.listFiles();
        uploadToSFTP(archivosparaSubir);
        deleteFolder(archivosparaSubir);

    }

    private static void deleteFolder(File[] archivosparaSubir) {
        for (File arch : archivosparaSubir) {
            if (arch.delete()) {
                System.out.println(arch.getName() + " ha sido borrado satisfactoriamente");
            } else {
                System.out.println("No se ha podido borrar el archivo " + arch.getName());
            }
        }
    }


    private static void uploadToSFTP(File [] archivo) throws JSchException {
        String host = "192.168.56.1";
        String username = "tester";
        String password = "password";


        JSch jsch = new JSch();
        jsch.addIdentity("C:\\Users\\VSPC-BLACKFRIDAY\\Desktop\\PSP\\privateKEY.ppk");
        Session session = null;
        try {
            session = jsch.getSession(username, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            System.out.println("Conexión con clave privada establecida ✅");

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            for (File f: archivo) {
                String remoteFile = f.getName();
                String localFile = f.getAbsolutePath();
                sftpChannel.put(localFile, remoteFile);
                System.out.println("Se ha subido "+localFile);
            }
            sftpChannel.disconnect();
            session.disconnect();

        } catch (SftpException | JSchException e) {
            e.printStackTrace();
        }

    }

    private static List<String> obtenerLista(URL url) throws IOException {
        Type tipo = new TypeToken<List<Download>>() {}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(tipo, new ImageDeserializer())
                .setPrettyPrinting()
                .create();

        StringBuilder sb = new StringBuilder();
        try (var in = new BufferedReader(new InputStreamReader(url.openStream()))){
            String line;
            while ((line=in.readLine())!=null){
                sb.append(line);
            }
        }
        String jsonIMG = sb.toString();
        List<Download> listaDescargas = gson.fromJson(jsonIMG, tipo);
        List<String> urls = new ArrayList<>();

        for (Download d: listaDescargas){
            urls.add(d.getUrl());
        }

        return urls;
    }


}