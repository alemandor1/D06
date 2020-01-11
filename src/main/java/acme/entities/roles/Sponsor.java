
package acme.entities.roles;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.CreditCardNumber;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "user_account_id")
})
public class Sponsor extends UserRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	private String				organisationName;

	@CreditCardNumber
	private String				creditCard;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
