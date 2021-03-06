package com.swastikairhub.SwastiKAirHubBackend.User;

import com.swastikairhub.SwastiKAirHubBackend.Role.ERole;
import com.swastikairhub.SwastiKAirHubBackend.Role.Role;
import com.swastikairhub.SwastiKAirHubBackend.Role.RoleRepository;
import com.swastikairhub.SwastiKAirHubBackend.Util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo repo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public String save(SignUpRequest request) {
        checkValidation(request);
        User customer = toCustomer(request);
        User saveCustomer = repo.save(customer);
        return "user registered successfully";
    }

    @Override
    public Iterable<User> findAll() {
        return repo.findAll();
    }

    @Override
    public String update(String id, SignUpRequest request) {
        checkValidation(request);
        Optional<User> findCustomer = repo.findById(id);
        if (findCustomer.isPresent()) {
            User updateCustomer = toCustomer(request);
            updateCustomer.setId(id);
            User updatedCustomer = repo.save(updateCustomer);
            return "user details updated";
        } else
            throw new NullPointerException("The customer does not exist");
    }

    @Override
    public String delete(String id) {
        Optional<User> findCustomer = repo.findById(id);
        if (findCustomer.isPresent()) {
            User deleteCustomer = findCustomer.get();
            deleteCustomer.setStatus("terminated");
            User deletedCustomer = repo.save(deleteCustomer);
            return "user deleted";
        } else
            throw new NullPointerException("The Customer Doesn't Exist");
    }

    @Override
    public CustomerDetailResponse findById(String id) {
        Optional<User> findCustomer = repo.findById(id);
        if (findCustomer.isPresent()) {
            User customer = findCustomer.get();
            return toCustomerDTO(customer);
        } else
            throw new NullPointerException("The Customer Doesn't Exist");
    }

    private CustomerDetailResponse toCustomerDTO(User customer) {
        return CustomerDetailResponse.builder().
                id(customer.getId()).
                email(customer.getEmail()).
                firstName(customer.getFirstName()).
                lastName(customer.getLastName()).
                middleName(customer.getMiddleName()).
                phoneNumber(customer.getPhoneNumber()).
                build();
    }

    private User toCustomer(SignUpRequest request) {
        User customer=new User();
        Set<Role> roles = getRoles();
        customer.setFirstName(request.getFirstName());
        customer.setEmail(request.getEmail());
        customer.setLastName(request.getLastName());
        customer.setMiddleName(request.getMiddleName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setUsername(request.getUsername());
        customer.setPassword(encoder.encode(request.getPassword()));
        customer.setRoles(roles);
        customer.setStatus("Registered");
        return customer;
    }

    private Set<Role> getRoles() {
        Role role=roleRepository.findByName(ERole.ROLE_USER);
        Set<Role> roles= new HashSet<>();
        roles.add(role);
        return roles;
    }

    private void checkValidation(SignUpRequest request) {
        checkEmail(request);
        checkUsername(request);
        checkPhoneNumber(request);
    }

    private void checkEmail(SignUpRequest request) {
        Optional<User> customer=repo.findCustomerByEmail(request.getEmail());
        if (customer.isPresent())
            throw new CustomException(CustomException.Type.EMAIL_ALREADY_EXITS);
    }
    private void checkUsername(SignUpRequest request) {
        Optional<User> customer=repo.findCustomerByUsername(request.getUsername());
        if (customer.isPresent())
            throw new CustomException(CustomException.Type.USERNAME_ALREADY_EXIST);
    }
    private void checkPhoneNumber(SignUpRequest request) {
        Optional<User> customer=repo.findCustomerByPhoneNumber(request.getPhoneNumber());
        if (customer.isPresent())
            throw new CustomException(CustomException.Type.PHONE_NUMBER_ALREADY_EXISTS);
    }
}
