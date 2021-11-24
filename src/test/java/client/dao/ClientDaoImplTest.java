package client.dao;

import application.Utils;
import client.domain.Client;
import constants.ClientVar;
import org.testng.annotations.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ClientDaoImplTest {
    Connection connection;
    ClientDaoImpl clientDao;
    
    
    @BeforeMethod
    public void setUp() throws Exception {
        connection = Utils.openConnection();
        clientDao = new ClientDaoImpl(connection);
    }
    
    @Test
    public void getTest() {
        List<Client> clientList = clientDao.getAll();
        List<Long> clientIdList = new ArrayList<>();
        for (Client client: clientList) {
            clientIdList.add(client.getId());
        }
        Client client = clientDao.get(clientIdList.get((int)(Math.random() * clientIdList.size())));
        assertEquals("User isn't correct",client.getName(), clientDao.get(client.getId()).getName());
    }
   
    @Test
    public void getAllTest() {
        int sizeBefore = clientDao.getAll().size();
        Client client = Client.createRandomClient();
        clientDao.create(client);
        int sizeAfter = clientDao.getAll().size();
        assertNotEquals("Size isn't correct",sizeBefore, sizeAfter);
    }
    
    @Test
    public void createTest() {
        Client clientRandom = Client.createRandomClient();
        String nameRandomClient = clientRandom.getName();
        List<Client> clientList = clientDao.getAll();
        Long lastClient = 0L;
        for (Client client:clientList) {
            lastClient = client.getId();
        }
        clientDao.create(clientRandom);
        String nameClientInTable = clientDao.get(lastClient+1).getName();
        assertEquals(nameRandomClient,nameClientInTable);
    }
    
    @Test
    public void updateTest() {
        clientDao.create(Client.createRandomClient());
        List<Client> clientList = clientDao.getAll();
        Long lastClient = 0L;
        for (Client client:clientList) {
            lastClient = client.getId();
        }
        String nameNotUpdated = clientDao.get(lastClient).getName();
        Client clientAfterChange = clientDao.get(lastClient);
        clientAfterChange.setName(ClientVar.NAMES[(int) (Math.random() * ClientVar.NAMES.length)] + "e");
        clientDao.update(lastClient, clientAfterChange);
        assertNotEquals(nameNotUpdated, clientAfterChange.getName());
    }
    
    @Test
    public void deleteTest() {
        clientDao.create(Client.createRandomClient());
        List<Client> clientList = clientDao.getAll();
        Long lastClient = 0L;
        for (Client client:clientList) {
            lastClient = client.getId();
        }
        Long idBeforeDelete = clientDao.get(lastClient).getId();
        clientDao.delete(idBeforeDelete);
        assertNull(clientDao.get(idBeforeDelete).getId());
    }
    
    @Test
    public void deleteIdAboveThanTest() {
        Client randomClient = Client.createRandomClient();
        clientDao.create(randomClient);
        int originalSize = clientDao.getAll().size();
        List<Client> clientList = clientDao.getAll();
        Long lastClient = 0L;
        for (Client client:clientList) {
            lastClient = client.getId();
            }
        clientDao.create(randomClient);
        clientDao.deleteIdAboveThan(lastClient);
        int sizeAfter = clientDao.getAll().size();
        assertEquals(originalSize, sizeAfter);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        Utils.closeConnection(connection);
    }
    

}