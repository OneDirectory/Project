package ie.dit.onedirectory.utilities;

import ie.dit.onedirectory.dao.EventCauseDAO;
import ie.dit.onedirectory.dao.FailureClassDAO;
import ie.dit.onedirectory.dao.MarketOperatorDAO;
import ie.dit.onedirectory.dao.UserEquipmentDAO;
import ie.dit.onedirectory.entities.EventCause;
import ie.dit.onedirectory.entities.FailedCallData;
import ie.dit.onedirectory.entities.FailureClass;
import ie.dit.onedirectory.entities.MarketOperator;
import ie.dit.onedirectory.entities.UserEquipment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class DataValidator {
	
//	@PersistenceContext
//	EntityManager entityManager;
	
	@EJB
	EventCauseDAO eventCauseDAO;
	@EJB
	FailureClassDAO failureClassDAO;
	@EJB
	MarketOperatorDAO marketOperatorDAO;
	@EJB
	UserEquipmentDAO userEquipmentDAO;
	
	static private ArrayList<EventCause> validEventCauseList = new ArrayList<EventCause>();
	static private ArrayList<FailureClass> validFailureClassList = new ArrayList<FailureClass>();
	static private ArrayList<MarketOperator> validMarketOperatorList = new ArrayList<MarketOperator>();
	static private ArrayList<UserEquipment> validUserEquipmentList = new ArrayList<UserEquipment>();
	private Logger logger;
	private FileHandler fileHandler;
	
	@PostConstruct
	public void onStart(){
		logger = Logger.getLogger("ErrorLog");
		try {
			fileHandler = new FileHandler("/Users/Darren/Project/errors.log");
			logger.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
			logger.info("Log created on " + new Date());
		}
		catch(SecurityException secEx){
			secEx.printStackTrace();
		}
		catch(IOException ioEx){
			ioEx.printStackTrace();
		}
		for(EventCause eventCause: eventCauseDAO.getAllEventCauses()){
			EventCause validEventCause = new EventCause();
			System.out.println("DAO EventCause:" + eventCause.getCauseCode() + " " + eventCause.getEventId());
			validEventCause.setCauseCode(eventCause.getCauseCode());
			validEventCause.setEventId(eventCause.getEventId());
			System.out.println("Valid EventCause:" + validEventCause.getCauseCode() + " " + validEventCause.getEventId());
			validEventCauseList.add(validEventCause);
		}
		System.out.println("EventCause List Loaded.");
		for(FailureClass failureClass: failureClassDAO.getAllFailureClasses()){
			FailureClass validFailureClass = new FailureClass();
			validFailureClass.setFailureId(failureClass.getFailureId());
			validFailureClassList.add(validFailureClass);
		}
		System.out.println("FailureClass List Loaded.");
		for(MarketOperator marketOperator: marketOperatorDAO.getAllMarketOperators()){
			MarketOperator validMarketOperator = new MarketOperator();
			validMarketOperator.setMarketId(marketOperator.getMarketId());
			validMarketOperator.setOperatorId(marketOperator.getOperatorId());
			validMarketOperatorList.add(validMarketOperator);
		}
		System.out.println("MarketOperator List Loaded.");
		for(UserEquipment userEquipment: userEquipmentDAO.getAllUserEquipment()){
			UserEquipment validUserEquipment = new UserEquipment();
			validUserEquipment.setTac(userEquipment.getTac());
			validUserEquipmentList.add(validUserEquipment);
		}
		System.out.println("UserEquipment List Loaded.");
	}
	
	public boolean isValid(FailedCallData failedCallData){
		boolean eventCauseValid = false;
		boolean failureClassValid = false;
		boolean marketOperatorValid = false;
		boolean userEquipmentValid = false;
		Integer eventIdToCheck = failedCallData.getEventCause().getEventId();
		Integer causeCodeToCheck = failedCallData.getEventCause().getCauseCode();
		Integer failureIdToCheck = failedCallData.getFailureClass().getFailureId();
		Integer operatorIdToCheck = failedCallData.getMarketOperator().getOperatorId();
		Integer marketIdToCheck = failedCallData.getMarketOperator().getMarketId();
		Integer tacToCheck = failedCallData.getUserEquipment().getTac();
		Date dateToCheck = failedCallData.getDateTime();
		
		for(EventCause eventCause: validEventCauseList){
			if((eventCause.getCauseCode().equals(causeCodeToCheck) && eventCause.getEventId().equals(eventIdToCheck))){
				eventCauseValid = true;
				break;
			}
		}
		
		if(!eventCauseValid){
			logger.log(Level.INFO, "Invalid Entries for Event ID: " + eventIdToCheck + "  and Cause Code: " + causeCodeToCheck, failedCallData);
			return false;
		}
		
		for(FailureClass failureClass: validFailureClassList){
			if(failureClass.getFailureId().equals(failureIdToCheck)){
				failureClassValid = true;
				break;
			}
		}
		
		if(!failureClassValid){
			logger.log(Level.INFO, "Invalid Failure ID Entry: " + failureIdToCheck, failedCallData);
			return false;
		}
		
		for(MarketOperator marketOperator: validMarketOperatorList){
			if((marketOperator.getOperatorId().equals(operatorIdToCheck)
					&& marketOperator.getMarketId().equals(marketIdToCheck))){
				marketOperatorValid = true;
				break;
			}
		}
		
		if(!marketOperatorValid){
			logger.log(Level.INFO, "Invalid Market ID: " + marketIdToCheck + "  and Operator Id: " + operatorIdToCheck + " entries.", failedCallData);
			return false;
		}
		
		for(UserEquipment userEquipment: validUserEquipmentList){
			if((userEquipment.getTac().equals(tacToCheck))){
				userEquipmentValid = true;
				break;
			}
		}
		
		if(!userEquipmentValid){
			logger.log(Level.INFO, "Invalid Type Allocation Code Entry: " + tacToCheck, failedCallData);
			return false;
		}
		
		if(dateToCheck.after(new Date())){
			logger.log(Level.INFO, "Invalid Date Entry: " + dateToCheck, failedCallData);
			return false;
		}
		
		return true;
	}

	public boolean validFailureIdAndCauseCodeTypes(String failureIdString, String causeCodeString){
		try{
			Integer failureId = Integer.valueOf(failureIdString);
		}
		catch(NumberFormatException nfEx){
			logger.log(Level.INFO, "Invalid entry for FailureID: " + failureIdString);
			return false;
		}
		try{
			Integer causeCode = Integer.valueOf(causeCodeString);
		}
		catch(NumberFormatException nfEx){
			logger.log(Level.INFO, "Invalid entry for CauseCode: " + causeCodeString);
			return false;
		}
		return true;
	}
	
	@PreDestroy
	public void onDestroy(){
		
	}
	
	public ArrayList<EventCause> getValidEventCauseList() {
		return validEventCauseList;
	}

	public void setValidEventCauseList(ArrayList<EventCause> validEventCauseList) {
		this.validEventCauseList = validEventCauseList;
	}

	public ArrayList<FailureClass> getValidFailureClassList() {
		return validFailureClassList;
	}

	public void setValidFailureClassList(
			ArrayList<FailureClass> validFailureClassList) {
		this.validFailureClassList = validFailureClassList;
	}

	public ArrayList<MarketOperator> getValidMarketOperatorList() {
		return validMarketOperatorList;
	}

	public void setValidMarketOperatorList(
			ArrayList<MarketOperator> validMarketOperatorList) {
		this.validMarketOperatorList = validMarketOperatorList;
	}

	public ArrayList<UserEquipment> getValidUserEquipmentList() {
		return validUserEquipmentList;
	}

	public void setValidUserEquipmentList(
			ArrayList<UserEquipment> validUserEquipmentList) {
		this.validUserEquipmentList = validUserEquipmentList;
	}
	
	
	
}
