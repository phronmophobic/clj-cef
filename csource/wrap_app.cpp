cef_app_t* wrapped = NULL;

void on_before_command_line_processing_wrapper(
      struct _cef_app_t* self,
      const cef_string_t* process_type,
      struct _cef_command_line_t* command_line)
{
    RestoreSignalHandlers();
    wrapped->on_before_command_line_processing(self, process_type, command_line);
};

void on_register_custom_schemes_wrapper(
    cef_app_t* self,
    cef_scheme_registrar_t* registrar)
{
    RestoreSignalHandlers();
    wrapped->on_register_custom_schemes(self, registrar);
};

cef_resource_bundle_handler_t* get_resource_bundle_handler_wrapper(cef_app_t* self){
    RestoreSignalHandlers();
    return wrapped->get_resource_bundle_handler(self);
};

cef_browser_process_handler_t* get_browser_process_handler_wrapper(cef_app_t* self){
    RestoreSignalHandlers();
    return wrapped->get_browser_process_handler(self);
};

cef_render_process_handler_t*get_render_process_handler_wrapper(cef_app_t* self){
    RestoreSignalHandlers();
    return wrapped->get_render_process_handler(self);
};



void wrap_app(cef_app_t* app){
        // app->on_before_command_line_processing = WRAPIT(app->on_before_command_line_processing);

        // app->get_browser_process_handler = my_handler;

        // app->on_register_custom_schemes = WRAPIT(app->on_before_command_line_process);
        // app->get_resource_bundle_handler = WRAPIT(app->on_before_command_line_process);
        // app->get_browser_process_handler = WRAPIT(app->on_before_command_line_process);
        // app->get_render_process_handler = WRAPIT(app->on_before_command_line_process);

    wrapped = new cef_app_t();
    if (app->on_before_command_line_processing){
        wrapped->on_before_command_line_processing = app->on_before_command_line_processing;
        app->on_before_command_line_processing = on_before_command_line_processing_wrapper;
    }

    if (app->on_register_custom_schemes){
        wrapped->on_register_custom_schemes = app->on_register_custom_schemes;
        app->on_register_custom_schemes = on_register_custom_schemes_wrapper;
    }

    if (app->get_resource_bundle_handler){
        wrapped->get_resource_bundle_handler = app->get_resource_bundle_handler;
        app->get_resource_bundle_handler = get_resource_bundle_handler_wrapper;
    }

    if (app->get_browser_process_handler){
        wrapped->get_browser_process_handler = app->get_browser_process_handler;
        app->get_browser_process_handler = get_browser_process_handler_wrapper;
    }

    if (app->get_render_process_handler){
        wrapped->get_render_process_handler = app->get_render_process_handler;
        app->get_render_process_handler = get_render_process_handler_wrapper;    
    }

}

void unwrap_app(cef_app_t* app){
    if (wrapped->on_before_command_line_processing){
        app->on_before_command_line_processing = wrapped->on_before_command_line_processing;
    }

    if (wrapped->on_register_custom_schemes){
        app->on_register_custom_schemes = wrapped->on_register_custom_schemes;
    }

    if (wrapped->get_resource_bundle_handler){
        app->get_resource_bundle_handler = wrapped->get_resource_bundle_handler;
    }

    if (wrapped->get_browser_process_handler){
        app->get_browser_process_handler = wrapped->get_browser_process_handler;
    }

    if (wrapped->get_render_process_handler){
        app->get_render_process_handler = wrapped->get_render_process_handler;
    }

    delete wrapped;
    wrapped = NULL;

}
