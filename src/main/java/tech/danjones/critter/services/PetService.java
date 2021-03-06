package tech.danjones.critter.services;

import tech.danjones.critter.entities.Customer;
import tech.danjones.critter.entities.Pet;
import tech.danjones.critter.repository.CustomerRepository;
import tech.danjones.critter.repository.PetRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    private final CustomerRepository customerRepository;

    private final PetRepository petRepository;

    public PetService(CustomerRepository customerRepository,
        PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public Pet savePet(Pet pet, long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.addPet(pet);
        customerRepository.save(customer);
        return pet;
    }

    public Pet getPetById(long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getPetsById(List<Long> petIds) {
        return petRepository.findAllById(petIds);

    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(long ownerId) {
        return petRepository.getPetsByCustomerId(ownerId);
    }
}
