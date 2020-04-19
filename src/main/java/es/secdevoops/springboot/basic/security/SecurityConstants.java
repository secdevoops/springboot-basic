package es.secdevoops.springboot.basic.security;

public class SecurityConstants {
	public static final long EXPIRATION_TIME = 86400000; //24H
	public static final String SUPER_SECRET_KEY = "MySup3rS3cre7KeY@secdevoops";
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";
	public static final Object AUTHORITIES_KEY = "authorities";
	public static final boolean SECURITY_ENABLED = true;
	public static final String LOGIN_URL = "/login";
	public static final String REGISTER_URL = "/users/register";
	public static final String REGISTER_ADMIN_URL = "/users/registeradmin";
	
}
