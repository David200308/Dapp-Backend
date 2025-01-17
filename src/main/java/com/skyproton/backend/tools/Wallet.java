package com.skyproton.backend.tools;

import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Wallet {
    public boolean signatureVerification(
        String message,
        String signature,
        String walletAddress
    ) {
        byte[] messageHash = Numeric.hexStringToByteArray(
                Numeric.toHexStringNoPrefix(
                        message.getBytes(StandardCharsets.UTF_8)
                )
        );

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);

        byte v = signatureBytes[signatureBytes.length - 1];
        byte[] r = Arrays.copyOfRange(signatureBytes, 0, 32);
        byte[] s = Arrays.copyOfRange(signatureBytes, 32, 64);
        ECDSASignature ecdsaSignature = new ECDSASignature(
                new BigInteger(1, r),
                new BigInteger(1, s)
        );
        BigInteger recoveredAddress = Sign.recoverFromSignature(v, ecdsaSignature, messageHash);
        String recoveredAddressHex = "0x" + recoveredAddress.toString(16);

        boolean isVerified = recoveredAddressHex.equalsIgnoreCase(walletAddress);

        return isVerified;
    }
}
