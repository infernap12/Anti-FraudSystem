package antifraud.api.antifraud.suspiciousIp;

import antifraud.IpAddressValidator;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class SuspiciousIpService {
    final SuspiciousIpRepository repo;

    public boolean exists(String ip) {
        return repo.existsByIp(ip);
    }

    public boolean isValid(String ip) {
        new IpAddressValidator().isValid(ip,null);
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            return false;
        }
        return true;
    }

    public SuspiciousIpService(@Autowired SuspiciousIpRepository repo) {
        this.repo = repo;
    }

    public SuspiciousIp getIp(String ip) {
        return repo
                .findByIp(ip)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ip not found"));
    }

    public SuspiciousIp createIP(String ip) {
        if (repo.existsByIp(ip)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ip already registered");
        }
        val address = new SuspiciousIp(ip);

        return repo.save(address);
    }

    public void deleteIp(String ip) {
        deleteIp(getIp(ip));
    }

    public void deleteIp(SuspiciousIp ipEntity) {
        repo.delete(ipEntity);
    }

    public List<SuspiciousIp> getAllIps() {
        return getAllIps(Sort.by(Sort.Order.asc("id")));
    }

    public List<SuspiciousIp> getAllIps(Sort sort) {
        return repo.findAll(sort);
    }
}
