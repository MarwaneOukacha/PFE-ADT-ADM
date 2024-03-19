package ma.adria.document_validation.administration.dao.impl;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IUserDAO;
import ma.adria.document_validation.administration.exception.ResourceNotFoundException;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.ErrorCode;
import ma.adria.document_validation.administration.reposetiry.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDAOImpl implements IUserDAO {

    private final UserRepository userRepository;

    @Override
    public Utilisateur save(Utilisateur user) {
        return userRepository.save(user);
    }

    @Override
    public Utilisateur findByUserName(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUserName(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Utilisateur findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND_ID));
    }

    @Override
    public Page<Utilisateur> getPage(Specification<Utilisateur> userSpecification, Pageable pageable) {
        return userRepository.findAll(userSpecification, pageable);
    }

    @Override
    public Utilisateur findByKeycloakUserId(String string) {
        Utilisateur user = userRepository.findByKeycloakId(string);
        if (user == null) {
            throw new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND_ID);
        }
        return user;
    }

    @Override
    public boolean exists() {
        return userRepository.existsByIdNotNull();
    }

}
