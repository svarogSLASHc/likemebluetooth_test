package com.cs.liker.csbeacon.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtils {
    final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static UUID asUuid(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

//    public static String onLeScan(final byte[] scanRecord) {
//        int startByte = 2;
//        String uuid = null;
//        boolean patternFound = false;
//        while (startByte <= 5) {
//            if (    ((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
//                    ((int) scanRecord[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
//                patternFound = true;
//                break;
//            }
//            startByte++;
//        }
//
//        if (patternFound) {
//            //Convert to hex String
//            byte[] uuidBytes = new byte[16];
//            System.arraycopy(scanRecord, startByte+4, uuidBytes, 0, 16);
//            String hexString = bytesToHex(uuidBytes);
//
//            //Here is your UUID
//            uuid =  hexString.substring(0,8) + "-" +
//                    hexString.substring(8,12) + "-" +
//                    hexString.substring(12,16) + "-" +
//                    hexString.substring(16,20) + "-" +
//                    hexString.substring(20,32);
//
//            //Here is your Major value
//            int major = (scanRecord[startByte+20] & 0xff) * 0x100 + (scanRecord[startByte+21] & 0xff);
//
//            //Here is your Minor value
//            int minor = (scanRecord[startByte+22] & 0xff) * 0x100 + (scanRecord[startByte+23] & 0xff);
//
//
//        }
//        return uuid;
//    }


    public static String onLeScan(final byte[] scanRecord) {
        int startByte = 2;
        String uuid = null;
        boolean patternFound = false;
        while (startByte <= 5) {
            if (    ((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                    ((int) scanRecord[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
                patternFound = true;
                break;
            }
            startByte++;
        }

        if (patternFound) {
            //Convert to hex String
            byte[] uuidBytes = new byte[16];
            System.arraycopy(scanRecord, startByte+4, uuidBytes, 0, 16);
            String hexString = bytesToHex(uuidBytes);

            //Here is your UUID
            uuid =  hexString.substring(0,8) +
                    hexString.substring(8,12) +
                    hexString.substring(12,16) +
                    hexString.substring(16,20) +
                    hexString.substring(20,32);

            //Here is your Major value
            int major = (scanRecord[startByte+20] & 0xff) * 0x100 + (scanRecord[startByte+21] & 0xff);

            //Here is your Minor value
            int minor = (scanRecord[startByte+22] & 0xff) * 0x100 + (scanRecord[startByte+23] & 0xff);


        }
        return uuid;
    }
}
