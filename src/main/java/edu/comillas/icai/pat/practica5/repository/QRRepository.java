package edu.comillas.icai.pat.practica5.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import edu.comillas.icai.pat.practica5.dao.QRDao;


public interface QRRepository extends CrudRepository<QRDao, Integer>{
    @Modifying
    @Query(value = "INSERT INTO QR_INFO (QR_NAME, QR_SIZE, QR_IMAGE) VALUES (:name, :size, :image)")
    void guardar(@Param("name") String name, @Param("size") String size, @Param("image") byte[] image);

    @Query(value = "SELECT * FROM QR_INFO WHERE QR_NAME = :name")
    QRDao findByName(@Param("name") String name);

}
