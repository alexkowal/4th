package rogrammDefender;

import java.io.Serializable;
import java.util.Date;

public class MyFile implements Serializable {
    private String name;

    private Date lastModified;

    private Date createTime;

    private Long size;

    private Long fileKey;

    private String extension;

    private Integer hash;

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Integer getHash() {


        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }


    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getFileKey() {
        return fileKey;
    }

    public void setFileKey(Long fileKey) {
        this.fileKey = fileKey;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyFile myFile = (MyFile) o;

        if (createTime != null ? !createTime.equals(myFile.createTime) : myFile.createTime != null) return false;
        return fileKey != null ? fileKey.equals(myFile.fileKey) : myFile.fileKey == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        //result = name != null ? name.hashCode() : 0;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (fileKey != null ? fileKey.hashCode() : 0);
        return result;
    }
}
