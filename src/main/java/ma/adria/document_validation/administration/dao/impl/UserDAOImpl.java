package ma.adria.document_validation.administration.dao.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IUserDAO;
import ma.adria.document_validation.administration.exception.ResourceNotFoundException;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.ErrorCode;
import ma.adria.document_validation.administration.model.enums.UserStatus;
import ma.adria.document_validation.administration.reposetiry.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDAOImpl implements IUserDAO {
	private final UserRepository userRepository;
	
	@Override
	public List<Utilisateur> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public Utilisateur save(Utilisateur user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public Utilisateur findByUserName(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean existsByUserName(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}

	@Override
	public Utilisateur findById(UUID id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id)
				.orElseThrow(
						()->new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND_ID));


	}

	@Override
	public Page<Utilisateur> getPage(Specification<Utilisateur> userSpecification, Pageable pageable) {
		// TODO Auto-generated method stub
		return userRepository.findAll(userSpecification, pageable);
	}

	@Override
	public Utilisateur findByKeycloakUserId(String string) {
		// TODO Auto-generated method stub
		Utilisateur user=userRepository.findByKeycloakId(string);
		if (user == null) {
            throw new ResourceNotFoundException(ErrorCode.ACCOUNT_USER_NOT_FOUND_ID);
        }
		return user;
	}

	
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return userRepository.count();
	}

	@Override
	public long countByStatus(UserStatus userStatus) {
		// TODO Auto-generated method stub
		return userRepository.countByStatut(userStatus.toString());
	}

}
