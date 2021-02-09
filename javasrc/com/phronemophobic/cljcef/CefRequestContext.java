package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefRequestContext extends Structure{
public CefRequestContext(){
base.size.setValue(this.size());
}

public static interface IsSameFunc extends Callback {

int is_same(CefRequestContext x0, CefRequestContext x1); 
}

public static interface IsSharingWithFunc extends Callback {

int is_sharing_with(CefRequestContext x0, CefRequestContext x1); 
}

public static interface IsGlobalFunc extends Callback {

int is_global(CefRequestContext x0); 
}

public static interface GetHandlerFunc extends Callback {

CefRequestContextHandler get_handler(CefRequestContext x0); 
}

public static interface GetCachePathFunc extends Callback {

CefStringUtf16 get_cache_path(CefRequestContext x0); 
}

public static interface GetCookieManagerFunc extends Callback {

CefCookieManager get_cookie_manager(CefRequestContext x0, CefCompletionCallback x1); 
}

public static interface RegisterSchemeHandlerFactoryFunc extends Callback {

int register_scheme_handler_factory(CefRequestContext x0, Pointer x1, Pointer x2, CefSchemeHandlerFactory x3); 
}

public static interface ClearSchemeHandlerFactoriesFunc extends Callback {

int clear_scheme_handler_factories(CefRequestContext x0); 
}

public static interface PurgePluginListCacheFunc extends Callback {

void purge_plugin_list_cache(CefRequestContext x0, int x1); 
}

public static interface HasPreferenceFunc extends Callback {

int has_preference(CefRequestContext x0, Pointer x1); 
}

public static interface GetPreferenceFunc extends Callback {

CefValue get_preference(CefRequestContext x0, Pointer x1); 
}

public static interface GetAllPreferencesFunc extends Callback {

CefDictionaryValue get_all_preferences(CefRequestContext x0, int x1); 
}

public static interface CanSetPreferenceFunc extends Callback {

int can_set_preference(CefRequestContext x0, Pointer x1); 
}

public static interface SetPreferenceFunc extends Callback {

int set_preference(CefRequestContext x0, Pointer x1, CefValue x2, CefStringUtf16 x3); 
}

public static interface ClearCertificateExceptionsFunc extends Callback {

void clear_certificate_exceptions(CefRequestContext x0, CefCompletionCallback x1); 
}

public static interface ClearHttpAuthCredentialsFunc extends Callback {

void clear_http_auth_credentials(CefRequestContext x0, CefCompletionCallback x1); 
}

public static interface CloseAllConnectionsFunc extends Callback {

void close_all_connections(CefRequestContext x0, CefCompletionCallback x1); 
}

public static interface ResolveHostFunc extends Callback {

void resolve_host(CefRequestContext x0, Pointer x1, CefResolveCallback x2); 
}

public static interface LoadExtensionFunc extends Callback {

void load_extension(CefRequestContext x0, Pointer x1, CefDictionaryValue x2, CefExtensionHandler x3); 
}

public static interface DidLoadExtensionFunc extends Callback {

int did_load_extension(CefRequestContext x0, Pointer x1); 
}

public static interface HasExtensionFunc extends Callback {

int has_extension(CefRequestContext x0, Pointer x1); 
}

public static interface GetExtensionsFunc extends Callback {

int get_extensions(CefRequestContext x0, Pointer x1); 
}

public static interface GetExtensionFunc extends Callback {

CefExtension get_extension(CefRequestContext x0, Pointer x1); 
}

public static interface GetMediaRouterFunc extends Callback {

CefMediaRouter get_media_router(CefRequestContext x0); 
}

public CefBaseRefCounted base;

public IsSameFunc is_same;

public IsSharingWithFunc is_sharing_with;

public IsGlobalFunc is_global;

public GetHandlerFunc get_handler;

public GetCachePathFunc get_cache_path;

public GetCookieManagerFunc get_cookie_manager;

public RegisterSchemeHandlerFactoryFunc register_scheme_handler_factory;

public ClearSchemeHandlerFactoriesFunc clear_scheme_handler_factories;

public PurgePluginListCacheFunc purge_plugin_list_cache;

public HasPreferenceFunc has_preference;

public GetPreferenceFunc get_preference;

public GetAllPreferencesFunc get_all_preferences;

public CanSetPreferenceFunc can_set_preference;

public SetPreferenceFunc set_preference;

public ClearCertificateExceptionsFunc clear_certificate_exceptions;

public ClearHttpAuthCredentialsFunc clear_http_auth_credentials;

public CloseAllConnectionsFunc close_all_connections;

public ResolveHostFunc resolve_host;

public LoadExtensionFunc load_extension;

public DidLoadExtensionFunc did_load_extension;

public HasExtensionFunc has_extension;

public GetExtensionsFunc get_extensions;

public GetExtensionFunc get_extension;

public GetMediaRouterFunc get_media_router;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_same", "is_sharing_with", "is_global", "get_handler", "get_cache_path", "get_cookie_manager", "register_scheme_handler_factory", "clear_scheme_handler_factories", "purge_plugin_list_cache", "has_preference", "get_preference", "get_all_preferences", "can_set_preference", "set_preference", "clear_certificate_exceptions", "clear_http_auth_credentials", "close_all_connections", "resolve_host", "load_extension", "did_load_extension", "has_extension", "get_extensions", "get_extension", "get_media_router");
 }}