package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.repository;

import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Roles , Long> {

    Roles findByRole(String role);

}
