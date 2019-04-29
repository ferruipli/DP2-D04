
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Provider;

@Service
@Transactional
public class ProviderService {

	// Managed repository ---------------------------------------------

	@Autowired
	private ProviderRepository	providerRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;


	//Constructor ----------------------------------------------------

	public ProviderService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------

	public Provider create() {
		Provider result;

		result = new Provider();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.PROVIDER));

		return result;
	}

	public Provider findOne(final int providerId) {
		Provider result;

		result = this.providerRepository.findOne(providerId);
		Assert.notNull(result);

		return result;
	}

	public Provider findOneToDisplayEdit(final int providerId) {
		Assert.isTrue(providerId != 0);

		Provider result, principal;

		principal = this.findByPrincipal();
		result = this.providerRepository.findOne(providerId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == providerId);

		return result;
	}

	public Provider save(final Provider provider) {
		Provider result;

		result = (Provider) this.actorService.save(provider);

		return result;
	}

	public Collection<Provider> findAll() {
		Collection<Provider> result;

		result = this.providerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// Other business methods ----------------------------------------
	public Collection<Provider> topFiveProviders() {
		Collection<Provider> results;
		Pageable page;
		Page<Provider> providers;

		page = new PageRequest(0, 5);
		providers = this.providerRepository.topFiveProviders(page);

		results = providers.getContent();

		return results;
	}

	public Provider findByPrincipal() {
		Provider result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	private Provider findByUserAccount(final int userAccountId) {
		Provider result;

		result = this.providerRepository.findByUserAccount(userAccountId);

		return result;
	}

}
