package lv.rgl.mla.service.client;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.infrastructure.enums.RiskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Loan getClientLoan(String ip, Long loanId) {
        List<Loan> clientLoans = getLoansByClientIp(ip).stream().filter(cl -> cl.getRiskStatus() == RiskStatus.OK && cl.getId().equals(loanId)).collect(Collectors.toList());
        if (clientLoans != null && clientLoans.size() > 0) {
            return clientLoans.get(0);
        } else {
            throw new InvalidParameterException("No loan to extend with id: " + loanId);
        }
    }

    @Override
    public List<Loan> getLoansByClientIp(String ip) {
        Client client = clientRepository.findByIp(ip);
        if (client != null) {
            return client.getLoans();
        } else {
            throw new InvalidParameterException("No client found with ip: ".concat(ip));
        }
    }

    @Override
    public Client getClientForLoan(String ip) {
        Client client = clientRepository.findByIp(ip);
        if (client == null) {
            client = new Client();
            client.setIp(ip);
            client = clientRepository.save(client);
        }
        return client;
    }
}
