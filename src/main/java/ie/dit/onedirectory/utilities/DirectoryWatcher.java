package ie.dit.onedirectory.utilities;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import ie.dit.onedirectory.services.FailedCallDataServiceLocal;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.io.FilenameUtils;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class DirectoryWatcher {

	private static String fileDirectory = "/Users/Darren/Project/";
	private WatchService watcher;
	private Path dir;
	private WatchKey key;
	@Resource
	private TimerService timerService;
	@EJB
	FailedCallDataServiceLocal service;

	@PostConstruct
	public void onStart() throws InterruptedException {
		try {
			watcher = FileSystems.getDefault().newWatchService();
			dir = Paths.get(fileDirectory);
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
			System.out.println("Watcher added for " + dir.getFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		 timerService.createIntervalTimer(new Date(), new Date().getTime(), new TimerConfig());
	}

	@Lock(LockType.READ)
	@Schedule(hour = "*", minute = "*", second = "*/10")
	public void run() throws InterruptedException {
		try {
			key = watcher.take();
		} catch (InterruptedException ex) {
			System.err.println(ex);
		}
		for (WatchEvent<?> event : key.pollEvents()) {
			WatchEvent.Kind<?> kind = event.kind();
			@SuppressWarnings("unchecked")
			WatchEvent<Path> ev = (WatchEvent<Path>) event;
			Path fileName = ev.context();
			String extension = FilenameUtils.getExtension(fileName.toString());
			if (kind == ENTRY_CREATE) {
				System.out.println("File Created.");
				if (extension.equals("xls")) {
					try {
						service.addFromFile(fileDirectory + fileName);
						System.out.println("Data from file " + fileName + " added successfully");
					} catch (IOException e) {
						System.out.println(fileName + " has incorrect layout for upload.");
					}
				} else {
					System.out.println("File " + fileName + " is not a valid file type for upload.");
				}
			} else if (kind == ENTRY_MODIFY) {
				System.out.println("File Modified");
				if (extension.equals("xls")) {
					try {
						service.addFromFile(fileDirectory + fileName);
					} catch (IOException e) {
						System.out.println(fileName + " does not contain correct layout.");
					}
				} else {
					System.out.println("File " + fileName + " is not a valid file type for upload.");
				}
			} else if (kind == ENTRY_DELETE) {
				System.out.println("File " + fileName + " Deleted!");
			}
		}
		boolean valid = key.reset();
		if (!valid) {
			System.out.println("key reset error");
		}

	}
	
	@Timeout
	public void onTimeout(Timer timer) {
		System.out.println("Bean timed out");
	}
	
	@PreDestroy
	public void onDestroy(){
		try{
		watcher.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
