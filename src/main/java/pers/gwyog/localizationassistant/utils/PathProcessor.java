package pers.gwyog.localizationassistant.utils;

import java.io.File;
import java.nio.file.Path;

public class PathProcessor {
    private String path;
    private String fileName;
    private String fileExt;
    private String suffix;

    public PathProcessor(Path path) {
        this(path.toString());
    }

    public PathProcessor(String fullPath) {
        //对目录进行拆分，找出文件全名
        //substring不包含末尾但begin包含，所以需要+1
        path = fullPath.substring(0, fullPath.lastIndexOf(File.separatorChar) + 1);
        String fullFileName = fullPath.substring(fullPath.lastIndexOf(File.separatorChar) + 1);
        //进一步拆分文件全名为文件名和扩展名
        fileName = fullFileName.substring(0, fullFileName.lastIndexOf('.'));
        fileExt = fullFileName.substring(fullFileName.lastIndexOf('.'));
        suffix = "";
    }

    public boolean isExtNotExist() {
        return fileExt.isEmpty();
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFullPath() {
        return path + fileName + suffix + fileExt;
    }
}
