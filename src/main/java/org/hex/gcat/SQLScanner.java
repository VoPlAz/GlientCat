package org.hex.gcat;

import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SQLScanner implements Runnable {

    public static final Queue<String> targets = new ConcurrentLinkedQueue<>();
    public static Set<String> scanning = new HashSet<>();
    public static void addTarget(String url) {
        targets.offer(url);
    }

    @Override
    public void run() {
        while (true) {
            String target = targets.poll();
            if (target == null) {

                try {
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //Thread.currentThread().interrupt();
                    continue;
                }
                continue;
            }

            scanTarget(target);
        }
    }

   private void scanTarget(String target) {
       try {
           scanning.add(target);
           ProcessBuilder pb = null;
           if (Start.sqlPm.equals("null")) {
               pb = new ProcessBuilder(
                       "python", "sqlmap.py", "-u", target, "--batch", "--random-agent", "--crawl=2", "--forms");
           }else {
               pb = new ProcessBuilder("python","sqlmap.py","-u",target,"--batch",Start.sqlPm);
           }
           pb.redirectErrorStream(true);
           Process process = pb.start();

           try (BufferedReader reader = new BufferedReader(
                   new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

               StringBuilder output = new StringBuilder();
               String line;
               while ((line = reader.readLine()) != null) {
                   output.append(line).append("\n");
               }

               if (!process.waitFor(30, TimeUnit.MINUTES)) {
                   process.destroyForcibly();
                   throw new RuntimeException("Timeout");
               }

               int exitCode = process.exitValue();
               System.out.println(exitCode);


               if (output.toString().contains("the following injection point(s)")&&output.toString().contains("Payload")) {
                   File file = new File("details-"+System.currentTimeMillis()+".txt");
                   file.createNewFile();
                   BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                   bw.write(output.toString());
                   bw.close();
                   Pattern pattern = Pattern.compile("Type: (.+?)\\n");
                   Matcher matcher = pattern.matcher(output.toString());
                   String types="";
                   while (matcher.find()) {
                       types+="· " + matcher.group(1)+"\n";
                   }
                   Start.bot.execute(new SendDocument(Start.ownerID, file).caption("0x6763636174\nFound SQL Injection Point Successfully\nURL: \"" +target+"\"\n"+types));
                   file.delete();
               } else if (output.toString().contains("can't establish SSL connection")) {
                   Start.bot.execute(new SendMessage(Start.ownerID,"Cannt establish SSL Connection\nURL: "+target));
               } else {
                   Start.bot.execute(new SendMessage(Start.ownerID,
                           "Didn't Find SQL Injection Point: Status Code" + exitCode+"\n" +
                                   "URL: "+target));
               }
               scanning.remove(target);
           }
       } catch (Exception e) {
           System.err.println("Unknown Error: " + target);
           e.printStackTrace();
           Start.bot.execute(new SendMessage(Start.ownerID,
                   "Unknown Error: " + target + "\nDescription: " + e.getMessage()));
           scanning.remove(target);
       }
   }

}

