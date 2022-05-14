package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.repository;


import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository< Img , Long> {
}
