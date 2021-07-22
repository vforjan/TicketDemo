package hu.otp.simple.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.otp.simple.core.domain.UserBankCard;

/**
 * Repository for UserBankCard entites.
 * 
 * @author vforjan
 *
 */
@Repository
public interface UserBankCardsRepository extends JpaRepository<UserBankCard, Integer> {

	UserBankCard findByCardId(String cardId);

}
