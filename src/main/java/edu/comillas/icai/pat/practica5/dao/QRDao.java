package edu.comillas.icai.pat.practica5.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "QR_INFO")
public class QRDao {

    @Id
    @Column("ID")
    private Integer id;

    @Column("QR_NAME")
    private String name;

    @Column("QR_SIZE")
    private String size;

    @Column("QR_IMAGE")
    private byte[] image;

    public QRDao() {}

    public QRDao(String name, String size, byte[] image) {
        this.name = name;
        this.size = size;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }


}
