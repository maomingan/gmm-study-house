package com.gmm.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gmm
 * @date 2024/9/9
 */
@Slf4j
public class IPUtils {

    private static final Pattern PATTERN_HOSTNAME = Pattern.compile("^.*\\D+([0-9]+)$");

    /**
     * 获取request中的第一个公网ip
     *
     * @param request
     * @return
     */
    public static String getTrueIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前机器IP地址
     */
    public static String hostAddress() {
        InetAddress inetAddress = null;
        try {
            boolean success = false;
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                if (success) {
                    break;
                }

                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> ips = networkInterface.getInetAddresses();
                while (ips.hasMoreElements()) {
                    inetAddress = ips.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
                        success = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null != inetAddress ? inetAddress.getHostAddress() : "";
    }

    public static long addressTail() {
        try {
            String hostname = IPUtils.hostAddress();
            Matcher matcher = PATTERN_HOSTNAME.matcher(hostname);
            if (matcher.matches()) {
                return Long.parseLong(matcher.group(1));
            }
        } catch (Exception e) {

        }

        return 0;
    }

    /**
     * 获取调用客户端的IP地址
     */
    public static String getRemoteIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            log.error("IpUtils getRemoteIpAddr error " + e);
            ipAddress = "";
        }

        return ipAddress;
    }

}
