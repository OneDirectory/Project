package ie.dit.onedirectory.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import ie.dit.onedirectory.dao.UserDAO;
import ie.dit.onedirectory.dao.jpa.UserDAOImplemetation;
import ie.dit.onedirectory.entities.User;
import ie.dit.onedirectory.rest.UserREST;
import ie.dit.onedirectory.services.UserServiceLocal;
import ie.dit.onedirectory.services.ejbs.UserServiceEJB;
import ie.dit.onedirectory.utilities.DataValidator;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserTest {

	@Deployment
	public static Archive<?> createDeployment() {

		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackages(true,
						"ie.dit.onedirectory.dao",
						"ie.dit.onedirectory.dao.jpa",
						"ie.dit.onedirectory.entities",
						"ie.dit.onedirectory.entities.pks",
						"ie.dit.onedirectory.rest",
						"ie.dit.onedirectory.services",
						"ie.dit.onedirectory.services.ejbs",
						"ie.dit.onedirectory.utilities"
						)
						.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
						.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		
	}
	

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@EJB
	private UserServiceLocal service;

	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
		startTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

	@Test
	public void entityTest() throws Exception {
		User user = new User(1, "admin", "pass", "Calvin", "McGowan");
		em.persist(user);

		Integer findNum = 1;

		User getUser = em.find(User.class, findNum);
		assertEquals("Insertion Failed", "Calvin", getUser.getUserFName());

		getUser.setUserFName("Brian");
		User checkUpdatedUser = em.find(User.class, findNum);
		assertEquals("Update Failed", "Brian", checkUpdatedUser.getUserFName());

		em.remove(checkUpdatedUser);
		User shouldBeNull = em.find(User.class, findNum);
		assertNull("Event Cause Failed to delete", shouldBeNull);

	}

	@Test
	public void serviceLocalTest() {
		User user = new User(1, "admin", "pass", "Calvin", "McGowan");
		user.setUserID(1);
		user.setUserSName("McGowan");
		user.setUserPassword("pass");
		user.setUserType("admin");
		User userTwo = new User(2, "admin", "pass", "Brian", "Cowzer");
		service.addUser(user);

		assertEquals("UserService Failed to Add", service.getUserList().size(), 1);

		service.addUser(userTwo);

		assertEquals("UserService Failed to Add", service.getUserList().size(), 2);

		assertEquals("pass",service.findByID(1).getUserPassword());
		assertEquals("admin",service.findByID(1).getUserType());
		assertEquals("McGowan",service.findByID(1).getUserSName());
		assertEquals(1,service.findByID(1).getUserID());
		

	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from User").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}

}