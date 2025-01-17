package com.skyproton.backend.services.wallet;

import com.skyproton.backend.repositories.AuthRepo;
import com.skyproton.backend.schemas.database.AuthSchema;
import com.skyproton.backend.schemas.wallet.ConnectTypes;
import com.skyproton.backend.tools.Wallet;
import com.skyproton.backend.tools.Auth;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/wallet/connect")
public class Connect {

    @Autowired
    private AuthRepo authRepo;

    @PostMapping("")
    public ResponseEntity<ConnectTypes.WalletConnectResponseType> walletConnect(
            @RequestBody ConnectTypes.WalletConnectRequestType requestData
    ) {
        AuthSchema newAuthConnection = new AuthSchema();
        newAuthConnection.setWalletAddress(requestData.getWalletAddress());

        ConnectTypes.WalletConnectResponseType response = new ConnectTypes.WalletConnectResponseType(
                "success",
                requestData.getWalletAddress(),
                newAuthConnection.getAuthUuid()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<ConnectTypes.WalletVerificationResponseType> walletConnectVeirfy(
            @RequestBody ConnectTypes.WalletVerificationRequestType requestData
    ) throws JoseException {
        String authUuid = authRepo.findByWalletAddress(requestData.getWalletAddress())
                .orElseThrow().getAuthUuid();
        String message =
                requestData.getTimestamp() + " " +
                requestData.getWalletAddress() + " " +
                authUuid;

        Boolean isVerified = new Wallet().signatureVerification(
                message,
                requestData.getSignature(),
                requestData.getWalletAddress()
        );

        ConnectTypes.WalletVerificationResponseType response = new ConnectTypes.WalletVerificationResponseType(
                isVerified ? "success" : "failure",
                requestData.getWalletAddress(),
                isVerified
        );

        String token = new Auth().createJwt(
                "auth",
                requestData.getWalletAddress(),
                authUuid
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie",
                "token=" + token +
                        ";Max-Age=3600; Path=/; Secure; HttpOnly"
        );

        return ResponseEntity.status(
                isVerified ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        ).headers(headers).body(response);
    }

}
