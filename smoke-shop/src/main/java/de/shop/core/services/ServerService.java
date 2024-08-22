package de.shop.core.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Service
public class ServerService {
    private HttpServletRequest request;

    public ServerService(HttpServletRequest request) {
        this.request = request;
    }

    public String getHost() {
        return request.getServerName();
    }

    public String getURI() {
        return request.getRequestURI();
    }

    public String getProtocol() {
        return request.getScheme();
    }

    public int getPort() {
        return request.getServerPort();
    }

    public String getQueryParameters() {
        return request.getQueryString();
    }

    public Optional<String> getParam(String key) {
        return Optional.ofNullable(request.getParameter(key));
    }

    public String getFullUrl() {
        String protocol = getProtocol();
        String host = getHost();
        String uri = getURI();
        String p = String.valueOf(getPort());
        String port = "";
        if (!p.equals("80")) {
            port = ":" + p;
        }
        String full = protocol + "://" + host + "" + port + "" + uri;
        return full;
    }

    public String getSite() {
        String protocol = getProtocol();
        String host = getHost();
        String p = String.valueOf(getPort());
        String port = "";
        if (!p.equals("80")) {
            port = ":" + p;
        }
        return protocol + "://" + host + "" + port;
    }

    public String getClientIp() {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    public String getServerIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            return "Unknown";
        }
    }


}

