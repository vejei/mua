package io.github.zeleven.mua;

public class FileEntity {
    private String name;
    private long lastModified;
    private String absolutePath;

    public FileEntity() {
    }

    public FileEntity(String name, long lastModified, String absolutePath) {
        this.name = name;
        this.lastModified = lastModified;
        this.absolutePath = absolutePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
