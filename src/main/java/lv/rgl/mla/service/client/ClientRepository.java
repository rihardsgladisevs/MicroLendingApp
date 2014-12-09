package lv.rgl.mla.service.client;

import lv.rgl.mla.domain.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByIp(String ip);
}
