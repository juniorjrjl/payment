package com.ead.payment.adapter.outbound.persistence;

import com.ead.payment.adapter.mapper.UserMapper;
import com.ead.payment.adapter.outbound.persistence.entity.UserEntity;
import com.ead.payment.adapter.outbound.persistence.entity.UserEntity_;
import com.ead.payment.core.domain.UserDomain;
import com.ead.payment.core.port.persistence.UserPersistencePort;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class UserPersistencePortImpl implements UserPersistencePort {

    private final EntityManager entityManager;
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public UserDomain save(final UserDomain domain) {
        var entity = userJpaRepository.save(userMapper.toEntity(domain));
        return userMapper.toDomain(entity);
    }

    @Override
    public void update(final UserDomain domain) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var update = criteriaBuilder.createCriteriaUpdate(UserEntity.class);
        var root = update.from(UserEntity.class);
        update.set(root.get(UserEntity_.fullName), domain.fullName());
        update.set(root.get(UserEntity_.email), domain.email());
        update.set(root.get(UserEntity_.userStatus), domain.userStatus());
        update.set(root.get(UserEntity_.userType), domain.userType());
        update.set(root.get(UserEntity_.phoneNumber), domain.phoneNumber());
        update.set(root.get(UserEntity_.cpf), domain.cpf());
        update.where(criteriaBuilder.equal(root.get(UserEntity_.id), domain.id()));
        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public void delete(final UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public Optional<UserDomain> findById(final UUID id) {
        return userJpaRepository.findById(id).map(userMapper::toDomain);
    }

}
