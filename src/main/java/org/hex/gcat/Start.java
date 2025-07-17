package org.hex.gcat;

import com.google.gson.JsonObject;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.hex.gcat.utils.Config;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.util.Map;

public class Start {
    static TelegramBot bot = null;
    public static String botToken="null";
    public static String ownerID="null";
    public static String sqlPm="null";


    public static void main(String[] args) throws IOException {
        //DEBUG
        System.setProperty("proxyHost", "127.0.0.1");
        System.setProperty("proxyPort", "7890");
        if (!Config.isConfigExist()){
            Config.makeConfig();
            Config.writeConfig(Config.DEAFULT_CONFIG());
            System.out.println("Initialization successful. Please modify the configuration file config.json and restart !");
            System.exit(0);
        }else {
            JsonObject config=Config.readConfig();
            botToken=config.get("botToken").getAsString();
            ownerID=config.get("ownerID").getAsString();
            sqlPm=config.get("sqlPm").getAsString();
            //Check
            if (botToken.equals("null") || ownerID.equals("null")){
                System.out.println("You have not complete the config");
                System.exit(0);
            }
            System.out.println("botToken: "+botToken);
            System.out.println("OwnerID: "+ownerID);
            if (sqlPm.equals("null")){
                System.out.println("You didn't edit the sqlmap's parameter.Sqlmap will run with our default parameter");
            }else {
                System.out.println("Sqlmap'Pm: "+sqlPm);
            }
            bot=new TelegramBot(botToken);
            System.out.println("\nGlientCat Running... ");
        }
        //15 Threads
        for (int i = 0; i < 15; i++) {
            new Thread(new SQLScanner()).start();
        }
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                System.out.println(update);
                if (update.message() != null && update.message().document() != null) {
                    String content=handleDocument(bot, update);
                    Thread thread =new Thread(()->{
                        String[] lines = content.split("\r\n|\n|\r");

                        for (String line : lines) {
                            SQLScanner.addTarget(line);
                            System.out.println(line);
                        }
                    });
                    thread.start();
                    bot.execute(new SendMessage(update.message().chat().id(),"Imported Successfully\nAdded："+content.lines().count()+"\nWaiting："+SQLScanner.targets.size()));
                } else if (update.message()!=null&&update.message().text()!=null) {
                    String msg=update.message().text();
                    Long id =update.message().chat().id();
                    System.out.println(id);
                    switch (msg){
                        case "/threads":
                            bot.execute(new SendMessage(id,"Active Threads Count:"+countActiveThreadsSimple()));
                            break;
                        case "/targets":
                            String scanning="";
                            for (String target:SQLScanner.scanning){
                                scanning+="· "+target+"\n";
                            }
                            bot.execute(new SendMessage(id,"Remain Targets:"+SQLScanner.targets.size()+"\nScanning: "+scanning));
                            break;
                    }
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            } else {
                e.printStackTrace();
            }
        });
    }
    public static int countActiveThreadsSimple() {
        Map<Thread, StackTraceElement[]> allThreads = Thread.getAllStackTraces();
        int activeCount = 0;

        for (Thread thread : allThreads.keySet()) {
            if (thread.isAlive()) {
                activeCount++;
            }
        }

        return activeCount;
    }

    private static String handleDocument(TelegramBot bot, Update update) {
        Document document = update.message().document();
        long chatId = update.message().chat().id();

        if (document.fileName().endsWith(".txt")) {
            try {
                GetFile getFileRequest = new GetFile(document.fileId());
                GetFileResponse getFileResponse = bot.execute(getFileRequest);
                String filePath = getFileResponse.file().filePath();

                String fileUrl = "https://api.telegram.org/file/bot"+botToken+"/" + filePath;

                URL url = new URL(fileUrl);
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {

                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    return content.toString();
                }

            } catch (Exception e) {
                bot.execute(new SendMessage(chatId, "Error: " + e.getMessage()));
                e.printStackTrace();
            }
        } else {
            bot.execute(new SendMessage(chatId, "Please send file with .txt "));
        }
        return "error";
    }
}
