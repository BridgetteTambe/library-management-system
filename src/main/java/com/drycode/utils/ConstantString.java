package com.drycode.utils;

public class ConstantString {
    public static final String ID_EXISTS = "Can not create new #ENTITY with id: #ID. already exists";
    public static final String ID_NULL = "Can not update #ENTITY with id.null: ";
    public static final String NAMES_EXIST = "A #ENTITY already exists with the #NAMEEXISTS ";
    public static final String NAME_NULL = "#ENTITY field #NAME can not be null: ";


    // SERVICE LOGS
    public static final String DELETE_LOG = "Request to delete #ENTITY:  {}";
    public static final String SAVE_LOG = "Request to save #ENTITY:  {}";
    public static final String GET_LOG = "Request to get #ENTITY:  {}";
    public static final String FIND_BY_LOG = "Request to get #ENTITY by #FIELD {}";

    // RESOURCE / CONTROLLERS LOGS
    public static final String REST_DELETE_LOG = "Rest request to delete #ENTITY:";
    public static final String REST_UPDATE_LOG = "Rest request to update #ENTITY:";
    public static final String REST_SAVE_LOG = "Rest request to create new #ENTITY:";
    public static final String REST_GET_LOG = "Rest request to get #ENTITY:";

}
