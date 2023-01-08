package com.thrive.db;

import com.thrive.model.dao.StoredUser;

import java.io.File;
import java.util.Optional;

public interface UserFileS3 {

    Optional<File> get(String userId, String fileName) throws Exception;

    boolean put(String userId, File file) throws Exception;

}