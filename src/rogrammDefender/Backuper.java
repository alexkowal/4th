package rogrammDefender;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Backuper implements Serializable {

    ArrayList<MyFile> list = new ArrayList<>();

    static String getIno(Object ob) {
        java.lang.String s = ob.toString();
        StringBuilder val = new StringBuilder();
        for (int i = s.length() - 2; s.charAt(i) != '='; i--) {
            val.append(s.charAt(i));
        }
        return val.reverse().toString();
    }

    ArrayList<MyFile> findNewFiles(File folder) throws IOException {
        ArrayList<MyFile> filesInFolder = new ArrayList<>();
        createBackup(folder, filesInFolder);
        for (int i = 0; i < filesInFolder.size(); i++) {
            if (list.contains(filesInFolder.get(i))) {
                filesInFolder.remove(filesInFolder.get(i));
                i--;
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("Новые файлы: " + filesInFolder.size());
        for (MyFile myFile : filesInFolder) {
            System.out.println(myFile.getName() + " " + sdf.format((myFile.getCreateTime())));

        }
        return filesInFolder;
    }

    ArrayList<MyFile> findRemovedFiles(File folder) throws IOException {
        ArrayList<MyFile> filesInFolder = new ArrayList<>();
        ArrayList<MyFile> result = new ArrayList<>();
        createBackup(folder, filesInFolder);
        for (int i = 0; i < list.size(); i++) {
            if (!filesInFolder.contains(list.get(i))) {
                result.add(list.get(i));
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("Удаленные файлы: " + result.size());
        for (MyFile myFile : result) {
            System.out.println(myFile.getFileKey() + " " + myFile.getName() + " " + sdf.format((myFile.getCreateTime())));

        }
        return result;
    }

    ArrayList<MyFile> findChangedFiles(File folder) throws IOException {
        ArrayList<MyFile> filesInFolder = new ArrayList<>();
        ArrayList<MyFile> result = new ArrayList<>();
        createBackup(folder, filesInFolder);
        for (int i = 0; i < list.size(); i++) {
            if (filesInFolder.contains(list.get(i))) {
                if (!filesInFolder.get(filesInFolder.indexOf(list.get(i))).getSize().equals(list.get(i).getSize())
                        || !filesInFolder.get(filesInFolder.indexOf(list.get(i))).getLastModified().equals(list.get(i).getLastModified())) {

                    result.add(list.get(i));
                    //System.out.println(filesInFolder.get(filesInFolder.indexOf(list.get(i))).getSize() + " " + list.get(i).getSize());
                }
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("Измененные файлы: " + result.size());
        for (MyFile myFile : result) {
            System.out.println(myFile.getName() + " " + sdf.format((myFile.getCreateTime())));

        }
        return result;
    }


    public void createBackup(File folder, ArrayList<MyFile> list) throws IOException {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                createBackup(entry, list);
                continue;
            } else {
                Path file = entry.toPath();
                MyFile f = new MyFile();
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                f.setName(file.getFileName().toString());
                FileTime time = attr.creationTime();
                Date newDate = new Date(time.toMillis());
                f.setCreateTime(newDate);
                f.setSize(attr.size());

                FileTime lastModTIme = attr.creationTime();
                Date lastModDate = new Date(lastModTIme.toMillis());
                f.setLastModified(lastModDate);
                Object ob = attr.fileKey();
                f.setFileKey(Long.valueOf(getIno(ob)));
                list.add(f);
            }
        }
    }

    public void inspect(File folder) throws IOException {
        ArrayList<MyFile> changedFiles = new ArrayList<>();
        ArrayList<MyFile> removed = new ArrayList<>();

        File[] folderEntries = folder.listFiles();
        findNewFiles(folder);
        findRemovedFiles(folder);
        findChangedFiles(folder);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Выберите режим: Backup(b) Inspect(i)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String mode = br.readLine();
        if (mode.equals("b")) {
            Backuper b = new Backuper();

            b.createBackup(new File("/Users/aleksandr/4 курс 2 семестр"), b.list);
            FileOutputStream fs = new FileOutputStream("tmp.txt");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(b);
        } else {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("tmp.txt"));
            Backuper b = (Backuper) is.readObject();
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("tmp.txt"));
            os.writeObject(b);
            b.inspect(new File("/Users/aleksandr/4 курс 2 семестр"));

            ///Users/aleksandr/bearings
            ///Users/aleksandr/4 курс 2 семестр
            ///Users/aleksandr/4 курс 2 семестр/Матлаб
        }
    }
}
