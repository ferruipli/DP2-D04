
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import domain.Curriculum;
import domain.Hacker;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService {

	// Managed repository ------------------------------------------------

	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private HackerService				hackerService;

	@Autowired
	private UtilityService				utilityService;


	// Constructors ------------------------------------------------------

	public MiscellaneousDataService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public MiscellaneousData create() {
		MiscellaneousData result;

		result = new MiscellaneousData();

		return result;
	}

	private MiscellaneousData saveInternal(final MiscellaneousData miscellaneousData) {
		this.utilityService.checkURLS(miscellaneousData.getAttachments());

		MiscellaneousData saved;

		saved = this.miscellaneousDataRepository.save(miscellaneousData);

		return saved;
	}

	// Editing an existing MiscellaneousData
	public MiscellaneousData save(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);
		Assert.isTrue(this.miscellaneousDataRepository.exists(miscellaneousData.getId()));
		this.checkOwner(miscellaneousData.getId());
		this.checkCurriculumIsOriginal(miscellaneousData);

		final MiscellaneousData saved = this.saveInternal(miscellaneousData);

		return saved;
	}

	// Creating new MiscellaneousData
	public MiscellaneousData save(final MiscellaneousData miscellaneousData, final int curriculumId) {
		Assert.notNull(miscellaneousData);
		Assert.isTrue(!this.miscellaneousDataRepository.exists(miscellaneousData.getId()));

		final MiscellaneousData saved = this.saveInternal(miscellaneousData);
		final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);

		this.curriculumService.addMiscellaneousData(curriculum, saved);

		return saved;
	}

	public void delete(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);
		Assert.isTrue(this.miscellaneousDataRepository.exists(miscellaneousData.getId()));
		this.checkOwner(miscellaneousData.getId());
		this.checkCurriculumIsOriginal(miscellaneousData);

		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByMiscellaneousDataId(miscellaneousData.getId());
		curriculum = this.curriculumService.findOneToEdit(curriculumId);
		this.curriculumService.removeMiscellaneousData(curriculum, miscellaneousData);
		this.miscellaneousDataRepository.delete(miscellaneousData);
	}

	public MiscellaneousData findOne(final int miscellaneousDataId) {
		MiscellaneousData result;

		result = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public MiscellaneousData findOneToEdit(final int miscellaneousDataId) {
		MiscellaneousData result;

		result = this.findOne(miscellaneousDataId);
		this.checkOwner(miscellaneousDataId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	protected Collection<MiscellaneousData> copy(final Collection<MiscellaneousData> miscellaneousData) {
		final Set<MiscellaneousData> result = new HashSet<>();

		for (final MiscellaneousData m : miscellaneousData) {
			final MiscellaneousData copy = new MiscellaneousData();

			copy.setAttachments(m.getAttachments());
			copy.setText(m.getText());

			result.add(copy);
		}

		return result;
	}

	private void checkOwner(final int miscellaneousDataId) {
		Hacker principal, owner;

		principal = this.hackerService.findByPrincipal();
		owner = this.hackerService.findByMiscellaneousDataId(miscellaneousDataId);

		Assert.isTrue(principal.equals(owner));
	}

	private void checkCurriculumIsOriginal(final MiscellaneousData miscellaneousData) {
		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByMiscellaneousDataId(miscellaneousData.getId());
		curriculum = this.curriculumService.findOne(curriculumId);

		Assert.isTrue(curriculum.getIsOriginal());
	}
}
