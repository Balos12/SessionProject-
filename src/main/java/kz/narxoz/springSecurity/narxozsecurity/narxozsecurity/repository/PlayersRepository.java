package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.repository;

import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PlayersRepository extends JpaRepository<Players, Long> {
}
