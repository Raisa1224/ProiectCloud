package com.health.constants;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    @Value("${docker.application.ip}")
    public static String ip;
    public static String GET_PET_BY_ID_URL = "/pet/getPetById/";

    public static String GET_OWNER_FOR_PET_URL = "/pet/getOwnerIdForPet/";

    public static String GET_ALL_PETS_URL = "/pet/getAll";

    public static String PETS_BASE_URL = "http://"+ip+":8081";

    public static String PETS_BASE_URL_CONTAINER = "http://pet:8081";

    public static String HEALTH_BASE_URL = "http://"+ip+":8080";
}
