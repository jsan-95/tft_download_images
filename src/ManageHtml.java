
import java.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Date;

public class ManageHtml {

    private static HttpURLConnection con;

    public ManageHtml(){}

    // Método para recoger todos los id de las imágenes, filtradas por painting (cuadros), e introducirlos en un txt
    public void createTxt(){
        try {
            System.out.println("Comienzo: "+new Date().toString());
            BufferedReader reader = new BufferedReader(new FileReader("/Users/Yisus95/eclipse-workspace/ManageHtml/src/dataset_result"));
            String line;

            BufferedWriter writer = null;
            writer = new BufferedWriter( new FileWriter( "/Users/Yisus95/eclipse-workspace/ManageHtml/src/all_json_result", true));

            int i = 0;
            while ((line = reader.readLine()) != null) {
                if(i%100 == 0){
                    System.out.println("Iteración: " + i);
                }
                writer.append(request("https://collectionapi.metmuseum.org/public/collection/v1/objects/",line));
                i++;
            }
            writer.close();
            reader.close();
            System.out.println("Final: "+new Date().toString());
        }

        catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", "dataset_images.txt");
            e.printStackTrace();
        }
    }

    public void filterByPublicDomain(){
        try {
            System.out.println("Comienzo: "+new Date().toString());
            BufferedReader reader = new BufferedReader(new FileReader("/Users/Yisus95/eclipse-workspace/ManageHtml/src/all_json_result"));
            String line;

            BufferedWriter publicWriter = null;
            publicWriter = new BufferedWriter( new FileWriter( "/Users/Yisus95/eclipse-workspace/ManageHtml/src/json_with_public_images", true));

            BufferedWriter noPublicWriter = null;
            noPublicWriter = new BufferedWriter( new FileWriter( "/Users/Yisus95/eclipse-workspace/ManageHtml/src/json_without_public_images", true));

            int i = 0;

            while ((line = reader.readLine()) != null) {

                if(line.contains('"'+"isPublicDomain"+'"'+":true")){
                    publicWriter.append(line+"\n");
                }else{
                    noPublicWriter.append(line+"\n");
                }
            }
            publicWriter.close();
            noPublicWriter.close();
            reader.close();

            System.out.println(i + " Final: "+new Date().toString());
        }

        catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", "dataset_images.txt");
            e.printStackTrace();
        }
    }

    public void readPublicImages(){

        try {
            System.out.println("Comienzo: "+new Date().toString());
            BufferedReader reader = new BufferedReader(new FileReader("/Users/Yisus95/eclipse-workspace/ManageHtml/src/json_with_public_images"));
            String line;

            int i = 0;

            while ((line = reader.readLine()) != null) {
                if(i%100 == 0 ){
                    System.out.println("Iteración: "+ i);
                }

                String imageUrl = line.substring(line.indexOf("http"), line.indexOf('"',line.indexOf("http")));
                if(imageUrl.contains(" ")){
                    imageUrl = imageUrl.replace(" ", "%20");
                }
                String id = line.substring(line.indexOf(':')+1, line.indexOf(','));
                download(imageUrl,"/Volumes/Seagate Expansion Drive/TFT/Conjunto de datos/public_images"+"/"+id+".jpg");
                i++;

            }
            reader.close();

            System.out.println(i + " Final: "+new Date().toString());
        }
        catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", "dataset_images.txt");
            e.printStackTrace();
        }

    }

    public static void download(String URL,String folder) throws IOException {

        InputStream inputStream = null;

        // This will read the data from the server;
        OutputStream outputStream = null;

        try {
            // This will open a socket from client to server
            URL url = new URL(URL);

            // This user agent is for if the server wants real humans to visit
            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36 ";

            // This socket type will allow to set user_agent
            URLConnection con = url.openConnection();

            // Setting the user agent
            con.setRequestProperty("User-Agent", USER_AGENT);

            // Requesting input data from server
            inputStream = con.getInputStream();

            // Open local file writer
            outputStream = new FileOutputStream(folder);

            // Limiting byte written to file per loop
            byte[] buffer = new byte[2048];

            // Increments file size
            int length;

            // Looping until server finishes
            while ((length = inputStream.read(buffer)) != -1) {
                // Writing data
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception ex) {

        }

        outputStream.close();
        inputStream.close();
    }

    // Sicitud a la url y comprobar si contienes la ristra Painting
    public String request(String URL, String id){
        String url = URL+id;
        try {

            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
            URL myurl = new URL(url);

            con.setRequestProperty("User-Agent", USER_AGENT);
            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("GET");

            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            if(content.toString().contains("Painting")){
                return content.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return "";
    }


    public String requestAirlines(String URL){
        String url = URL;

        try {
            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

            URL myurl = new URL(url);
            URLConnection con = myurl.openConnection();

            con.setRequestProperty("User-Agent", USER_AGENT);





            System.out.printf("paso2");
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            try (PrintStream out = new PrintStream(new FileOutputStream("filename.txt"))) {
                out.print(content);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
