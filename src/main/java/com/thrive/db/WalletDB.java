package com.thrive.db;

import com.thrive.model.dao.StoredUser;
import com.thrive.model.dao.StoredWallet;
import com.thrive.model.dto.User;

import java.util.Optional;

public interface WalletDB {

    Optional<StoredWallet> get(StoredUser storedUser, boolean allowInactive) throws Exception;

    Optional<StoredWallet> save(StoredWallet storedWallet, StoredUser storedUser) throws Exception;

}
