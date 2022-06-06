package domain;

import config.Prop;
import domain.time.ExecutionTime;
import exceptions.AlgorithmException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Random;

public class DataInfo extends ExecutionTime {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String originalFileName;
    private String originalPath;

    private String compressFileName;
    private String compressPath;

    private String decompressFileName;
    private String decompressPath;

    private String fileName;
    private String path;
    private String key;

    private String destinationFolder = null;

    private File originalFile;

    public void setFile(File file) {
        originalFile = file;
        this.originalFileName = file.getName();
        this.originalPath = file.getParent();

        setCompressPath();
        setDecompressPath();
        setOriginalPathForUse();
    }

    private void setCompressPath() {
        compressPath = destinationFolder == null ? originalPath + "\\..\\compressed" : destinationFolder + "SpeedNetTransmission";
        compressFileName = originalFileName.replace(Prop.COMPRESSED_EXTENSION, "").replace(Prop.DECOMPRESSED_EXTENSION, "");
        compressFileName += "." + key + Prop.COMPRESSED_EXTENSION;
    }

    private void setDecompressPath() {
        decompressPath = destinationFolder == null ? originalPath + "\\..\\decompressed" : destinationFolder + "SpeedNetTransmission";
        decompressFileName = originalFileName.replace(Prop.COMPRESSED_EXTENSION, "").replace(Prop.DECOMPRESSED_EXTENSION, "");
        decompressFileName += "." + key + Prop.DECOMPRESSED_EXTENSION;
    }

    public long getCompressedSize() {
        File f = new File(compressPath + "\\" + compressFileName);
        return f.length();
    }

    public long getDecompressedSize() {
        File f = new File(decompressPath + "\\" + decompressFileName);
        return f.length();
    }

    private long getOriginalSize() {
        File f = new File(originalPath + "\\" + originalFileName);
        return f.length();
    }

    public void setCompressPathForUse() {
        path = compressPath;
        fileName = compressFileName;
    }

    public void setDecompressPathForUse() {
        path = decompressPath;
        fileName = decompressFileName;
    }

    public void setOriginalPathForUse() {
        path = originalPath;
        fileName = originalFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getCompressFileName() {
        return compressFileName;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public String getDecompressFileName() {
        return decompressFileName;
    }

    public String getDecompressPath() {
        return decompressPath;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCompressionRate() {
        return df.format((1 - ((double) getCompressedSize() / getOriginalSize())) * 100);
    }

    public void setDestinationFolder(String destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

    public File getOriginalFile() {
        return originalFile;
    }

    public String getProbeFile() {
        return originalPath + "\\..\\probe\\" + originalFileName + Prop.PROBE_EXTENSION;
    }
    public String getProbePath() {
        return originalPath + "\\..\\probe";
    }

    public File createProbeFile(final int noBlocks, final int blockSize) {
        File newFile = null;
        File original = getOriginalFile();
        try (RandomAccessFile raf = new RandomAccessFile(original, "r")) {
            if ((original.length() / noBlocks) - blockSize <= 0) {
                throw new AlgorithmException("Dimensiunea blocurilor si numarul lor depaseste dimensiunea fisierului original");
            }
            Random rdn = new Random();
            byte[] dest = new byte[blockSize];
            int limit = (int) ((original.length() / noBlocks) - blockSize);

            String fullPath = getProbeFile();
            Files.createDirectories(Paths.get(getProbePath()));
            newFile = new File(fullPath);
            newFile.createNewFile();
            FileOutputStream fw = new FileOutputStream(newFile);

            for (int i = 0; i < noBlocks; i++) {
                int randomBlockPosition = i * limit + rdn.nextInt(limit);
                raf.seek(randomBlockPosition);
                int bytesRead = raf.read(dest, 0, blockSize);

                fw.write(dest, 0, bytesRead);
                fw.flush();
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }
}
