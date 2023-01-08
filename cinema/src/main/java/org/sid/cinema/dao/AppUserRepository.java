package org.sid.cinema.dao;


import org.sid.cinema.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByUsername(String username);

    public AppUser findByEmail(String email);
}
