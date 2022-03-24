package me.thedreps.hypixeltweaks.update;

import me.thedreps.hypixeltweaks.reference.Reference;
import me.thedreps.hypixeltweaks.reference.UpdateInfo;
import me.thedreps.hypixeltweaks.utility.LogHelper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class UpdateCheck {

    public static void checkForUpdates(){

        try {

            URL url = new URL(UpdateInfo.updatePath);

            HttpURLConnection connection = (HttpURLConnection) new URL(url.toString()).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();

            LogHelper.info("Update checker got response " + responseCode);

            if(responseCode != 200){
                UpdateInfo.updateErred = true;
            }
            Scanner scanner = new Scanner(url.openStream());
            while(scanner.hasNextLine()){
                UpdateInfo.modUpdate.add(scanner.nextLine());
            }
            scanner.close();
            
            UpdateInfo.updateArray = UpdateInfo.modUpdate.toArray(new String[0]);

            if((!Reference.MOD_VERSION.endsWith(UpdateInfo.updateArray[0]))){
                UpdateInfo.updateAvailable = true;
                UpdateInfo.updateLN1 = UpdateInfo.updateArray[1];
                UpdateInfo.updateLN2 = UpdateInfo.updateArray[2];
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
