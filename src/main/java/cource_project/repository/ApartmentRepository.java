package cource_project.repository;

import cource_project.models.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    Apartment findApartmentByNumber(int number);
}
