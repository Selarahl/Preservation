package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.*;


public class Main {

    public static String pathway = "F://NETOLOGY/Games/savegames";

    private static void saveGame(String path, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFiles(String path, List<String> pathFiles) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String pathsFiles : pathFiles) {
                File file = new File(pathsFiles);
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removingIfNotZip (String path) {
        Arrays.stream(new File(path).listFiles())
                .filter(x-> !x.getName().endsWith("zip"))
                .forEach(File::delete);
    }

    public static void main(String[] args) {
        GameProgress game = new GameProgress(300, 5, 12, 15.3);
        GameProgress game1 = new GameProgress(100, 3, 9, 7.4);
        GameProgress game2 = new GameProgress(750, 12, 35, 75.7);

        saveGame(pathway + "/saveGame.dat", game);
        saveGame(pathway + "/saveGame1.dat", game1);
        saveGame(pathway + "/saveGame2.dat", game2);

        zipFiles(pathway + "/saveGames.zip", Arrays.asList(pathway + "/saveGame.dat", pathway + "/saveGame1.dat", pathway + "/saveGame2.dat"));

        removingIfNotZip(pathway);
    }
}
