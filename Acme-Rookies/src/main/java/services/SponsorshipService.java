
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SponsorshipRepository;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository ------------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	// Other supporting services -----------------

	// Constructors ------------------------------
	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods -----------------------

	// Other business methods --------------------
	public Double[] dataOfSponsorshipPerProvider() {
		Double[] results;

		results = this.sponsorshipRepository.dataOfSponsorshipPerProvider();

		return results;
	}

}
