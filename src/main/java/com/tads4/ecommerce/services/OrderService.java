package com.tads4.ecommerce.services;

import com.tads4.ecommerce.dtos.OrderDTO;
import com.tads4.ecommerce.entities.Order;
import com.tads4.ecommerce.entities.Payment;
import com.tads4.ecommerce.entities.User;
import com.tads4.ecommerce.repositories.OrderRepository;
import com.tads4.ecommerce.repositories.PaymentRepository;
import com.tads4.ecommerce.repositories.UserRepository;
import com.tads4.ecommerce.services.exceptions.DatabaseException;
import com.tads4.ecommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;

    public OrderDTO findById(Long id) {
        return new OrderDTO(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado")));
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<Order> orders = repository.findAll(pageable);
        return orders.map(OrderDTO::new);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Payment payment = paymentRepository.getReferenceById(dto.getPayment());
        User user = userRepository.getReferenceById(dto.getClient());
        Order entity = new Order();

        entity.setMoment(dto.getMoment());
        entity.setStatus(dto.getStatus());
        entity.setPayment(payment);
        entity.setClient(user);
        entity = repository.save(entity);
        return new OrderDTO(entity);
    }

    @Transactional
    public OrderDTO update(Long id, OrderDTO dto) {
        try {
            Order entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            return new OrderDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integrigadade referencial");
        }
    }

    private void copyDtoToEntity(OrderDTO dto, Order entity) {
        Payment payment = paymentRepository.getReferenceById(dto.getPayment());
        User user = userRepository.getReferenceById(dto.getClient());
        entity.setMoment(dto.getMoment());
        entity.setStatus(dto.getStatus());
        entity.setPayment(payment);
        entity.setClient(user);
    }
}