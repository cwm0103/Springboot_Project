package com.sbm.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * sbm
 * Created by yadong.zhang on com.sbm.util
 * User：yadong.zhang
 * Date：2016/10/21
 * Time：16:04
 */
public class IPUtil {
    public static String getRealIp() {
        String realIp = null;
        InetAddress ip = null;

        try {
            if (isWindowsOS()) { // windows
                ip = InetAddress.getLocalHost();
            } else { // Linux ,从网卡中获取ip地址
                boolean bFindIP = false;
                Enumeration<NetworkInterface> netInterfaces =
                        (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
                NetworkInterface ni = null;
                Enumeration<InetAddress> ips = null;
                while (netInterfaces.hasMoreElements()) {
                    if (bFindIP) {
                        break;
                    }
                    ni = (NetworkInterface) netInterfaces.nextElement();
                    ips = ni.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        ip = (InetAddress) ips.nextElement();
                        if (ip.isSiteLocalAddress()
                                && !ip.isLoopbackAddress()   //127.开头的都是lookback地址a
                                && ip.getHostAddress().indexOf(":") == -1) {
                            bFindIP = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ip != null) {
            realIp = ip.getHostAddress();
        }
        return realIp;
    }

    /**
     * 判断当前系统类型
     *
     * @return
     */
    private static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }

        return isWindowsOS;
    }
}
