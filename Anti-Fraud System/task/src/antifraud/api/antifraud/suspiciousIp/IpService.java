package antifraud.api.antifraud.suspiciousIp;

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
public class IpService {
    final IpEntityRepository repo;

    public boolean exists(String ip) {
        try {
            return exists(InetAddress.getByName(ip));
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public boolean exists(InetAddress ip) {
        return repo.existsByIp(ip);
    }

    public boolean isValid(String ip) {
        try {
            InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            return false;
        }
        return true;
    }

    public IpService(@Autowired IpEntityRepository repo) {
        this.repo = repo;
    }

    public IpEntity getIp(InetAddress ip) {
        return repo
                .findByIp(ip)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ip not found"));
    }

    public IpEntity getIp(String ip) {
        final InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ip");
        }
        return getIp(inetAddress);
    }

    public IpEntity createIP(InetAddress ip) {
        if (repo.existsByIp(ip)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ip already registered");
        }
        val address = new IpEntity(ip);

        return repo.save(address);
    }

    public void deleteIp(String ip) {
        deleteIp(getIp(ip));
    }

    public void deleteIp(IpEntity ipEntity) {
        repo.delete(ipEntity);
    }

    public List<IpEntity> getAllIps() {
        return getAllIps(Sort.by(Sort.Order.asc("id")));
    }

    public List<IpEntity> getAllIps(Sort sort) {
        return repo.findAll(sort);
    }
}
