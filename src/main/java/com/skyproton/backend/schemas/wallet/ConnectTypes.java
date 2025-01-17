package com.skyproton.backend.schemas.wallet;

import lombok.Getter;
import lombok.Setter;

public class ConnectTypes {
    @Setter
    @Getter
    public static class WalletConnectRequestType {
        private String walletAddress;
    }

    @Getter
    @Setter
    public static class WalletConnectResponseType {
        private String status;
        private String walletAddress;
        private String authUuid;

        public WalletConnectResponseType(
                String status,
                String walletAddress,
                String authUuid
        ) {
            this.status = status;
            this.walletAddress = walletAddress;
            this.authUuid = authUuid;
        }
    }

    @Setter
    @Getter
    public static class WalletVerificationRequestType {
        private String walletAddress;
        private String timestamp;
        private String signature;
    }

    @Getter
    @Setter
    public static class WalletVerificationResponseType {
        private String status;
        private String walletAddress;
        private Boolean isVerified;

        public WalletVerificationResponseType(
                String status,
                String walletAddress,
                Boolean isVerified
        ) {
            this.status = status;
            this.walletAddress = walletAddress;
            this.isVerified = isVerified;
        }
    }
}
