package cource_project.service;

import cource_project.models.Apartment;
import org.aspectj.apache.bcel.classfile.ExceptionTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cource_project.models.User;
import cource_project.repository.ApartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public List<Apartment> findAll(){
        return apartmentRepository.findAll();
    }

    public Apartment findOne(int id){
        Optional<Apartment> foundApartment = apartmentRepository.findById(id);
        return foundApartment.orElse(null);
    }

    public List<Apartment> findWithPagination(Integer page, Integer apartmentsPerPage){
        return apartmentRepository.findAll(PageRequest.of(page, apartmentsPerPage)).getContent();
    }

    @Transactional
    public void save(Apartment apartment){
        apartmentRepository.save(apartment);
    }

    @Transactional
    public void update(int id, Apartment updatedApartment){
        Apartment apartmentToBeUpdated = apartmentRepository.findById(id).get();

        updatedApartment.setId(id);
        updatedApartment.setOwner(apartmentToBeUpdated.getOwner());

        apartmentRepository.save(updatedApartment);
    }
    @Transactional
    public void delete(int id) {
        apartmentRepository.deleteById(id);
    }

    public User getApartmentOwner(int id){
        return apartmentRepository.findById(id).map(Apartment::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id){
        apartmentRepository.findById(id).ifPresent(
                apartment -> apartment.setOwner(null)
        );
    }

    @Transactional
    public void assign(int id, User user){
        apartmentRepository.findById(id).ifPresent(
                apartment -> apartment.setOwner(user)
        );
    }

}
