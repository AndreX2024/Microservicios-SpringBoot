package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Pay;
import dev.carloscastano.get.entities.PayMethod;
import dev.carloscastano.get.repository.PayMethodRepository;
import dev.carloscastano.get.repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PayMethodService implements IPayMethodService {

    @Autowired
    private PayMethodRepository payMethodRepository;

    @Autowired
    private PayRepository payRepository;

    @Override
    public List<PayMethod> findAll() {
        return (List<PayMethod>) payMethodRepository.findAll();
    }

    @Override
    public Optional<PayMethod> findById(Long id) {
        return payMethodRepository.findById(id);
    }

    @Override
    public Optional<PayMethod> findByMetodo(String metodo) {
        return payMethodRepository.findByMetodo(metodo);
    }

    @Override
    public List<Pay> findPaysByMetodoId(Long metodoId) {
        return payRepository.findByMetodoPago_IdMetodo(metodoId);
    }

    @Override
    public PayMethod save(PayMethod payMethod) {
        return payMethodRepository.save(payMethod);
    }

    @Override
    public PayMethod partialUpdate(PayMethod payMethod, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(PayMethod.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, payMethod, value);}
        });
        return payMethodRepository.save(payMethod);
    }

    @Override
    public void deleteById(Long id) {
        payMethodRepository.deleteById(id);
    }
}