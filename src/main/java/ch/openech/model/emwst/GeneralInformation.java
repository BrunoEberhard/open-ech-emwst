package ch.openech.model.emwst;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;

import ch.openech.model.organisation.UidStructure;

public class GeneralInformation {
	public static final GeneralInformation $ = Keys.of(GeneralInformation.class);
	
//	@NotEmpty
//	public Uid uid;

	@NotEmpty
	public final UidStructure uid = new UidStructure();
	
	@Size(255) @NotEmpty
	public String organisationName;
	@NotEmpty @Size(Size.TIME_WITH_MILLIS)
	public LocalDateTime generationTime = LocalDateTime.now();
	@NotEmpty
	public LocalDate reportingPeriodFrom, reportingPeriodTill;
	@NotEmpty
	public TypeOfSubmission typeOfSubmission;
	@NotEmpty
	public FormOfReporting formOfReporting;
	@Size(50) @NotEmpty
	public String businessReferenceId;
	
	public final SendingApplication sendingApplication = new SendingApplication();
}
