package config;

import java.io.*;

public class Helper {
    public static void createBinaryFileFromTxt(File file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            StringBuilder build = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                build.append(line).append("\n");
            }
            SerialString s1 = new SerialString(build.toString());

            System.out.println("b"+file.getName());
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getParent()+"\\b"+file.getName()));
            out.writeObject(s1);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File p = new File(Prop.INPUT_FILE_PATH + "\\bRLE.txt");
        String sourceFile = p.getPath();
        String destFile = p.getParent()+"\\output.dat";
        BufferedReader fis = null;
        BufferedWriter fos = null;

        try {
            fis = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
            fos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile)));

            char[] buffer = new char[1024];
            int noOfBytes = 0;

            System.out.println("Copying file using streams");

            // read bytes from source file and write to destination file
            while ((noOfBytes = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, noOfBytes);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        }
        catch (IOException ioe) {
            System.out.println("Exception while copying file " + ioe);
        }
        finally {
            // close the streams using close method
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }

    protected static class SerialString implements Serializable {
        private byte[] content;

        public SerialString(String content) {
            this.content = content.getBytes();
        }
    }
}
