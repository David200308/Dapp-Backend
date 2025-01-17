package com.skyproton.backend.repositories;

import com.skyproton.backend.schemas.database.AuthSchema;
import org.springframework.data.repository.ListCrudRepository;
import java.util.Optional;

public interface AuthRepo extends ListCrudRepository<AuthSchema, Integer> {
    Optional<AuthSchema> findByAuthUuid(String authUuid);
    Optional<AuthSchema> findByWalletAddress(String walletAddress);
}
