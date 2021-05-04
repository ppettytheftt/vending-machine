package com.techelevator;

import java.io.*;

public class Logger implements Closeable{

    private File logFile;
    private PrintWriter writer;

    public Logger(String logFile) {
        this.logFile = new File(logFile);

        if (this.logFile.exists()) {  // don't want to overwrite, want to append
            try {
                writer = new PrintWriter(new FileWriter(this.logFile, true)); // this will append
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {  // file doesn't exit, create new and start at beginning to write
            try {
                writer = new PrintWriter(this.logFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void write (String logMessage){
        writer.println(logMessage);
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
