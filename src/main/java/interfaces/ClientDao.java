package interfaces;

import client.domain.Client;


public interface ClientDao  extends Crud<Client>{
    Boolean deleteIdAboveThan (Long id);
}
