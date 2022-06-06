package algorithms;

import config.Constants;
import domain.DataInfo;
import exceptions.AlgorithmException;
import exceptions.ConsumerException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public abstract class AbstractAlgoritm implements Algorithm {
    protected DataInfo dataInfo = new DataInfo();

    public boolean cleanFirstTime = true;

    @Override
    public AbstractAlgoritm setFileName(File file) {
        dataInfo.setKey(getAlgorithmKey());
        dataInfo.setFile(file);
        return this;
    }


    @Override
    public AbstractAlgoritm setDestinationFolder(String destinationFolder) {
        dataInfo.setDestinationFolder(destinationFolder);
        return this;
    }

    public void reset() {
        dataInfo = new DataInfo();
    }

    protected void reading(ConsumerException<char[], Integer, AlgorithmException> exec) throws AlgorithmException {
        char[] buffer = new char[Constants.BUFFER_READING_SIZE];

        File filename = new File(dataInfo.getPath() + "\\" + dataInfo.getFileName());
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            int size;
            while ((size = br.read(buffer)) != -1) {
                exec.accept(buffer, size);
                buffer = new char[Constants.BUFFER_READING_SIZE];
            }
            exec.accept(new char[1], 0);
            cleanFirstTime = true; //prepare for the other reading

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new AlgorithmException("File could not be found at the location: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AlgorithmException("Unexpected error occurred!");
        }
    }

    protected void writing(char[] exec, boolean isCompressing) throws AlgorithmException {
        String path;
        String filename;
        if (isCompressing) {
            filename = dataInfo.getCompressFileName();
            path = dataInfo.getCompressPath();
        } else {
            filename = dataInfo.getDecompressFileName();
            path = dataInfo.getDecompressPath();
        }
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new AlgorithmException("Directory could not be created for the designed path: " + path);
        }
        File file = new File(path, filename);
        try {
            FileOutputStream fw;
            if (file.exists() && !cleanFirstTime)
                fw = new FileOutputStream(file, true);
            else
                fw = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fw));
            bw.write(exec);
            bw.flush();
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new AlgorithmException("File could not be found at the location: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AlgorithmException("Unexpected error occurred!");
        }
        cleanFirstTime = false;
    }

    @Override
    public DataInfo exec() throws AlgorithmException {
        compress();
        dataInfo.setCompressPathForUse();
        decompress();
        dataInfo.setOriginalPathForUse();
        return dataInfo;
    }

    @Override
    public DataInfo probe(File probeFile) throws AlgorithmException {
        File file = dataInfo.getOriginalFile();
        setFileName(probeFile);
        compress();
        setFileName(file);
        return dataInfo;
    }

    @Override
    public DataInfo getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }
}
