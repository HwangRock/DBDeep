package org.example.presentation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ConnectRequest {
    private DataBaseType dataBaseType;
    private String host;
    private int port;
    private String dbName;
    private String userName;
    private String password;
}
